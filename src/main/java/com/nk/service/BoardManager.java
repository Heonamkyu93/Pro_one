package com.nk.service;


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
	
	public String boardInsert()  {
		BoardDao ba = new BoardDao();
		BoardDto bt = new BoardDto();
		bt.setPeid(request.getParameter("peid"));
		bt.setBoTitle(request.getParameter("botitle"));
		bt.setBoContent(request.getParameter("bocontent"));
		bt.setBoAvailable(1);
		boolean re=ba.boardInsert(bt);
		if(re) {
			
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
		String botitle=request.getParameter("botitle");
		bDao.contentInside(botitle);
		
		
		
		
	}
	
	
	
	
	
}
