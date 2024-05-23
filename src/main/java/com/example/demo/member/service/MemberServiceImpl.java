package com.example.demo.member.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper memberMapper;
	private final EncryptHelper encryptHelper;
	
	/* 회원가입 */
	@Override
	public int insertMember(MemberDTO memberDTO) {
		String userPwd = memberDTO.getUserPwd();
		String encryptedPw = encryptHelper.encrypt(userPwd);
		memberDTO.setUserPwd(encryptedPw);	
		
		return memberMapper.insertMember(memberDTO);
	}
	
	/* 중복체크 */
	@Override
	public int overlapCheck(String valueType, String value) {	
		return memberMapper.overlapCheck(valueType, value);
	}
	
	/* 로그인 */
	@Override
	public MemberDTO login(MemberDTO memberDTO) {
		
		MemberDTO storedMember = memberMapper.getUserByEmail(memberDTO.getEmail());
	
		
		log.info("storedMember = {} ", storedMember);
		log.info("memberDTO = {} ", memberDTO);
		
		
		if (storedMember != null && encryptHelper.isMatch(memberDTO.getUserPwd(), storedMember.getUserPwd())) {
            return storedMember; 
        }

        return null; 
    }

	
	
	
	
	
	
	
	
}
