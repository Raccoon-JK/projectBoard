package com.example.demo.board.dto;



import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
	
	private int boardNo;
	private int userNo;
	private String boardTitle;
	private String boardContent;
	private Date createDate;
	private String boardStatus;
	private int commentCount;
	
	private String userName;
	private MultipartFile[] files;
	private String deletedFiles;
	

}
