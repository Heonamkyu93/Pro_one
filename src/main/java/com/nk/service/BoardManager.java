package com.nk.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nk.dao.BoardDao;
import com.nk.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardManager {
	HttpServletRequest request;
	HttpServletResponse response;

	public BoardManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String boardInsert() {
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp";
		} else if (loginch.equals("login")) {
		
		BoardDao ba = new BoardDao();
		BoardDto bt = new BoardDto();
		BoardDto bt2;
	//	String upPath = request.getSession().getServletContext().getRealPath("upload");
		String upPath = "C:\\tomcat\\apache-tomcat-9.0.74\\webapps\\Pro_one\\upload";
		int size = 10 * 1024 * 1024;
		// PEBOARD 테이블에 넣을값
		File f = new File(upPath);
		if (!f.isDirectory()) {
			System.out.println("폴더없음");
			f.mkdir();
		}
		try {
			MultipartRequest multi = new MultipartRequest(request, upPath, size, "utf-8",
					new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames(); // 배열
			bt.setBoTitle(multi.getParameter("botitle"));
			bt.setBoContent(multi.getParameter("bocontent"));
			bt.setPeid(multi.getParameter("peid"));
			bt.setBoDate(multi.getParameter("bodate"));
			bt.setBoAvailable(1);
			String seq = ba.boardInsert(bt);
			if (!seq.equals("n")) { // 게시글 인서트 안되면 처리해야함

				while (files.hasMoreElements()) {
					String file = (String) files.nextElement();
					String boFileSer = multi.getFilesystemName(file); // 서버파일이름
					String boFileOri = multi.getOriginalFileName(file); // 오리지날이름
					if (boFileOri == null)
						continue; // 파일 1,3번째는 업로드하고 2번째를 건너뛸경우


					bt2 = new BoardDto();
					// BOARDFILE 테이블에넣을값 //시퀀스값을 구해와야함
					bt2.setBoSequence(seq);
					bt2.setBoFileSer(boFileSer);
					bt2.setBoFileOri(boFileOri);
					ba.fileInset(bt2);
				}
				ba.cloe();
				return "jump.jsp";}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		return null;
	}

	public String boardList() {
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp";
		} else if (loginch.equals("login")) {
		
		
		BoardDao bDao = new BoardDao();
		int cur = 1;
		int listco = 1;
		if (request.getParameter("page") != null) {
			cur = Integer.parseInt(request.getParameter("page"));
			listco = cur * 10 - 9;
		}
		if (request.getParameter("page") != null) {
			cur = Integer.parseInt(request.getParameter("page"));
			listco = cur * 10 - 9;
		}
		ArrayList<BoardDto> al = new ArrayList<>();
		al = bDao.boardList(listco, listco + 9);
		String page = makeHtmlPage(cur);
		String html = makeHtmlList(al);
		request.setAttribute("html", html);
		request.setAttribute("page", page);
		
	}
		return "boardlist.jsp";
	}

	private String makeHtmlPage(int cur) {
		Page bp = new Page();
		BoardDao bDao = new BoardDao();
		int total = bp.totlaPage(bDao.total(), 10);
		int start = bp.startPage(cur, 10);
		int end = bp.endPage(cur, 10, total);
		StringBuilder sb = new StringBuilder();

		sb.append("<ul class='pagination'>");
		if (bp.pre(start)) {
			sb.append("<li class='page-item'>");
			sb.append("<a class=page-link' href='./listPage?page=");
			sb.append(start - 1);
			sb.append("'aria-label='Previous'>");
			sb.append("<span aria-hidden='true'>&laquo;</span>");
			sb.append("</a>");
			sb.append("</li>");
		}
		for (start = start; start <= end; start++) {
			sb.append("<li class='page-item'>");
			sb.append("<a class='page-link' href='./listPage?page=");
			sb.append(start);
			sb.append("'>");
			sb.append(start);
			sb.append("</a>");
			sb.append("</li>");
		}
		if (bp.next(total, end)) {
			sb.append("<li class='page-item'>");
			sb.append("<a class=page-link' href='./listPage?page=");
			sb.append(start);
			sb.append("'aria-label='Next'>");
			sb.append("<span aria-hidden='true'>&raquo;</span>");
			sb.append("</a>");
			sb.append("</li>");
		}
		return sb.toString();
	}

	private String makeHtmlList(ArrayList<BoardDto> al) {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='table table-hover'>");
		sb.append("<tr>");
		sb.append("<th>");
		sb.append("글번호");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("제목");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("작성자");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("작성일");
		sb.append("</th>");
		sb.append("</tr>");
		for (int i = 0; i < al.size(); i++) {
			sb.append("<tr>");
			sb.append("<th>");
			sb.append(al.get(i).getBoSequence());
			sb.append("</th>");
			sb.append("<th>");
			sb.append("<a href='./boardInside?bosequence=");
			sb.append(al.get(i).getBoSequence());
			sb.append("'>");
			sb.append(al.get(i).getBoTitle());
			sb.append("</a>");
			sb.append("</th>");
			sb.append("<th>");
			sb.append(al.get(i).getPeid());
			sb.append("</th>");
			sb.append("<th>");
			sb.append(al.get(i).getBoDate());
			sb.append("</th>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		return sb.toString();
	}

	public String loginCookie() { // 다른 페이지를 갈때 쿠키여부를 확인해서 로그인상태인지 아닌지를 판단하는 메소드
		/*
		 * Cookie[] cList = request.getCookies(); for (int i = 0; i < cList.length &&
		 * cList[i].getName() != null; i++) { if (cList[i].getName().equals("peid")) {
		 * cookie = 1; break; } }
		 */
		HttpSession session = request.getSession();
		byte cookie = 0;
		if (session.getAttribute("peid") != null) {
			cookie = 1;
		}
		String cook = (cookie != 0) ? "login" : "logout";
		return cook;
	}

	public String boardInsertForm() {
		HttpSession session = request.getSession();
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp";
		} else if (loginch.equals("login")) {
			String peid = (String) session.getAttribute("peid");
			request.setAttribute("peid", peid);
			return "boardInsert.jsp";
		}
		return "loginForm.jsp";
	}


	public String boardInside() throws UnsupportedEncodingException { // 조회수, 파일 , 댓글 다 불러와야함
		String bosequence = request.getParameter("bosequence");
		BoardDao bDao = new BoardDao();
		LinkedList<BoardDto> ll = bDao.boardInside(bosequence);
		HttpSession session = request.getSession();
		
		if (ll.get(0).getBoTitle() != null) {
			if(session.getAttribute("peid").equals(ll.get(0).getPeid())) {
				request.setCharacterEncoding("utf-8");
				request.setAttribute("update","<a href='./boardUpdateForm?bosequence="+ll.get(0).getBoSequence()+"&peid="+ll.get(0).getPeid()+"'><button type='button' class='btn btn-primary'>수정</button></a>");
				request.setAttribute("delete","<a href='./boarDelete?bosequence="+ll.get(0).getBoSequence()+"&peid="+ll.get(0).getPeid()+"'><button type='button' class='btn btn-primary'>삭제</button></a>");
			}
			request.setAttribute("repeid",session.getAttribute("peid"));
			request.setAttribute("peid", ll.get(0).getPeid());
			request.setAttribute("botitle", ll.get(0).getBoTitle());
			request.setAttribute("bocontent", ll.get(0).getBoContent());
			request.setAttribute("botitle", ll.get(0).getBoTitle());
			request.setAttribute("bodate", ll.get(0).getBoDate());
			request.setAttribute("bosequence", ll.get(0).getBoSequence());
			if (ll.get(0).getReSequence() != null) { // 리플시퀀스가 널이아니면 댓글가져오기
				String reple = makeHtmlReple(ll);
				request.setAttribute("reple", reple);
				
			}
			if (ll.get(0).getBoFileOri() != null) { // 파일도 마찬가지
				
				request.setAttribute("file",fileDownload(ll)); 
			}

			return "boardInside.jsp";

		} else {
			return "index.jsp?nav=logoutheader.jsp";
		}

	}

	public String fileDownload(LinkedList<BoardDto> ll)  {
		StringBuilder sb = new StringBuilder();	
		LinkedHashSet<String> fileLhs = new LinkedHashSet<>();
		for(int i =0 ;i<ll.size();i++) {
			String file = ll.get(i).getBoFileOri()+"ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺"+ll.get(i).getBoFileSer()+"ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺"+ll.get(i).getFileseq();
			fileLhs.add(file);
		}
		Iterator <String> iter = fileLhs.iterator();
		int i = 1;
		while(iter.hasNext()) {
			
			String removeDuple = iter.next();
			String k[]=removeDuple.toString().split("ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺",3);
			sb.append("<div class='col-md-4' id='prediv"+i+"'>");
			sb.append("<a download href='"+"\\Pro_one\\upload" + "\\"+k[1]+"' id='prefile"+i+"' onclick='pre(this);'>");
			sb.append(k[0]);
			sb.append("</a>");
			sb.append("</div>");
			sb.append("<input type='hidden' value='"+k[2]+"'id='prefilenum"+i+"'name='prefilenum"+i+"'>");
			i++;
			
		}
		return sb.toString();
		
	}

	private String makeHtmlReple(LinkedList<BoardDto> ll) {			//댓글이 여러개일수도 있고 없을수도있음 댓글이 한개여도 파일이 여러개면 여러개가 담김   파일도 마찬가지  
		StringBuilder sb = new StringBuilder();
		LinkedHashSet<String> lhs = new LinkedHashSet<>();
		for(int i=0;i<ll.size();i++) {
			String reple=ll.get(i).getRepeid() + "ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺"+ll.get(i).getReple()+"ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺"+ll.get(i).getRedate()+"ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺"+ll.get(i).getReSequence();
			lhs.add(reple);
		}
		HttpSession session = request.getSession();
		String peid=(String)session.getAttribute("peid");
		Iterator <String> iter = lhs.iterator();
			 while(iter.hasNext()) { 
				 String rePlus=iter.next(); 
			 String k[]=rePlus.toString().split("ωμέриллицаعَبْد ٱللَّٰه ٱبْن عَبْد ٱلْ䨺",4);
			 sb.append("<div class='row'>");
			 sb.append("<div class='col-md-2 b' align='center' id='againrepeid"+k[3]+"'>");
			 sb.append(k[0]);
			 sb.append("</div>");
			 sb.append("<div class='col-md-7 b' id='apple"+k[3]+"'>");
			 sb.append(k[1]);
			 sb.append("</div>");
			 sb.append("<div class='col-md-2 b'>");
			 sb.append(k[2]);
			 sb.append("</div>");
			 sb.append("<div class='col-md-1 b'>");
			 if(peid.equals(k[0])) {
				 sb.append("<button type='button' class='btn btn-primary btn-sm' onclick='repledelete();'>삭제</button>");  //삭제 수정은둘다 ajax
				 sb.append("<button type='button' class='btn btn-default btn-sm' onclick='writeagain("+k[3]+");'>수정</button>");
				 sb.append("<button type='button' class='btn btn-default btn-sm' onclick='rewrite("+k[3]+");'>등록</button>");
			 }
			 sb.append("</div>");
			 sb.append("</div>");
			 
			 }
		return sb.toString();
	}

	public String repleIn() {
		String json=request.getParameter("json");
	//	JsonObject jobj = (JsonObject) JsonParser.parseString(json);
		Gson gson = new Gson();
		BoardDto bDto = gson.fromJson(json, BoardDto.class);	// repedi , reple , boSequence , redate를 가져왔음
		BoardDao bDao = new BoardDao();
		boolean re=bDao.repleIn(bDto);	
		try(PrintWriter pw = response.getWriter();) {
			if(re) {
				pw.print(json);
			}else {
				pw.print("실패");
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String boardUpdateForm() {
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp";
		} else if (loginch.equals("login")) {
			String peid=request.getParameter("peid");
			String bosequence=request.getParameter("bosequence");
			BoardDao bDao = new BoardDao();
	LinkedList<BoardDto> ll = bDao.boardUpdateForm(peid,bosequence);
			if(ll.get(0).getPeid()!=null) {
				request.setAttribute("peid",ll.get(0).getPeid());
				request.setAttribute("botitle",ll.get(0).getBoTitle());
				request.setAttribute("bocontent",ll.get(0).getBoContent());
				request.setAttribute("bodate", ll.get(0).getBoDate());
				request.setAttribute("bosequence", ll.get(0).getBoSequence());
				if(ll.get(0).getBoFileOri()!=null) {
					request.setAttribute("file",fileDownload(ll)); 	
				}
			}
			return "boardUpdateForm.jsp";
		}
		return "loginForm.jsp";
	}

	public String boardUpdate() {		// 오라클merge문을 사용해서 update 없으면 insert   제거할파일을 업데이트 없으면 인서트니깐 새로운 파일처리도 한번에 가능할듯 
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp";
		} else if (loginch.equals("login")) {
			//String upPath = request.getSession().getServletContext().getRealPath("upload");
			String upPath = "C:\\tomcat\\apache-tomcat-9.0.74\\webapps\\Pro_one\\upload";
			int size = 10 * 1024 * 1024;
			// PEBOARD 테이블에 넣을값
			File f = new File(upPath);
			if (!f.isDirectory()) {
				System.out.println("폴더없음");
				f.mkdir();
			}
			BoardDto bDto = new BoardDto();
			BoardDao bDao = new BoardDao();
			try {
				MultipartRequest multi = new MultipartRequest(request, upPath, size, "utf-8",
						new DefaultFileRenamePolicy());
				Enumeration files = multi.getFileNames(); // 배열
				String bosequence=multi.getParameter("bosequence");
				String file1=multi.getParameter("prefilenum1");			
				fileDeleteCheck(file1,bosequence);
				String file2=multi.getParameter("prefilenum2");			
				fileDeleteCheck(file2,bosequence);
				String file3=multi.getParameter("prefilenum3");			
				fileDeleteCheck(file3,bosequence);
				
				
				bDto.setBoSequence(bosequence);
				bDto.setBoTitle(multi.getParameter("botitle"));
				bDto.setBoContent(multi.getParameter("bocontent"));
				bDto.setPeid(multi.getParameter("peid"));
				bDto.setBoDate(multi.getParameter("bodate"));
				bDto.setBoAvailable(1);
				
				bDao.boardUpdate(bDto);
				
				
				while (files.hasMoreElements()) {
					String file = (String) files.nextElement();
					String boFileSer = multi.getFilesystemName(file); // 서버파일이름
					String boFileOri = multi.getOriginalFileName(file); // 오리지날이름
					if (boFileOri == null)
						continue; // 파일 1,3번째는 업로드하고 2번째를 건너뛸경우


					bDto = new BoardDto();
					// BOARDFILE 테이블에넣을값 //시퀀스값을 구해와야함
					bDto.setBoSequence(bosequence);
					bDto.setBoFileSer(boFileSer);
					bDto.setBoFileOri(boFileOri);
					bDao.fileInset(bDto);
				}
				
				bDao.cloe(); 
				}
				
			catch (Exception e) {
			}
			
	}
		return "jump.jsp";
	}
	private String fileDeleteCheck(String file1, String bosequence) {    //delete 붙은 파일을 dao가서 select로 정보 가져오고 그걸로 실제 파일삭제하고나서 디비에 저장된값을 지우자
		String fileIndex;
		if(file1.contains("delete")) {
			
			String[] deleteOriginal = file1.split("/", 2);
			fileIndex=deleteOriginal[0];
			BoardDao bDao = new BoardDao();
			BoardDto bDto = new BoardDto();
			
			bDto=bDao.findFile(fileIndex);
			
			if(bosequence.equals(bDto.getBoSequence())) bDao.deleteFile(bDto);
			String serverName=bDto.getBoFileSer();
			//String upPath = request.getSession().getServletContext().getRealPath("upload")+"//";
			String upPath = "C:\\tomcat\\apache-tomcat-9.0.74\\webapps\\Pro_one\\upload\\";
			upPath+=serverName;
			System.out.println(upPath);
			File f= new File(upPath);
			if(f.exists()) {f.delete();}else {
				return null;
			}
			
		
	}

		return null;
}
}