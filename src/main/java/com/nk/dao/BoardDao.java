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
		try  {
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##heo", "1111");

		} catch (SQLException e) {
			System.out.println("db 커넥트 ");
			e.printStackTrace();
		}
	}

	public String boardInsert(BoardDto bt) {
		String sql = "INSERT INTO PEBOARD VALUES (bonumeringseq.NEXTVAL,?,?,?,SYSDATE,?)";
		try  {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, bt.getBoTitle());
			psmt.setString(2, bt.getBoContent());
			psmt.setString(3, bt.getPeid());
			psmt.setInt(4, bt.getBoAvailable());
			int re = psmt.executeUpdate();
			if (re != 0) {
				String sql2 = "SELECT BONUMERINGSEQ.CURRVAL FROM DUAL";
				psmt = con.prepareStatement(sql2);
				rs = psmt.executeQuery();
				if (rs.next()) {
					String seq = rs.getString("currval");
					return seq;
				}

			} else {
				return "n";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "n";
	}



	public ArrayList<BoardDto> boardList(int start, int end) {
		String sql = "SELECT * FROM ( SELECT ROWNUM NUM ,N. * FROM (SELECT * FROM PEBOARD WHERE boavailable = 1 ORDER BY BODATE DESC) N ) WHERE NUM BETWEEN ? AND ? AND  boavailable = 1";
	//	String sql = "SELECT BOSEQUENCE,BOTITLE,BOCONTENT,PEID,BODATE,BOAVAILABLE FROM (SELECT * FROM PEBOARD ORDER BY BODATE DESC) WHERE ROWNUM BETWEEN ? AND ? AND BOAVAILABLE = 1";
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
			if (al!= null) {
				return al;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	public int total() {
		String sql = "SELECT  COUNT(*) as cnt FROM PEBOARD where boavailable = 1 ";
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

	public void fileInset(BoardDto bt2) {
		String sql = "INSERT INTO BOARDFILE VALUES(?,?,?,bofileseq.nextval)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, bt2.getBoSequence());
			psmt.setString(2, bt2.getBoFileOri());
			psmt.setString(3, bt2.getBoFileSer());
			int re = psmt.executeUpdate();
			if (re != 0) {
				System.out.println("인서트성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public LinkedList<BoardDto> boardInside(String bosequence) {
		// "SELECT P.BOSEQUENCE , P.BOTITLE , P.BOCONTENT , P.PEID ,P.BODATE ,F.BOFILEORI ,F.BOFILESER ,C.BOHIT,C.BOLIKE,C.BODISLIKE ,R.REPEID,R.REPLE,R.REDATE FROM PEBOARD P , BOARDFILE F , boardcount C , boardreple R WHERE p.bosequence=f.bosequence AND C.BOSEQUENCE=F.BOSEQUENCE AND c.bosequence = r.bosequence AND P.BOSEQUENCE = ?";
		// "SELECT P.BOSEQUENCE , P.BOTITLE , P.BOCONTENT , P.PEID ,P.BODATE ,F.BOFILEORI ,F.BOFILESER ,C.BOHIT,C.BOLIKE,C.BODISLIKE ,R.REPEID,R.REPLE,R.REDATE,R.reSequence FROM PEBOARD P , BOARDFILE F , boardcount C , boardreple R WHERE p.bosequence=f.bosequence AND C.BOSEQUENCE=F.BOSEQUENCE AND c.bosequence = r.bosequence AND P.BOSEQUENCE = ? ORDER BY REDATE ASC";
		// "SELECT P.BOSEQUENCE , P.BOTITLE , P.BOCONTENT , P.PEID ,P.BODATE,C.BOHIT,C.BOLIKE,C.BODISLIKE FROM PEBOARD P ,boardcount C WHERE p.bosequence= c.bosequence AND P.BOSEQUENCE =? ";
		String sql ="SELECT * FROM PEBOARD P FULL OUTER JOIN boardcount C ON p.bosequence = c.bosequence FULL OUTER JOIN  boardfile F ON p.bosequence = f.bosequence FULL OUTER JOIN boardreple r ON p.bosequence = r.bosequence where P.BOSEQUENCE =? AND p.boavailable=1 ORDER BY REDATE ASC"; 
		LinkedList<BoardDto> ll = new LinkedList<>();																															 
		BoardDto bDto;
		try {
			psmt = con.prepareStatement(sql);			 
			psmt.setString(1, bosequence);
			rs = psmt.executeQuery();
			while (rs.next()) {
				bDto = new BoardDto();
				int i = 0;
				if (i == 0) {
					bDto.setBoSequence(rs.getString("BOSEQUENCE"));
					bDto.setBoTitle(rs.getString("BOTITLE"));
					bDto.setBoContent(rs.getString("BOCONTENT"));
					bDto.setPeid(rs.getString("PEID"));
					bDto.setBoDate(rs.getString("BODATE"));
				}
				bDto.setBoFileOri(rs.getString("BOFILEORI"));
				bDto.setBoFileSer(rs.getString("BOFILESER"));
				bDto.setFileseq(rs.getString("fileseq"));
				bDto.setBoHit(rs.getInt("BOHIT"));
				bDto.setBoLike(rs.getInt("BOLIKE"));
				bDto.setBodisLike(rs.getInt("BODISLIKE"));
				bDto.setReSequence(rs.getString("reSequence"));
				bDto.setRepeid(rs.getString("REPEID"));
				bDto.setReple(rs.getString("REPLE"));
				bDto.setRedate(rs.getString("REDATE"));
				ll.add(bDto);
				i++;
			}
			
			if (ll!= null) {
				return ll;
			} 
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
		return null;

	}

	public void cloe() {
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

	/*
	 * public void getReple(String bosequence) { String sql =
	 * "select * from boardreple where bosequence=? order by redate desc;";
	 * LinkedList< BoardDto> ll = new LinkedList<>(); BoardDto bDto; try {
	 * psmt=con.prepareStatement(sql); psmt.setString(1, bosequence);
	 * rs=psmt.executeQuery(); while(rs.next()) { bDto = new BoardDto();
	 * bDto.setReSequence(rs.getString("reSequence"));
	 * bDto.setRepeid(rs.getString("REPEID")); bDto.setReple(rs.getString("REPLE"));
	 * bDto.setRedate(rs.getString("REDATE")); } } catch (SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
	
	public boolean repleIn(BoardDto bDto) {
		String sql = "INSERT INTO BOARDREPLE VALUES(?,?,?,sysdate,borepleseq.nextval)";
		try {
			psmt= con.prepareStatement(sql);
			psmt.setString(1,bDto.getBoSequence());
			psmt.setString(2,bDto.getRepeid());
			psmt.setString(3,bDto.getReple());
			int re=psmt.executeUpdate();
			if(re!=0) {
				return true;
			}
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
		return false;
	}

	public LinkedList<BoardDto> boardUpdateForm(String peid, String bosequence) {
		String sql = "SELECT * FROM PEBOARD P FULL OUTER JOIN boardfile F ON p.bosequence = f.bosequence WHERE P.PEID=? AND P.BOSEQUENCE=? AND boavailable = 1";
		try(PreparedStatement psmt=con.prepareStatement(sql);) {
			psmt.setString(1, peid);
			psmt.setString(2, bosequence);
			rs=psmt.executeQuery();
			BoardDto bDto;
			LinkedList<BoardDto> ll = new LinkedList<>();
			while(rs.next()) {
				 bDto = new BoardDto();
				bDto.setBoSequence(rs.getString("bosequence"));
			bDto.setBoTitle(rs.getString("botitle"));
			bDto.setBoContent(rs.getString("bocontent"));
			bDto.setPeid(rs.getString("peid"));
			bDto.setBoDate(rs.getString("bodate"));
			bDto.setBoAvailable(rs.getInt("boavailable"));
			if(rs.getString("bofileori")!=null) {
				bDto.setBoFileOri(rs.getString("bofileori"));
				bDto.setBoFileSer(rs.getString("bofileser"));
				bDto.setFileseq(rs.getString("fileseq"));
			}
			ll.add(bDto);
			}
			return ll;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public BoardDto findFile(String fileIndex) {
		String sql= "select * from boardfile where fileseq=?";
		BoardDto bDto = new BoardDto();
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, fileIndex);
			rs=psmt.executeQuery();
			while(rs.next()) {
				bDto.setBoFileOri(rs.getString("bofileori"));
				bDto.setBoFileSer(rs.getString("bofileser"));
				bDto.setFileseq(rs.getString("fileseq"));
				bDto.setBoSequence(rs.getString("bosequence"));
			}
			return bDto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	public void deleteFile(BoardDto bDto) {
		String sql = "delete from boardfile where fileseq=? and bofileori=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, bDto.getFileseq());
			psmt.setString(2, bDto.getBoFileOri());
			System.out.println(bDto.getBoFileOri());
			System.out.println(bDto.getBoFileSer());
			System.out.println(bDto.getFileseq());
			int re=psmt.executeUpdate();
		if(re!=0) {
			System.out.println("삭제처리");
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void boardUpdate(BoardDto bDto) {
		String sql ="UPDATE PEBOARD SET BOTITLE=?,BOCONTENT=?,BODATE=SYSDATE,BoAvailable=? WHERE PEID=? AND BOSEQUENCE=? ";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, bDto.getBoTitle());
			psmt.setString(2, bDto.getBoContent());
			psmt.setInt(3, bDto.getBoAvailable());
			psmt.setString(4, bDto.getPeid());
			psmt.setString(5, bDto.getBoSequence());
			int re= psmt.executeUpdate();
			if (re!=0) {
				System.out.println("게시판업데이트");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	public boolean rewriteReple(BoardDto bDto) {
		String sql = "UPDATE BOARDREPLE SET REPLE=? WHERE REPEID=? AND RESEQUENCE = ? AND BOSEQUENCE =? ";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1,bDto.getReple());
			psmt.setString(2, bDto.getRepeid());
			psmt.setString(3, bDto.getReSequence());
			psmt.setString(4, bDto.getBoSequence());
			int re=psmt.executeUpdate();
			if(re!=0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	return false;
	}

	public void boarDelete(String bosequence, String peid) {
		String sql = "UPDATE PEBOARD SET BOAVAILABLE = 2 WHERE PEID=? AND BOSEQUENCE=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, peid);
			psmt.setString(2, bosequence);
			int re=psmt.executeUpdate();
			if(re!=0) {
				System.out.println("게시판삭제처리업데이트성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean repledelete(String resequence) {
			String sql="DELETE FROM BOARDREPLE WHERE RESEQUENCE=?";
			try {
				psmt=con.prepareStatement(sql);
				psmt.setString(1, resequence);
				int re=psmt.executeUpdate();
				if(re!=0) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
	}

	public ArrayList<BoardDto> searchData(String data) {
		String sql = "SELECT * FROM PEBOARD WHERE BOAVAILABLE=1 AND BOTITLE LIKE '%'||?||'%' ORDER BY BODATE DESC ";   // setString 에서 "%"+data+"%" 이런식으로도 처리가능  
		ArrayList<BoardDto> al = new ArrayList<>();
		BoardDto bDto;
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1,data);
			rs=psmt.executeQuery();
			while(rs.next()) {
			bDto=new BoardDto();
			bDto.setBoTitle(rs.getString("botitle"));
			bDto.setBoContent(rs.getString("bocontent"));
			bDto.setBoSequence(rs.getString("bosequence"));
			bDto.setBoDate(rs.getString("bodate"));
			bDto.setPeid(rs.getString("peid"));
			bDto.setBoAvailable(rs.getInt("boavailable"));
			al.add(bDto);
			}
			if(al!=null) {
				return al;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public void hitPlus(String bosequence) {
		String sql="UPDATE FROM BOARDCOUNT SET BOHIT 여기서 더하기 WHERE BOSEQUENCE = ?";
		
		
	}


	/*
	 * public void merge() { String
	 * sql="MERGE INTO BOARDFILE F USING DUAL ON (F.BOSEQUENCE=? AND F.FILESEQ=?) WHEN MATCHED THEN UPDATE SET BOFILEORI=?,BOFILESER=? WHEN NOT MATCHED THEN INSERT (F.BOFILEORI,F.BOFILESER)VALUES(?,?,bofileseq.nextval)"
	 * ;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

}
