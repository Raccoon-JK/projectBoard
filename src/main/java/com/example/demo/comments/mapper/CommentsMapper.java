package com.example.demo.comments.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.comments.dto.CommentsDTO;

@Mapper
public interface CommentsMapper {
	
	/* 댓글 조회 */
	List<CommentsDTO> commentSelect(int boardNo);
	
	/* 댓글 등록 */
	void commentsInsert(CommentsDTO commentsDTO);
	
	/* 댓글 조회 ajax 1개 그려주기용 */
	CommentsDTO selectCommentById(int commentNo); 
	
	/* 댓글 삭제용 */
	int commentDelete(int commentNo);
	
	/* 댓글 수정용 */
	int modifyComment(int commentNo, String commentContent);
	
}
