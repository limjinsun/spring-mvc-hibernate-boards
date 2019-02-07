package com.rainbowtape.boards.service;

import java.util.List;

import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.Reply;

public interface ReplyService {
	
	public void save(Reply reply);
	public List<Reply> findByPost(Post post);
	public void delete(int idreply);

}
