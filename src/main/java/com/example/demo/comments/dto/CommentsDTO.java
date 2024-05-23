package com.example.demo.comments.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentsDTO {
	
	private int commentNo;
	private int boardNo;
	private int userNo;
	private String commentContent;
	private Date commentDate;
	private String commentStatus;
	private int commentParents;
	private int deep;
	
	private String userName; 

}
