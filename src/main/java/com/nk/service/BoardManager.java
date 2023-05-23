package com.nk.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.dao.BoardDao;
import com.nk.dto.BoardDto;

public class BoardManager {
	HttpServletRequest request;
	HttpServletResponse response;

	public BoardManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String boardInsert() {
		BoardDao ba = new BoardDao();
		BoardDto bt = new BoardDto();
		bt.setPeid(request.getParameter("peid"));
		bt.setBoTitle(request.getParameter("botitle"));
		bt.setBoContent(request.getParameter("bocontent"));
		bt.setBoAvailable(1);
		boolean re = ba.boardInsert(bt);
		if (re) {
			
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
			listco = cur * 10 -	9 ;
		}
		ArrayList<BoardDto> al = bDao.boardList(listco, listco+9);
		String page = makeHtmlPage(cur);
		String html = makeHtmlList(al);
		request.setAttribute("html", html);
		request.setAttribute("page", page);
		return "boardlist.jsp";
	}

	private String makeHtmlPage(int cur) {
		Page bp = new Page();
		BoardDao bDao = new BoardDao();
		int total=bp.totlaPage(bDao.total(),10);
		int start=bp.startPage(cur,10);
		int end=bp.endPage(cur, 10, total);
		StringBuilder sb = new StringBuilder();
		sb.append("<table border='1'>");
		sb.append("<tr>");
		if(bp.pre(start)) {
			sb.append("<th>");
			sb.append("<a href='./listPage?page=");
			sb.append(start-1);
			sb.append("'>");
			sb.append("<");
			sb.append("</a>");
			sb.append("</th>");
		}
		for (start=start; start <= end; start++) {
			sb.append("<th>");
			sb.append("<a href='./listPage?page=");
			sb.append(start);
			sb.append("'>");
			sb.append(start);
			sb.append("</a>");
			sb.append("</th>");
		}
		if(bp.next(total, end)) {
			sb.append("<th>");
			sb.append("<a href='./listPage?page=");
			sb.append(start);
			sb.append("'>");
			sb.append(">");
			sb.append("</a>");
			sb.append("</th>");
		}
		sb.append("</tr>");
		sb.append("</table>");
		return sb.toString();
	}

	private String makeHtmlList(ArrayList<BoardDto> al) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border='1'>");
		sb.append("<tr>");
		sb.append("<th>");
		sb.append("글번호");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("제목");
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
			sb.append(al.get(i).getBoDate());
			sb.append("</th>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		return sb.toString();
	}

}
