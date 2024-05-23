package com.example.demo.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.member.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	/* 회원가입 */
	public int insertMember(MemberDTO memberDTO);
	
	/* 중복체크 */
	public int overlapCheck(String valueType, String value);
	
	/* 로그인 */
	public MemberDTO getUserByEmail(String email);
		
}
