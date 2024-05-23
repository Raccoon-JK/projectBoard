package com.example.demo.comments.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.comments.dto.CommentsDTO;
import com.example.demo.comments.mapper.CommentsMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentsServiceImpl implements CommentsService{
	
	private final CommentsMapper commentsMapper;
	
	/* 댓글 조회 */
	@Override
	public List<CommentsDTO> commentSelect(int boardNo) {
		return commentsMapper.commentSelect(boardNo);
	}
	
	/* 댓글 등록 */
	@Override
	public void commentsInsert(CommentsDTO commentsDTO) {
		commentsMapper.commentsInsert(commentsDTO);
	}
	
	/* 댓글 조회 ajax 1개 그려주기용 */
	@Override
	public CommentsDTO getCommentById(int commentNo) {
	    return commentsMapper.selectCommentById(commentNo);
	}
	
	/* 댓글 조회 ajax 전체 그려주기용 */
	@Override
    public List<CommentsDTO> getCommentsByBoardNo(int boardNo) {
        return commentsMapper.commentSelect(boardNo);
    }
	
	/* 댓글 삭제용 */
	@Override
	public int commentDelete(int commentNo) {
		return commentsMapper.commentDelete(commentNo);
	}
	
	@Override
	public int modifyComment(int commentNo, String commentContent) {
		return commentsMapper.modifyComment(commentNo, commentContent);
	}

}
