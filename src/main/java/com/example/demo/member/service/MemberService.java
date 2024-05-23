package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDTO;

public interface MemberService {
	
	/* 회원가입 */
	int insertMember(MemberDTO memberDTO);
	
	/* 중복체크 */
	int overlapCheck(String valueType, String value);
	
	/* 로그인 */
	MemberDTO login(MemberDTO memberDTO);

}
