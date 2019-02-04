package com.rainbowtape.boards.service;

import java.util.List;

import com.rainbowtape.boards.entity.Post;

public interface PostService {
	
	public void save(Post post);
	public List<Post> getAllPost();
	public Post findById(int idpost);
	public void delete(int idpost);
	public void update(Post post);
}
