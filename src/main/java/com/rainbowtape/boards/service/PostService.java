package com.rainbowtape.boards.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rainbowtape.boards.entity.Post;

public interface PostService {
	
	public void save(Post post);
	public List<Post> getAllPost();
	public Post findById(int idpost);
	public void delete(int idpost);
	public void update(Post post);
	public Page<Post> findAll(Pageable pageable);
	public List<Post> findSpecialPost(String string);
}
