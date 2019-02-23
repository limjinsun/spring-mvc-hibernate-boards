package com.rainbowtape.boards.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;
import com.rainbowtape.boards.service.CourseService;
import com.rainbowtape.boards.service.SchoolService;


@Controller
@RequestMapping(value = "/school")
public class SchoolController {

	@Autowired
	ServletContext context;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CourseService courseService;

	/* An @ModelAttribute on a method argument indicates the argument should be retrieved from the model. 
	 * If not present in the model, the argument should be instantiated first and then added to the model.
	 * https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/mvc.html#mvc-ann-modelattrib-methods */	

	@GetMapping("/all")
	public String getAllSchool (Model model) {

		List<School> schools = schoolService.getAllSchool();

		model.addAttribute("schools", schools);
		return "_school";
	}

	@GetMapping("/course")
	public String getCourseDetails (@RequestParam("s_id") int idschool, Model model) {

		School school = schoolService.findOne(idschool);

		if(school == null) {
			return "404";
		}

		model.addAttribute("school", school);
		List<Course> courses = courseService.findBySchool(school);
		model.addAttribute("courses", courses);

		return "_course";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/addSchool")
	public String getSchoolAddForm (Model model) {

		model.addAttribute("school", new School());
		model.addAttribute("schoolName", new String());
		return "_schoolAddForm";
	}

	// /home/app-data/images/ --> live server
	// /Users/rainbowtape/liffey-app/images/ --> localhost:8080 
	private static String UPLOADED_FOLDER = "/home/app-data/images/";

	@PostMapping("/addSchool")
	public String addSchoolIntoDb (
			@RequestParam("file1") MultipartFile file1, 
			@RequestParam("file2") MultipartFile file2,
			@ModelAttribute("school") @Valid School school,
			BindingResult result, 
			RedirectAttributes redirectAttributes) {

		System.err.println(school.getSchoolDirectoryName());
		String schoolDirectoryName = school.getSchoolDirectoryName();

		if(result.hasErrors()) {
			System.out.println(result.toString());
			redirectAttributes.addFlashAttribute("errormsg", "입력에 실패하였습니다. 정상적으로 다시 작성해 주세요."); 
			return "redirect:/school/addSchool";
		}

		if (file1.isEmpty() || file2.isEmpty()) {
			redirectAttributes.addFlashAttribute("errormsg", "Please select a file to upload");
			return "redirect:/school/addSchool";
		}

		try {

			String directoryName = UPLOADED_FOLDER + schoolDirectoryName;
			File directory = new File(directoryName);
			if (! directory.exists()){
				System.err.println(directory.mkdirs());
				// If you require it to make the entire directory path including parents,
				// use directory.mkdirs(); here instead.
			}            

			String file1Name = schoolDirectoryName + "-logo.png";
			byte[] bytes = file1.getBytes();
			Path path = Paths.get(directoryName + File.separator + file1Name);
			Files.write(path, bytes);

			String file2Name = schoolDirectoryName + "-header.jpg";
			byte[] bytes2 = file2.getBytes();
			Path path2 = Paths.get(directoryName + File.separator + file2Name);
			Files.write(path2, bytes2);

			String logo = Paths.get(File.separator + schoolDirectoryName + File.separator + file1Name).toString();
			String schoolpics = Paths.get(File.separator + schoolDirectoryName + File.separator + file2Name).toString();
			school.setLogo(logo);
			school.setSchoolpics(schoolpics);
			
			school.setAdmintext(textToHtmlConvertingURLsToLinks(school.getAdmintext()));

			schoolService.save(school);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/school/all";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/update/{id}")
	public String getSchoolUpdateForm (@PathVariable("id") int idschool, Model model) {

		School school = schoolService.findOne(idschool);
		
		school.setAdmintext(html2textAndKeepLineBreak(school.getAdmintext()));
		model.addAttribute("school", school);

		return "_schoolUpdateForm";
	}

	@PostMapping("/update/{id}")
	public String updateSchool (
			@PathVariable("id") int idschool, 
			@RequestParam(value="file1", required=false) MultipartFile file1, 
			@RequestParam(value="file2", required=false) MultipartFile file2,
			@ModelAttribute("school") @Valid School temp,
			BindingResult result, 
			RedirectAttributes redirectAttributes,
			Model model) {

		if(result.hasErrors()) {
			System.out.println(result.toString());
			redirectAttributes.addFlashAttribute("errormsg", "입력에 실패하였습니다. 정상적으로 다시 작성해 주세요."); 
			return "redirect:/school/update/" + idschool;
		}
		// 그림파일 두개다 업데이트 하지 않은 경우, 학교정보만 업데이트 
		if (file1.isEmpty() && file2.isEmpty()) {
			School originalSchool = schoolService.findOne(idschool);
			temp.setIdschool(originalSchool.getIdschool()); // important.
			temp.setSchoolpics(originalSchool.getSchoolpics()); // keep pics index
			temp.setLogo(originalSchool.getLogo()); // keep logo index
			
			temp.setAdmintext(textToHtmlConvertingURLsToLinks(temp.getAdmintext()));
			originalSchool = temp;
			schoolService.save(originalSchool);
			
			return "redirect:/school/all";
		}
		// 그림파일 한개만 업데이트 한경우, 에러로 두개다 업데이트 요구함. 
		if(file1.isEmpty() && !file2.isEmpty()) {
			redirectAttributes.addFlashAttribute("errormsg", "Upload pics has to be set of pictures.");
			return "redirect:/school/update/" + idschool;
		} else if (!file1.isEmpty() && file2.isEmpty()) {
			redirectAttributes.addFlashAttribute("errormsg", "Upload pics has to be set of pictures.");
			return "redirect:/school/update/" + idschool;
		}

		String schoolDirectoryName = temp.getSchoolDirectoryName();
		School originalSchool = schoolService.findOne(idschool);

		try {

			// original foler exist? delete it!..
			deleteFolderIfExist(originalSchool);

			// make new folfer, if not exist..
			String directoryName = UPLOADED_FOLDER + schoolDirectoryName;
			File directory = new File(directoryName);
			if (! directory.exists()){
				System.err.println(directory.mkdirs());
				// If you require it to make the entire directory path including parents,
				// use directory.mkdirs(); here instead.
			}            

			String file1Name = schoolDirectoryName + "-logo.png";
			byte[] bytes = file1.getBytes();
			Path path = Paths.get(directoryName + File.separator + file1Name);
			Files.write(path, bytes);

			String file2Name = schoolDirectoryName + "-header.jpg";
			byte[] bytes2 = file2.getBytes();
			Path path2 = Paths.get(directoryName + File.separator + file2Name);
			Files.write(path2, bytes2);

			String logo = Paths.get(File.separator + schoolDirectoryName + File.separator + file1Name).toString();
			String schoolpics = Paths.get(File.separator + schoolDirectoryName + File.separator + file2Name).toString();
			temp.setLogo(logo);
			temp.setSchoolpics(schoolpics);
			temp.setAdmintext(textToHtmlConvertingURLsToLinks(temp.getAdmintext()));
			temp.setIdschool(originalSchool.getIdschool()); // important.

			originalSchool = temp;
			schoolService.save(originalSchool);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/school/all";
	}

	private void deleteFolderIfExist(School originalSchool) {
		String oldFolderName = UPLOADED_FOLDER + originalSchool.getSchoolDirectoryName();
		File oldFolder = new File(oldFolderName);

		if (oldFolder.isDirectory()) {
			File[] children = oldFolder.listFiles();
			for (File child : children) {
				child.delete();
			}
			oldFolder.delete();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete/{id}")
	public String deleteSchool (@PathVariable("id") int idschool) {

		School originalSchool = schoolService.findOne(idschool);
		deleteFolderIfExist(originalSchool);

		schoolService.delete(originalSchool);
		return "redirect:/school/all";
	}

	@GetMapping("/addCourse/{id}")
	public String getCourseAddForm (@PathVariable("id") int idschool, Model model) {

		School school = schoolService.findOne(idschool);
		model.addAttribute("course", new Course());
		model.addAttribute("school", school);

		return "_courseAddForm";
	}

	@PostMapping("/addCourse/{id}")
	public String addCourseIntoDb (@PathVariable("id") int idschool, @ModelAttribute Course course) {

		System.err.println(idschool);
		course.setSchool(schoolService.findOne(idschool));
		courseService.save(course);
		return "redirect:/school/all";
	}

	@GetMapping("/modifyCourse/{id}")
	public String getmodifyForm (@PathVariable("id") int idschool, Model model) {

		School school = schoolService.findOne(idschool);
		List<Course> courses = courseService.findBySchool(school);
		model.addAttribute("courses", courses);
		model.addAttribute("school", school);

		return "_courseModificationForm";
	}

	@PostMapping("/modifyCourse/{c_id}")
	public String modifyCourse (@PathVariable("c_id") int idcourse,
			@ModelAttribute("item") Course courseDTO) {

		Course course = courseService.findOne(idcourse);

		course.setName(courseDTO.getName());
		course.setPrice(courseDTO.getPrice());
		course.setSpecialprice(courseDTO.getSpecialprice());
		course.setSpecialdue(courseDTO.getSpecialdue());
		course.setDescription(courseDTO.getDescription());
		course.setAdmintext(courseDTO.getAdmintext());
		course.setExtra1(courseDTO.getExtra1());
		course.setExtra2(courseDTO.getExtra2());
		course.setExtra3(courseDTO.getExtra3());

		courseService.save(course);

		return "redirect:/school/all";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/deleteCourse/{c_id}")
	public String deleteCourse (@PathVariable("c_id") int idcourse) {
		
		System.err.println("deleting-course :" + idcourse);

		courseService.delete(idcourse);
		return "redirect:/school/all";
	}

	/** helper methods **/

	public static boolean isNullOrEmpty(String str) {
		if(str != null && !str.isEmpty())
			return false;
		return true;
	}

	public static String textToHtmlConvertingURLsToLinks(String text) {
		if (text == null) {
			return text;
		}
		String escapedText = HtmlUtils.htmlEscape(text);
		return escapedText.replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)",
				"$1<a href=\"$2\" target=\"_blank\">$2</a>$4");
	}

	public static String html2textAndKeepLineBreak(String html) {
		if(html==null)
			return html;
		Document document = Jsoup.parse(html);
		document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
		document.select("br").append("\\n");
		document.select("p").prepend("\\n\\n");
		String s = document.html().replaceAll("\\\\n", "\n");
		return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}

}