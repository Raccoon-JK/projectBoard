package com.example.demo.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	
	
	private int userNo;
	
	@Size(max = 15, message = "15글자 이하만 가능합니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z0-9])(?=.*[!@#$%^*+=-]).{8,15}$)", message = "비밀번호는 문자,특수문자 조합하여 최소8자리 최대15자리만 가능합니다." )
	@NotBlank(message = "password is required")
	private String userPwd;
	
	@Size(max = 10, message = "10글자 이하만 가능합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "사용자이름은 영어랑 숫자만 가능합니다.")
	@NotBlank(message = "Name is required")
	private String userName;
	
	@Pattern(regexp = "^[a-zA-Z._@]+@[a-zA-Z._@]+\\.[a-zA-Z._@]{1,3}$", message = "이메일은 영어와 ._@만 가능합니다.")
	@NotBlank(message = "email is required")
	private String email;
		
	@Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰번호는 숫자로 최대 11자리이하로만 가능합니다.")
	@NotBlank(message = "phone is required")
	private int phone;
	
	@NotBlank(message = "address is required")
	private String address;
	
	@NotBlank(message = "addressDetail is required")
	private String addressDetail;
	
	@NotBlank(message = "postCode is required")
	private int postCode;
	private String note;
	
	
}	

	
	
	

