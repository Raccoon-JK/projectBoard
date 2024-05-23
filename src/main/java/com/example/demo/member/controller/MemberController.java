package com.example.demo.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;
	
	/* 로그인 페이지 */
	@GetMapping("/member/loginUser")
	public String readyLogin(HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
		if(member != null) {
			return "redirect:/board/tables";
		}				
		return "member/loginUser";	
   }
	
	/* 로그인 */
	@ResponseBody
	@PostMapping("/member/loginUser")
	public int login(MemberDTO memberDTO, HttpSession session) {
	    MemberDTO member = memberService.login(memberDTO);
	    
	    if (member != null) {
	        session.setAttribute("loginMember", member);
	        return 1; // 로그인 성공을 나타내는 값
	    } else {
	        return 0; // 로그인 실패를 나타내는 값
	    }
	}
	
	/* 로그아웃 */
	@PostMapping("/member/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			 session.invalidate();
		}
		return "redirect:/board/tables";
	}
	
	/* 회원가입 페이지 */
	@GetMapping("/member/membership")
	public String readyMembership() {
		return "member/membership";
	}
	
	/* 회원가입 */
	@PostMapping("/member/membership")
	public String insertMember(MemberDTO memberDTO/* , BindingResult bindingResult, Model model */) {
		
		if(memberService.insertMember(memberDTO) > 0) {			
			
			return "redirect:/member/loginUser";
		}
		return "redirect:/member/membership";
	}	
//      백엔드 유효성검사	
//		System.out.println(memberDTO);
//		if (bindingResult.hasErrors()) {
//			System.out.println("에러");
//			List<FieldError> list = bindingResult.getFieldErrors();
//			Map<String, String> errorMsg = new HashMap<>();
//			for (int i = 0; i < list.size(); i++) {
//				String field = list.get(i).getField();
//				String message = list.get(i).getDefaultMessage();
//				System.out.println("필드 = " + field);
//				System.out.println("메세지 = " + message);
//				errorMsg.put(field, message);
//			}
//			model.addAttribute("errorMsg", errorMsg);
//			return "loginUser";
//		}
//		return "loginUser";	

	/* 중복검사 */
	@ResponseBody
	@GetMapping("/overlapCheck")
	public int overlapCheck(String valueType, String value){ 
		 
		int count = memberService.overlapCheck(valueType, value);
  
	  return count;  
	}
	
	/* 프로필 페이지 */
	@GetMapping("/board/profile")
	public String main(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession(false);
		boolean isloggedIn = session != null && session.getAttribute("loginMember") != null;
		
		model.addAttribute("isloggedIn", isloggedIn);
		
		return "board/profile";		
	}
	
	
	

}
