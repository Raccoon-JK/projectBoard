package com.example.demo.member.service;

public interface EncryptHelper {
	
	String encrypt(String userPwd);
	
	boolean isMatch(String userPwd, String hashed);
	
}
