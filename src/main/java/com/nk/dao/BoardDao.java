package com.nk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##heo", "1111");

		} catch (SQLException e) {
			System.out.println("db 커넥트 ");
			e.printStackTrace();
		}
	}

	public boolean boardInsert(BoardDto bt) {
		String sql = "INSERT INTO PEBOARD VALUES ('BONUMBERINGSEQ.NEXTVAL',?,?" + 1 + "SYSDATE,?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, bt.getBoTitle());
			psmt.setString(2, bt.getBoContent());
			psmt.setString(3, bt.getPeid());
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

	public boolean boardDel() {
		String sql = "UPDATE PEBOARD SET BOAVAILABLE = 2";
		try {
			psmt = con.prepareStatement(sql);
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

	public void contentInside(String botitle) {
		LinkedList<BoardDto> ll = new LinkedList<>();
		BoardDto bDto = null;
		String sql = "SELECT * FROM PEBOARD WHERE BOTITLE=?"; // 타이틀로 가져오면 안되고 unique나 시퀀스도 가져와서 중복된값이 없도록 처리해야한다
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, botitle);
			rs = psmt.executeQuery();
			while (rs.next()) {
				bDto = new BoardDto();
				bDto.setBoTitle(rs.getString("botitle"));
				bDto.setBoContent(rs.getString("bocontent"));
				bDto.setBoSequence(rs.getString("bosequence"));
				bDto.setBoDate(rs.getString("bodate"));
				bDto.setPeid(rs.getString("peid"));
				bDto.setBoAvailable(rs.getInt("boavailable"));
				ll.add(bDto);
			}
			if (rs.getString("botitle") != null) {

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

	}

	public ArrayList<BoardDto> boardList(int start, int end) {
		String sql = "SELECT * FROM ( SELECT ROWNUM NUM ,N. * FROM (SELECT * FROM PEBOARD ORDER BY BODATE DESC) N ) WHERE NUM BETWEEN ? AND ?";
		ArrayList<BoardDto> al = new ArrayList<>();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			rs = psmt.executeQuery();
			while (rs.next()) {
				BoardDto bDto = new BoardDto();
				bDto.setBoTitle(rs.getString("botitle"));
				bDto.setBoContent(rs.getString("bocontent"));
				bDto.setBoSequence(rs.getString("bosequence"));
				bDto.setBoDate(rs.getString("bodate"));
				bDto.setPeid(rs.getString("peid"));
				bDto.setBoAvailable(rs.getInt("boavailable"));
				al.add(bDto);
			}
			if (al.get(0).getPeid() != null) {
				return al;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	public int total() {
		String sql = "SELECT  COUNT(*) as cnt FROM PEBOARD";
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = Integer.parseInt(rs.getString("cnt"));
			}

			return total;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
