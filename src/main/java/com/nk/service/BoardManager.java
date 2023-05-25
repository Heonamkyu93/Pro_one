package com.nk.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		BoardDao ba = new BoardDao();
		BoardDto bt = new BoardDto() ;
		BoardDto bt2;
		String upPath = request.getSession().getServletContext().getRealPath("upload");
		
		int size = 10 * 1024 * 1024;
		// PEBOARD 테이블에 넣을값
		try {
			MultipartRequest multi = new MultipartRequest(request, upPath, size, "utf-8",
					new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames(); // 배열
			bt.setBoTitle(multi.getParameter("botitle"));
			bt.setBoContent(multi.getParameter("bocontent"));
			bt.setPeid(multi.getParameter("peid"));
			bt.setBoDate(multi.getParameter("bodate"));
			bt.setBoAvailable(1);
			String seq=ba.boardInsert(bt);
			System.out.println("시퀀스="+seq);
			if (!seq.equals("n")) {			// 게시글 인서트 안되면 처리해야함
			while (files.hasMoreElements()) {
				int i =0;
				i++;
				System.out.println(i);
				String file = (String) files.nextElement();
				String boFileSer = multi.getFilesystemName(file); // 서버파일이름
				String boFileOri = multi.getOriginalFileName(file); // 오리지날이름
				
				
				
				
				System.out.println("파일이름"+boFileOri);
				System.out.println("오리지날"+multi.getOriginalFileName("bofile"));
				File f = new File(upPath);
				if (!f.isDirectory()) {
					System.out.println("폴더없음");
					f.mkdir();
				}
				bt2= new BoardDto();
				// BOARDFILE 테이블에넣을값 //시퀀스값을 구해와야함
				bt2.setBoSequence(seq);
				bt2.setBoFileSer(boFileSer);
				bt2.setBoFileOri(boFileOri);
				ba.fileInset(bt2);

			
			}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		return null;
	}

	public String boardDel() {
		BoardDao ba = new BoardDao();
		ba.boardDel();

		return null;
	}

	public void contentInside() {
		BoardDao bDao = new BoardDao();
		String botitle = request.getParameter("botitle");
		bDao.contentInside(botitle);

	}

	public String boardList() {
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
		HttpSession session = request.getSession();
		String power = (String) session.getAttribute("pepower");
		String include = "loginheader.jsp";
		if (power.equals("3")) {
			include = "adminheader.jsp";
		}
		ArrayList<BoardDto> al = new ArrayList<>();
		al=bDao.boardList(listco, listco + 9);
		String page = makeHtmlPage(cur);
		String html = makeHtmlList(al);
		request.setAttribute("include", include);
		request.setAttribute("html", html);
		request.setAttribute("page", page);
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
			sb.append("<a href='./memberInfoUpdateFrom?peid=");
			sb.append(al.get(i).getBoTitle());
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
		if (cook.equals("login") && session.getAttribute("pepower").equals("3"))
			cook = "admin";
		return cook;
	}

	public String boardInsertForm() {
		HttpSession session = request.getSession();
		String loginch = loginCookie();
		if (loginch.equals("logout")) {
			return "loginForm.jsp?nav=logoutheader.jsp";
		} else if (loginch.equals("login")) {
			String peid = (String) session.getAttribute("peid");
			request.setAttribute("peid", peid);
			return "boardInsert.jsp";
		} else if (loginch.equals("admin")) {
			return "adminInsertBoard.jsp";
		}
		return "index.jsp?nav=logoutheader.jsp";
	}

	public String filetest() {
		return null;
	}

}
