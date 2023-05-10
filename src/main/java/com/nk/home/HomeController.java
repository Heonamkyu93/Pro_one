package com.nk.home;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.service.MemberManager;

@WebServlet({ "/index", "/loginForm", "/joinForm", "/dupliCheck", "/memberInsert", "/memberList", "/login" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글 깨지는걸 방지하기 위해 utf-8로 인코딩
		String uri = request.getRequestURI();
		String con = request.getContextPath();
		String url = uri.substring(con.length());
		String path = null;
		byte selforedi = 1; // default 값 1이여서 포워드가 default
		MemberManager mm = new MemberManager(request,response); // controller에서 코드를 짜면 너무 지저분해저서

		switch (url) {
		case "/index":
			path = "index.jsp";
			break;
		case "/loginForm":
			path = "loginForm.jsp";
			break;
		case "/joinForm":
			path = "joinForm.jsp";
			break;
		case "/dupliCheck":
			PrintWriter out = response.getWriter();
			if (mm.dupliCheck()) {
				out.print("y");
			} else {
				out.print("n");
			}
			break;
		case "/login":
			// path = ;
			selforedi = mm.login();   //1이면 로그인 실패  리턴값 스트링으로 수정해야함
			
			if (selforedi == 1) {
				path="index.jsp";
				break;
			}else {
				
				path="index.jsp";
				break;
			}

		case "/memberInsert":
			path = mm.memberInsert();
			break;
		case "/memberLogin":
			path = mm.memberLogin();
			break;
		case "/memberList":
			path = mm.memberList();
			break;
		case "/memberSearch":
			path = mm.memberSearch();
			break;
		case "/memberPrivateInfo":
			path = mm.memberSearch();
			break;
		}

		if (path != null && selforedi == 1) {
			RequestDispatcher dis = request.getRequestDispatcher(path);
			dis.forward(request, response);
		} else if(path != null && selforedi ==2) {
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
