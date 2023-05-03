package com.nk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nk.dto.MemberDto;

public class MemberDao {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버로딩");
			e.printStackTrace();
		}
	}

	public MemberDao() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##heo", "1111"); // ojdbc6 오라클
																										// 계정만들어야함 톰캣
																										// 포트번호 변경
		} catch (SQLException e) {
			System.out.println("db 커넥트 ");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	public void memberInsert(MemberDto mDto) {
	String sql = "INERT INTO 테이블명 (?,?,?,?)";							//테이블 만들때 데이터타입
	
	
	}
		
		
		
		
	}

