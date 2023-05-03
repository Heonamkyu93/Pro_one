package com.nk.home;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.service.MemberManager;

@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException{  // Was에 요청이 오면 req 객체에 저장 res로 응답 

		req.setCharacterEncoding("utf-8");			//한글 깨지는걸 방지하기 위해 utf-8로 인코딩
		//String uri=req.getRequestURI();
		//String con=req.getContextPath();
		String url= req.getRequestURI().substring(req.getContextPath().length());
		
		String path =null;
		MemberManager mm=new MemberManager(req);			//controller에서 코드를 짜면 너무 지저분해저서
		
		
		switch(url) {
		
		case "/index":
			path="index.jsp";
			break;
		case "/memberInsert":
			path=mm.memberInsert();
			break;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
