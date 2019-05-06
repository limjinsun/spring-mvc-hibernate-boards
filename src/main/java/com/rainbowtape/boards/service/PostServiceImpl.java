package com.rainbowtape.boards.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.PostDAO;
import com.rainbowtape.boards.entity.Post;

@Service
public class PostServiceImpl implements PostService {
	
	private final Logger slf4jLogger = LoggerFactory.getLogger(PostServiceImpl.class);
			
	@Autowired
	private PostDAO postDAO;

	@Override
	public void save(Post post) {
		postDAO.save(post);
		slf4jLogger.info("** DEBUG **","Post saved");
	}

	@Override
	public List<Post> getAllPost() {
		return postDAO.findAll();
	}

	@Override
	public Post findById(int idpost) {
		return postDAO.findByIdpost(idpost);
	}

	@Override
	@Transactional
	public void delete(int idpost) {
		postDAO.deleteByIdpost(idpost);
		System.err.println("delete post id - " + idpost);
	}

	@Override
	public void update(Post post) {
		postDAO.save(post);
	}

	@Override
	public Page<Post> findAll(Pageable pageable) {
		return postDAO.findAll(pageable);
	}

	@Override
	public List<Post> findSpecialPost(String string) {
		List<Post> result = postDAO.findSpecialPost(string);
		System.err.println(result.size());
		return result;
	}

	@Override
	public Page<Post> findManualPostAll(Pageable pageable) {
		return postDAO.findManualPostAll(pageable);
	}
	
}
