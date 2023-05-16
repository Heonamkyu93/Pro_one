package com.nk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	public boolean memberInsert(MemberDto mDto) {
		String sql = "INSERT INTO pemember VALUES (?,?,?,?,?,?,PENUMERINGSEQ.NEXTVAL,'1','1',?)"; // 테이블 만들때 데이터타입
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mDto.getPeId());
			psmt.setString(2, mDto.getPePwd());
			psmt.setString(3, mDto.getPeName());
			psmt.setInt(4, mDto.getPeAge());
			psmt.setString(5, mDto.getPePhoneNumber());
			psmt.setString(6, mDto.getPeMail());
			psmt.setString(7, mDto.getPeSalt());
			int re = psmt.executeUpdate();
			if (re != 0) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public int memberLogin(MemberDto mDto) {
		String sql = "SELECT * FROM PEMEMBER WHERE ID=(?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mDto.getPeId());
			rs = psmt.executeQuery();
			if (rs.next()) {
				int a = (rs.getString("pepwd").equals(mDto.getPePwd())) ? 1 : 2;
				return a;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 2;
	}

	public ArrayList<MemberDto> memberList(byte a, String parameter) {		//인자가 1일경우 단순 select문
		String sql = (a == 1) ? "SELECT * FROM PEMEMBER WHERE PESTATUS=1" : "SELECT * FROM PEMEMBER WHERE PEID = (?)";
		ArrayList<MemberDto> mList = new ArrayList<>();
		MemberDto mDto =null;
		try {
			psmt = con.prepareStatement(sql);
			if (a != 1) {				//1이 아닐경우 아이디로 찾는경우라 ?에 값을 넣는 작업이 필요하다
				psmt.setString(1, parameter);}
				rs = psmt.executeQuery();
			while (rs.next()) {									//mDto 객체가 어디서 생성되는지에 따라 mList에 같은 값만 들어갈수 있으니 조심
				mDto=new MemberDto();
				mDto.setPeId(rs.getString("peid"));
				mDto.setPePwd(rs.getString("pepwd"));
				mDto.setPeName(rs.getString("pename"));
				mDto.setPeAge(rs.getInt("peage"));
				mDto.setPePhoneNumber(rs.getString("pephonenumber"));
				mDto.setPeMail(rs.getString("peemail"));
				mDto.setPeSequence(rs.getString("pesequence"));
				mDto.setPePower(rs.getString("pepower"));
				mDto.setPeSalt(rs.getString("pesalt"));
				mList.add(mDto);
			}
			if (mDto.getPeId() != null) {
				return mList;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public String WithdrawalCheck(String peid, String pepwd) {
		String sql ="UPDATE PEMEMBER SET PENAME= '탈퇴' , PEAGE =0 , PEPHONENUMBER ='탈퇴',PEEMAIL='탈퇴',PESTATUS= 0, PESALT ='탈퇴' WHERE PEID=? AND PEPWD=?  ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, peid);
			psmt.setString(2, pepwd);
			int re=psmt.executeUpdate();
			if(re != 0) {
				return "index.jsp";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "beforeWithdrawalCheck.jsp";
		
		
		
		
		
		
		
	}


	public boolean memberInfoUpdate(MemberDto mDto) {
		String sql ="UPDATE PEMEMBER SET PENAME = ? , PEPHONENUMBER=?,PEEMAIL=? WHERE PEID=?";
		
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, mDto.getPeName());
			psmt.setString(2, mDto.getPePhoneNumber());
			psmt.setString(3, mDto.getPeMail());
			psmt.setString(4, mDto.getPeId());
			int re=psmt.executeUpdate();
			if(re!=0) {
				return true;
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
		
	}

	public boolean emaildup(String pemail) {
		String sql="SELECT PEEMAIL FROM PEMEMBER WHERE PEEMAIL=? ";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, pemail);
			rs=psmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * public void memberInfoUpdateForm(String peid) { String sql =
	 * "SELECT * FROM PEMEMBER WHERE PEID=?";
	 * 
	 * try { psmt=con.prepareStatement(sql); psmt.setString(1,peid);
	 * rs=psmt.executeQuery(); HashMap<String,String> hmap = new HashMap<>();
	 * while(rs.next()) { hmap.put("peid",rs.getString("peid"));
	 * hmap.put("pename",rs.getString("pename"));
	 * hmap.put("pephonenumber",rs.getString("pephonenumber"));
	 * hmap.put("peemail",rs.getString("peemail")); } } catch (SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */


	/*
	 * public void memberSearch(String parameter) { String sql =
	 * "SELECT * FROM PEMEMBER WHERE ID = (?)"; try { psmt =
	 * con.prepareStatement(sql); psmt.setString(1, parameter); rs =
	 * psmt.executeQuery(); while (rs.next()) ; } catch (Exception e) { } }
	 */

}
