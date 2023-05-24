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
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##heo", "1111");
		} catch (SQLException e) {
			System.out.println("db 커넥트 ");
			e.printStackTrace();
		}
	}

	
	
	
	public ArrayList<MemberDto> memberList(int start,int end){
		String sql ="SELECT * FROM ( SELECT ROWNUM NUM ,N. * FROM (SELECT * FROM PEMEMBER ORDER BY PEJOINDATE DESC) N ) WHERE NUM BETWEEN ? AND ? AND PESTATUS=1";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setInt(1,start);
			psmt.setInt(2,end);
			rs=psmt.executeQuery();
			MemberDto mDto =null;
			ArrayList<MemberDto> al = new ArrayList<>();
			while(rs.next()) {
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
				mDto.setPestatus(rs.getString("pestatus"));
				mDto.setPejoinDate(rs.getString("pejoindate"));
				al.add(mDto);
			}
			if(al.get(0).getPeId()!=null) {
				return al;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	
	public boolean memberInsert(MemberDto mDto) {
		String sql = "INSERT INTO pemember VALUES (?,?,?,?,?,?,PENUMERINGSEQ.NEXTVAL,'1','2',?,SYSDATE)"; // 0을 값으로 준건
																											// pestatus인데
																											// 이걸로 회원의
																											// 상태를 파악
																											// 1이면 정상회원
																											// 0이면 탈퇴회원
																											// 2면 메일인증이
																											// 안된 회원
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
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
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
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return 2;
	}

	public ArrayList<MemberDto> memberSearch(String parameter) { 
		String sql ="SELECT * FROM PEMEMBER WHERE PEID = (?)";
		ArrayList<MemberDto> mList = new ArrayList<>();
		MemberDto mDto = null;
		try {
			psmt = con.prepareStatement(sql);
				psmt.setString(1, parameter);
			rs = psmt.executeQuery();
			while (rs.next()) { // mDto 객체가 어디서 생성되는지에 따라 mList에 같은 값만 들어갈수 있으니 조심
				mDto = new MemberDto();
				mDto.setPeId(rs.getString("peid"));
				mDto.setPePwd(rs.getString("pepwd"));
				mDto.setPeName(rs.getString("pename"));
				mDto.setPeAge(rs.getInt("peage"));
				mDto.setPePhoneNumber(rs.getString("pephonenumber"));
				mDto.setPeMail(rs.getString("peemail"));
				mDto.setPeSequence(rs.getString("pesequence"));
				mDto.setPePower(rs.getString("pepower"));
				mDto.setPeSalt(rs.getString("pesalt"));
				mDto.setPestatus(rs.getString("pestatus"));
				mDto.setPejoinDate(rs.getString("pejoindate"));
				mList.add(mDto);
			}
			if (mDto.getPeId() != null) {
				return mList;
			}
		} catch (Exception e) {
		} /*
			 * finally { if (rs != null) try { rs.close(); } catch (Exception e) { } if
			 * (psmt != null) try { psmt.close(); } catch (Exception e) { } if (con != null)
			 * try { con.close(); } catch (Exception e) { } }
			 */
		return null;
	}

	public String WithdrawalCheck(MemberDto mDto) {
		String sql = "UPDATE PEMEMBER SET PENAME= '탈퇴' , PEAGE =0 , PEPHONENUMBER ='탈퇴',PEEMAIL='탈퇴',PESTATUS= 0, PESALT ='탈퇴' WHERE PEID=? AND PEPWD=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mDto.getPeId());
			psmt.setString(2, mDto.getPePwd());
			int re = psmt.executeUpdate();
			if (re != 0) {
				return "index.jsp";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

		return "beforeWithdrawalCheck.jsp";

	}

	public boolean memberInfoUpdate(MemberDto mDto) {
		String sql = "UPDATE PEMEMBER SET PENAME = ? , PEPHONENUMBER=?,PEEMAIL=? WHERE PEID=?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mDto.getPeName());
			psmt.setString(2, mDto.getPePhoneNumber());
			psmt.setString(3, mDto.getPeMail());
			psmt.setString(4, mDto.getPeId());
			int re = psmt.executeUpdate();
			if (re != 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

		return false;

	}

	public boolean emaildup(String pemail) {
		String sql = "SELECT PEEMAIL FROM PEMEMBER WHERE PEEMAIL=? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pemail);
			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return false;
	}

	public boolean mialChekFin(String pemail) {
		String sql = "UPDATE PEMEMBER SET PESTATUS=1 WHERE PEEMAIL=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pemail);
			int re = psmt.executeUpdate();
			if (re != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return false;

	}

	public boolean rePwd(MemberDto mDto) {
		String sql = "UPDATE PEMEMBER SET PEPWD =? , PESALT = ? WHERE PEID=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mDto.getPePwd());
			psmt.setString(2, mDto.getPeSalt());
			psmt.setString(3, mDto.getPeId());
			int re = psmt.executeUpdate();
			if (re != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return false;
	}




	public int toal() {
		String sql = "SELECT COUNT(*) as cnt FROM PEMEMBER WHERE PESTATUS=1";	
		try {
			psmt=con.prepareStatement(sql);
			rs=psmt.executeQuery();
			int total =0 ;
			if(rs.next()) {
				total = Integer.parseInt(rs.getString("cnt"));
			}
			return total;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (psmt != null)
				try {
					psmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		
		
		return 0;
	}



}
