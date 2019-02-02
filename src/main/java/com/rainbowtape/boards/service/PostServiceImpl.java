package com.rainbowtape.boards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
