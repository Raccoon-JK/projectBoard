package com.example.demo.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDTO {
	
	private int uploadfileNo;
	private int boardNo;
	private String fileName;
	private String changeName;
	
}
