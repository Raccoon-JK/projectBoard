package com.example.demo.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.board.dto.FileDTO;

@Mapper
public interface FileMapper {
	
	/* 파일 업로드 */
	void insertFiles(List<FileDTO> fileDTOList);
	 
	/* 파일 조회 */
	List<FileDTO> boardFile(int boardNo);
	
	/* 수정 파일 삭제 */
	void deleteFile(int fileNo);
	
	/* 서버 파일 삭제 조회용 */
	String getChangeName(int fileNo);

}
