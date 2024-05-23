package com.example.demo.comments.service;

import java.util.List;

import com.example.demo.comments.dto.CommentsDTO;

public interface CommentsService {
	
	/* 댓글 조회 */
	List<CommentsDTO> commentSelect(int boardNo);
	
	/* 댓글 등록 */
	void commentsInsert(CommentsDTO commentsDTO);
	
	/* 댓글 조회 ajax 1개 그려주기용 */
	CommentsDTO getCommentById(int commentNo);
	
	/* 댓글 조회 ajax 전체 그려주기용 */
	List<CommentsDTO> getCommentsByBoardNo(int boardNo);
	
	/* 댓글 삭제용 */
	int commentDelete(int commentNo);
	
	int modifyComment(int commentNo, String commentContent);
}
