package com.example.demo.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.CriteriaDTO;

@Mapper
public interface BoardMapper {
	
	/* 게시글 작성 */
	void boardCreate(BoardDTO boardDTO);
	
	/* 게시글 목록 페이징 */
	List<BoardDTO> getBoardAll(CriteriaDTO cri);
	
	/* 게시글 목록 총 */
	int getTotal();
	
	/* 게시글 조회 */
	BoardDTO boardSelect(int boardNo);
	
	/* 게시글 삭제 */
	int boardDelete(int boardNo);
	
	/* 게시글 수정 */
	int boardUpdate(BoardDTO boardDTO);	
	
	/* 게시글 댓글 수 조회 */
	List<BoardDTO> getCommentNum();
}