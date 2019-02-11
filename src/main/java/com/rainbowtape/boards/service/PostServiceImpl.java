package com.rainbowtape.boards.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.PostDAO;
import com.rainbowtape.boards.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	@Override
	public void save(Post post) {
		postDAO.save(post);
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
	
}
