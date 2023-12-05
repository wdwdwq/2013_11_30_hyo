package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import com.koreaIT.example.controller.ArticleController;
import com.koreaIT.example.controller.MemberController;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			conn = DriverManager.getConnection(url, "root", "");
			
			MemberController memberController = new MemberController(conn, sc);
			ArticleController articleController = new ArticleController(conn, sc);
			
			while(true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				
				if (cmd.equals("member join")) {
					memberController.doJoin();
				} else if (cmd.equals("member login")) {
					memberController.doLogin();
				} else if (cmd.equals("member logout")) {
					memberController.doLogout();
				} else if (cmd.equals("member profile")) {
					memberController.showProfile();
				} else if (cmd.equals("article write")) {
					articleController.doWrite();
				} else if (cmd.startsWith("article list")) {
					articleController.showList(cmd);
				} else if (cmd.startsWith("article detail ")) {
					articleController.showDetail(cmd);
				} else if (cmd.startsWith("article modify ")) {
					articleController.doModify(cmd);
				} else if (cmd.startsWith("article delete ")) {
					articleController.doDelete(cmd);
				} else {
					System.out.println("존재하지 않는 명령어 입니다");
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		sc.close();
		
		System.out.println("== 프로그램 종료 ==");
	}
}