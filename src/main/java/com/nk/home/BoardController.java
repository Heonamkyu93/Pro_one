package com.nk.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.service.BoardManager;
import com.nk.service.UpFile;

@WebServlet({ "/board","/boardList","/listPage","/boardInsert","/boardInsertForm","/filetest" })
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setCharacterEncoding("utf-8"); // 한글 깨지는걸 방지하기 위해 utf-8로 인코딩	
		String uri = request.getRequestURI();
		String con = request.getContextPath();							///########  어드민은 그냥 인덱스에서만 찍고 다른 페이지 싹 없애고 어드민 관련 코드 싹 걷어내야함 게시판등록 dao 한메소드에서 if문걸어서 싹 처리해야함
		String url = uri.substring(con.length());
		String path = null;
		int redifo = 1;
		BoardManager bm = new BoardManager(request,response);
		UpFile f = new UpFile(request,response);
		switch (url) {
		case "/boardInsert":			//게시판 디비에 입력
			path = bm.boardInsert();
			redifo=2;
			break;
		case "/boardInsertForm":		//게시판 입력폼으로 
			path = bm.boardInsertForm();
			break;
		case "/boardList":				//게시판 리스트 첫화면
			path = bm.boardList();
			break;
		case "/listPage":				//게시판 페이징에서 앞,뒤로가기
			path = bm.boardList();
			break;
		
		}


		if (path != null && redifo == 1) {
			RequestDispatcher dis = request.getRequestDispatcher(path);
			dis.forward(request, response);
		} else if (path != null && redifo == 2) {
			response.sendRedirect(path);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
