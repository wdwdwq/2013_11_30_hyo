package com.koreaIT.example.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.Session.Session;
import com.koreaIT.example.JAM.dto.Member;
import com.koreaIT.example.JAM.util.Util;
import com.koreaIT.example.Service.MemberService;

public class MemberController {
	private Scanner sc;
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.memberService = new MemberService(conn);
	}

	public void doJoin() {
		
		if (Session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		System.out.println("== 회원가입 ==");
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isLoginIdDup) {
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			
			System.out.printf("%s은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요");
				continue;
			}
			break;
		}
		
		memberService.doJoin(loginId, loginPw, name);
		
		System.out.printf("%s 회원님이 가입되었습니다\n", name);
		
	}

	public void doLogin() {
		
		if (Session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		String loginId = null;
		String loginPw = null;
		
		System.out.println("== 로그인 ==");
		
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
		
			Member member = memberService.getMemberByLoginId(loginId);
			
			if (member == null) {
				System.out.printf("%s은(는) 존재하지 않는 아이디입니다\n", loginId);
				continue;
			}
			
			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			
			Session.login(member);
			
			System.out.printf("%s님 환영합니다~\n", member.name);
			
			break;
		}
	}

	public void doLogout() {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		Session.logout();
		System.out.println("로그아웃 되었습니다");
	}

	public void showProfile() {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		System.out.println("== 마이 페이지 ==");
		System.out.printf("가입일 : %s\n", Util.datetimeFormat(Session.getLoginedMember().regDate));
		System.out.printf("수정일 : %s\n", Util.datetimeFormat(Session.getLoginedMember().updateDate));
		System.out.printf("로그인 아이디 : %s\n", Session.getLoginedMember().loginId);
		System.out.printf("이름 : %s\n", Session.getLoginedMember().name);
	}
	
}