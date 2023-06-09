package com.nk.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.service.MemberManager;

@WebServlet({ "/index", "/loginForm", "/joinForm", "/dupliCheck", "/memberInsert", "/memberList", "/login",
		"/beforeWithdrawalCheck", "/withdrawalCheck", "/memberInfoUpdate", "/memberInfoUpdateFrom", "/emaildup" , "/pwdchange", 
		"/resendmail" , "/certi" , "/logout" , "/beforepwdchangeForm" , "/repwd","/findidemailch","/findIdForm","/findpwdemailch","/findPwdForm"})
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
		case "/index":			//시작페이지
			path = mm.index();
			selforedi = 2;
			break;
		case "/loginForm":		//로그인 양식으로 이동
			path = mm.loginForm();
			break;
		case "/joinForm":		//회원가입 양식으로 이동
			path = "joinForm.jsp";
			break;
		case "/dupliCheck":		//아이디 중복체크 ajax
			mm.dupliCheck();
			break;
		case "/login":		// 로그인
			path = mm.login();
			selforedi = 2;
			break;
		case "/memberInsert":		//회원가입 db에 입력
			path = mm.memberInsert();
			break;
		case "/memberList":			//회원 목록
			path = mm.memberList();
			break;
		case "/memberSearch":		//회원검색 아직 x
			path = mm.memberSearch();
			break;
		case "/memberPrivateInfo":	//회원 자세한 정보
			path = mm.memberSearch();
			break;
		case "/beforeWithdrawalCheck":	//탈퇴전에 비밀번호 더블체크양식으로 이동
			path = "beforeWithdrawalCheck.jsp";
			break;
		case "/withdrawalCheck":		//탈퇴전에 비밀번호 더블체크 
			path = mm.WithdrawalCheck();
			break;
		case "/memberInfoUpdateFrom": //개인정보 수정양식으로 이동
			path = mm.memberInfoUpdateForm();
			break;
		case "/memberInfoUpdate":		// 개인정보수정
			path = mm.memberInfoUpdate();
			selforedi = 2;
			break;
		case "/emaildup":		//이메일 중복체크 ajax
			mm.emaildup();
			break;
		case "/tes":
			break;
		case "/logout":
			path=mm.tryLogout();
			selforedi = 2;
			break;
		case "/certi":
			path=mm.certiCheck();
			selforedi = 2;
			break;
		case "/resendmail":		//회원가입 인증번호 다시보내기 
			mm.resend();
			break;
		case "/beforepwdchangeForm":			//비밀번호 변경전 비밀번호 더블체크폼으로이동       
			path=mm.beforepwdchangeForm();
			break;
		case "/pwdchange":			//비밀번호 변경전 비밀번호 더블체크값받아와서 디비랑비교       
			path=mm.pwdchange();
			break;
		case"/repwd":				// 비밀번호 변경하는 값 받아와서 디비에 저장  
		   path=mm.repwd();
		break;
		case"/findidemailch":				// 아이디 찾기 가입한 계정에 메일이 있는지 확인   
			  mm.findidemailch();
			break;
		case"/findIdForm":				// 아이디 찾기 이메일입력폼으로이동   
			 path =mm.findIdForm();
			 selforedi = 2;
			break;
		case"/findpwdemailch":				// 비밀번호 찾기 아이디 이메일 입력후 성공하면 임시비밀번호 메일로 쏴주기   
			 path =mm.findpwdemailch();
			break;
		case"/findPwdForm":				// 비밀번호 찾기 아이디,이메일입력폼으로이동   
			 path =mm.findPwdForm();
			 selforedi = 2;
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
