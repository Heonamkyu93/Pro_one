package com.nk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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
		}finally {
	          if (rs != null) try { rs.close(); } catch(Exception e) {}
	          if (psmt != null) try { psmt.close(); } catch(Exception e) {}
	          if (con != null) try { con.close(); } catch(Exception e) {}
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
		}finally {
	          if (rs != null) try { rs.close(); } catch(Exception e) {}
	          if (psmt != null) try { psmt.close(); } catch(Exception e) {}
	          if (con != null) try { con.close(); } catch(Exception e) {}
	      }
		
		return false;
	}

	public void contentInside(String botitle) {
		LinkedList<BoardDto> ll = new LinkedList<>();
		BoardDto bDto = null;
		String sql = "SELECT * FROM PEBOARD WHERE BOTITLE=?";   //타이틀로 가져오면 안되고 unique나 시퀀스도 가져와서 중복된값이 없도록 처리해야한다
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, botitle);
			rs=psmt.executeQuery();
			while(rs.next()) {
				bDto=new BoardDto();
				bDto.setBoTitle(rs.getString("botitle"));
				bDto.setBoContent(rs.getString("bocontent"));
				bDto.setBoSequence(rs.getString("bosequence"));
				bDto.setBoDate(rs.getString("bodate"));
				bDto.setPeid(rs.getString("peid"));
				bDto.setBoAvailable(rs.getInt("boavailable"));
				ll.add(bDto);
			}
			if(rs.getString("botitle")!=null) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
	          if (rs != null) try { rs.close(); } catch(Exception e) {}
	          if (psmt != null) try { psmt.close(); } catch(Exception e) {}
	          if (con != null) try { con.close(); } catch(Exception e) {}
	      }
		
	}


}
