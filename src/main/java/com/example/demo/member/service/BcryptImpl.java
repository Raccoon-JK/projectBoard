package com.example.demo.member.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptImpl implements EncryptHelper{
	
	@Override
	public String encrypt(String userPwd) {
		return BCrypt.hashpw(userPwd, BCrypt.gensalt());
	}
	
	@Override
	public boolean isMatch(String userPwd, String hashed) {
		return BCrypt.checkpw(userPwd, hashed);
	}
	

}
