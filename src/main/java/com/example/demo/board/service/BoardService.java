package com.example.demo.board.service;

import java.util.List;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.CriteriaDTO;
import com.example.demo.board.dto.FileDTO;

public interface BoardService {
	
	/* 게시글 생성 */
	void boardCreate(BoardDTO boardDTO);
	
	/* 게시글 목록 페이징 처리 */
	List<BoardDTO> getBoardAll(CriteriaDTO cri);
	
	/* 목록 총 */
	public int getTotal();
	
	/* 게시글 조회 */
	BoardDTO boardSelect(int boardNo);
	
	/* 게시글 삭제 */	
	int boardDelete(int boardNo);
	
	/* 게시글 수정 */
	int boardUpdate(BoardDTO boardDTO);
	
	/* 파일 조회 */
	List<FileDTO> boardFile(int boardNo);
	
	/* 게시글 댓글 수 조회 */
	List<BoardDTO> getCommentNum();
	

}
