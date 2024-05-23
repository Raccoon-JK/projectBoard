package com.example.demo.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.CriteriaDTO;
import com.example.demo.board.dto.FileDTO;
import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.mapper.FileMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;	
	private final FileMapper fileMapper;
	
	/* 게시글 작성 */
	@Override
	public void boardCreate(BoardDTO boardDTO) {
	    MultipartFile[] files = boardDTO.getFiles();

	    // 게시글만 있을때
	    boardMapper.boardCreate(boardDTO);

	    int boardNo = boardDTO.getBoardNo();
	    
	    uploadFiles(files, boardNo);
	}
	
	/* 게시글 목록 페이징 */
	@Override
	public List<BoardDTO> getBoardAll(CriteriaDTO cri){
		return boardMapper.getBoardAll(cri);
	}
	
	/* 게시글 페이징 총 */
	@Override
	public int getTotal() {
		return boardMapper.getTotal();
	}
	
	/* 게시글 조회 */
	@Override
	public BoardDTO boardSelect(int boardNo) {
		return boardMapper.boardSelect(boardNo);
	}
	
	/* 게시글 삭제 */
	@Override
	public int boardDelete(int boardNo) {
		return boardMapper.boardDelete(boardNo);		
	}
	
	/* 게시글 수정 */
	@Override
	public int boardUpdate(BoardDTO boardDTO) {
		 int result = boardMapper.boardUpdate(boardDTO);
		 
			/* 삭제할 파일 있으면 삭제하기 */
	        if (boardDTO.getDeletedFiles() != null) {
	            String[] deletedFileIds = boardDTO.getDeletedFiles().split(",");
	            for (String fileNo : deletedFileIds) {
	            	if (!fileNo.isEmpty()) { // 파일이 없어 빈문자열이 올 경우 아래 실행 안하게하기
	            		
						/* db에서 삭제하기전에 변경된 파일 이름 가져오기 */
	            		String storedFileName = fileMapper.getChangeName(Integer.parseInt(fileNo));
	            		
						/* db에서 삭제 */
	            		fileMapper.deleteFile(Integer.parseInt(fileNo));
	            	
					/* 서버 파일 삭제 */
	            	String uploadDir = "C:/workspace/testProject/src/main/resources/static/board_file/";	            	
	                
	                if (storedFileName != null && !storedFileName.isEmpty()) {

	                    File file = new File(uploadDir + storedFileName);

	                    // 파일이 있으면 삭제하기
	                    if (file.exists()) {
	                        boolean deleted = file.delete();
	                        if (!deleted) {
	                            System.out.println("파일 삭제 실패: " + storedFileName);
	                        }
	                    } else {
	                        System.out.println("파일이 존재하지 않습니다: " + storedFileName);
	                    }
	                }
	            	
	            	}
	            }
	        }
	        
	        return result;		 		 
	}
	
	/* 파일 조회 */
	@Override
	public List<FileDTO> boardFile(int boardNo){
		return fileMapper.boardFile(boardNo);
	}
	
	/* 게시글 댓글 수 조회 */
	public List<BoardDTO> getCommentNum(){
		return boardMapper.getCommentNum();
	}
	
	/* 공통 파일 업로드 */
	private void uploadFiles(MultipartFile[] files, int boardNo) {
	    // 파일 없으면 건너뛰기
	    if (files == null || files.length == 0) {
	        return;
	    }

	    // 파일 업로드 경로 지정
	    String uploadDir = "C:/workspace/testProject/src/main/resources/static/board_file/";
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    List<FileDTO> fileDTOList = new ArrayList<>();

	    for (MultipartFile file : files) {
	        if (file == null || file.isEmpty()) {
	            continue;
	        }

	        String originalFilename = file.getOriginalFilename();
	        if (originalFilename == null || originalFilename.isEmpty()) {
	            continue;
	        }

	        String uuid = UUID.randomUUID().toString();
	        String extension = "";
	        int dotIndex = originalFilename.lastIndexOf(".");
	        if (dotIndex > 0) {
	            extension = originalFilename.substring(dotIndex);
	        }

	        String storedFilename = uuid + extension;

	        try {
	            File destination = new File(uploadDir + storedFilename);
	            file.transferTo(destination);
	        } catch (IOException e) {
	            throw new RuntimeException("파일 업로드 실패", e);
	        }

	        FileDTO fileDTO = new FileDTO();
	        fileDTO.setBoardNo(boardNo);
	        fileDTO.setFileName(originalFilename);
	        fileDTO.setChangeName(storedFilename);

	        fileDTOList.add(fileDTO);
	    }

	    if (!fileDTOList.isEmpty()) {
	        fileMapper.insertFiles(fileDTOList);
	    }
	}
		

}
