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

@WebServlet({ "/index", "/loginForm", "/joinForm", "/dupliCheck", "/memberInsert", "/memberList", "/login",
		"/beforeWithdrawalCheck", "/withdrawalCheck", "/memberInfoUpdate", "/memberInfoUpdateFrom", "/emaildup" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글 깨지는걸 방지하기 위해 utf-8로 인코딩
		response.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String con = request.getContextPath();
		String url = uri.substring(con.length());
		String path = null;
		byte selforedi = 1; // default 값 1이여서 포워드가 default
		MemberManager mm = new MemberManager(request, response); // controller에서 코드를 짜면 너무 지저분해저서

		switch (url) {
		case "/index":
			path = "index.jsp";
			break;
		case "/loginForm":
			mm.logout();
			path = "loginForm.jsp";
			break;
		case "/joinForm":
			path = "joinForm.jsp";
			break;
		case "/dupliCheck":
			mm.dupliCheck();
			break;
		case "/login":
			path = mm.login();
			selforedi = 2;
			// selforedi = (byte) ((path.equals("index.jsp")) ? 2 : 1); // 1이면 로그인 실패 리턴값
			// 스트링으로 수정해야함
			break;
		case "/memberInsert":
			path = mm.memberInsert();
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
		case "/beforeWithdrawalCheck":
			path = "beforeWithdrawalCheck.jsp";
			break;
		case "/withdrawalCheck":
			path = mm.WithdrawalCheck();
			break;
		case "/memberInfoUpdateFrom":
			path = mm.memberInfoUpdateForm();
			break;
		case "/memberInfoUpdate":
			path = mm.memberInfoUpdate();
			selforedi = 2;
			break;
		case "/emaildup":
			mm.emaildup();

			break;
		}

		if (path != null && selforedi == 1) {
			RequestDispatcher dis = request.getRequestDispatcher(path);
			dis.forward(request, response);
		} else if (path != null && selforedi == 2) {
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
