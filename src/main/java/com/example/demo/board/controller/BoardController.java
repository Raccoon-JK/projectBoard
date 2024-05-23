package com.example.demo.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.CriteriaDTO;
import com.example.demo.board.dto.FileDTO;
import com.example.demo.board.dto.PageDTO;
import com.example.demo.board.service.BoardService;
import com.example.demo.comments.dto.CommentsDTO;
import com.example.demo.comments.service.CommentsService;
import com.example.demo.member.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService boardService;
	private final CommentsService commentsService;
	
	/* 게시판 페이지 */
	@GetMapping("/board/tables")
	public String main(HttpServletRequest request, Model model, CriteriaDTO cri){
		
		HttpSession session = request.getSession(false);
		boolean isloggedIn = session != null && session.getAttribute("loginMember") != null;
		
		model.addAttribute("isloggedIn", isloggedIn);		
		
		List<BoardDTO> boards = boardService.getBoardAll(cri);
		model.addAttribute("boards", boards);

		int total = boardService.getTotal();
		
		PageDTO page = new PageDTO(cri, total);
		
		model.addAttribute("page", page);
		
		List<BoardDTO> CommentNum = boardService.getCommentNum();
		model.addAttribute("commentsNum", CommentNum);
		
		for (BoardDTO board : boards) {
            for (BoardDTO commentNum : CommentNum) {
                if (board.getBoardNo() == commentNum.getBoardNo()) {
                    board.setCommentCount(commentNum.getCommentCount());
                    break;
                }
            }
        }		
		return "board/tables";		
	}
	
	/* 게시글작성 페이지 */
	@GetMapping("/board/write")
	public String readyWrite(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession(false);
		boolean isloggedIn = session != null && session.getAttribute("loginMember") != null;
		
		if(!isloggedIn) {
			return "redirect:/board/tables";
		}
		model.addAttribute("isloggedIn", isloggedIn);
		
		return "board/write";
	}
	
	/* 게시글 작성 */
	@PostMapping("/board/write")
	public ResponseEntity<?> boardCreate(String boardTitle, 
								String boardContent, 
								HttpSession session,
								MultipartFile[] files,
								Model model) {
		
		Object loginMember = session.getAttribute("loginMember");
		
		MemberDTO memberDTO = (MemberDTO) loginMember;
		
		int userNo = memberDTO.getUserNo();
		
		if(loginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용 가능합니다.");
		}
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setUserNo(userNo);
		boardDTO.setBoardTitle(boardTitle);
		boardDTO.setBoardContent(boardContent);
		boardDTO.setFiles(files);

		boardService.boardCreate(boardDTO);
		
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", true);
		
		return ResponseEntity.ok().body(responseBody);
	}
	
	/* 게시글 상세페이지 */
	@GetMapping("/board/detail")
	public String main(HttpServletRequest request, Model model, BoardDTO boardDTO, int boardNo) {
		
		HttpSession session = request.getSession(false);
		boolean isloggedIn = session != null && session.getAttribute("loginMember") != null;
		
		model.addAttribute("isloggedIn", isloggedIn);
		model.addAttribute("loginMember", session.getAttribute("loginMember"));
		model.addAttribute("board", boardService.boardSelect(boardNo));
		
		List<FileDTO> fileList = boardService.boardFile(boardNo);
		model.addAttribute("fileList", fileList);
		
		List<CommentsDTO> comments = commentsService.commentSelect(boardNo);
		model.addAttribute("comments", comments);		
		
		return "board/detail";		
	}
	
	/* 게시글 삭제 */
	@ResponseBody
	@PostMapping("/deleteBoard")
	public int boardDelete(int boardNo) {
		
		int result = boardService.boardDelete(boardNo);
		
		System.out.println("result : " + result); 
		
		return result;		
	}
	
	/* 게시글 수정 페이지 */
	@GetMapping("/board/modify")
	public String main(HttpServletRequest request, Model model, int boardNo) {
		
		HttpSession session = request.getSession(false);
		boolean isloggedIn = session != null && session.getAttribute("loginMember") != null;
		
		 BoardDTO board = boardService.boardSelect(boardNo);
		 MemberDTO member = (MemberDTO)session.getAttribute("loginMember");
		 
		 if (!isloggedIn || board.getUserNo() != member.getUserNo()){
			 return "redirect:/member/loginUser";
		 }		 
		
		model.addAttribute("isloggedIn", isloggedIn);		
		model.addAttribute("board", boardService.boardSelect(boardNo));
		
		List<FileDTO> fileList = boardService.boardFile(boardNo);
		model.addAttribute("fileList", fileList);
		
		return "board/modify";
	}
	
	/* 게시글 수정 */
	@ResponseBody
	@PostMapping("/board/modify")
	public int boardUpdate(BoardDTO boardDTO, String deletedFiles) {
		
		if (deletedFiles != null) {
	        boardDTO.setDeletedFiles(deletedFiles);
	    }
			  
	  int result = boardService.boardUpdate(boardDTO);
	  
	  return result;
	  
	}
	

	

}
