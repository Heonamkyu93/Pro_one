package com.nk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nk.dto.BoardDto;

public class BoardDao {
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

	public BoardDao() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##heo", "1111"); // ojdbc6 오라클
																										// 계정만들어야함 톰캣
																										// 포트번호 변경
		} catch (SQLException e) {
			System.out.println("db 커넥트 ");
			e.printStackTrace();
		}
	}

	public boolean boardInsert(BoardDto bt) {
		String sql = "INSERT INTO PEBOARD VALUES ('BONUMBERINGSEQ.NEXTVAL',?,?"+1+"SYSDATE,?)";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1,bt.getBoTitle());
			psmt.setString(2, bt.getBoContent());
			psmt.setString(3, bt.getPeid());
			int re=psmt.executeUpdate();
			if(re!=0) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean boardDel() {
		String sql = "UPDATE PEBOARD SET BOAVAILABLE = 2" ;
		try {
			psmt=con.prepareStatement(sql);
			int re = psmt.executeUpdate();
			if(re!=0) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}


}
