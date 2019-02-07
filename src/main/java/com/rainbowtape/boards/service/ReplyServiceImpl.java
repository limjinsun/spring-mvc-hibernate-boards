package com.rainbowtape.boards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.ReplyDAO;
import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	@Override
	public void save(Reply reply) {
		replyDAO.save(reply);
	}

	@Override
	public List<Reply> findByPost(Post post) {
		return replyDAO.findByPost(post);
	}

	@Override
	public void delete(int idreply) {
		replyDAO.delete(idreply);
		System.err.println("delete reply id - " + idreply);
	}

}
