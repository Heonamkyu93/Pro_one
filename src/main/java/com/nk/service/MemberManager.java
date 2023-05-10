package com.nk.service;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nk.dao.MemberDao;
import com.nk.dto.MemberDto;

public class MemberManager {
	private HttpServletRequest request;
	private HttpServletResponse response;

	public MemberManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String memberInsert() { // 멤버 등록 메소드
		MemberDto mDto = new MemberDto();
		MemberDao mDao = new MemberDao();
		mDto.setPeId(request.getParameter("peid"));
		mDto.setPePwd(request.getParameter("pepwd"));
		mDto.setPeName(request.getParameter("pename"));
		System.out.println(request.getParameter("peage"));
		mDto.setPeAge(Integer.parseInt(request.getParameter("peage"))); // Integer.parseInt == String 타입의 숫자를 int로 변환
		mDto.setPeMail(request.getParameter("pemail"));
		mDto.setPePhoneNumber(request.getParameter("pephonenumber"));
		boolean re = mDao.memberInsert(mDto);
		System.out.println("ddd=" + re);
		if (re) {
			request.setAttribute("re", "회원가입");
			return "index.jsp"; // %%%%% 회원가입후 나올 페이지 만들어야함
		} else {
			request.setAttribute("re", "회원가입실패"); // %%%% 나중에 jsp에 값 찍어야함
			return null;
		}
	}

	public String memberLogin() { // ######쿠키에 정보 등록한거랑 비교 사용안함
		MemberDto mDto = new MemberDto();
		MemberDao mDao = new MemberDao();
		mDto.setPeId(request.getParameter("PeId"));
		mDto.setPePwd(request.getParameter("pePwd"));
		int a = mDao.memberLogin(mDto);
		if (a == 1) {
			// 로그인 성공
		} else {
			// 로그인 실패
		}

		return null;
	}

	public String memberList() { // makehtmllist 메소드와 DAO클래스의 memberList 메소드 재활용을위해 바이트로 어떤 메소드의 호출인지 변별
		String login = loginCookie();
		if (login.equals("logout")) {
			return "loginForm.jsp";
		}
		MemberDao mDao = new MemberDao();
		byte a = 1;
		String gar = "d";
		request.setAttribute("mList", makeHtmlList(a, mDao.memberList(a, gar)));
		return "showList.jsp";
	}

	private String makeHtmlList(byte a, ArrayList<MemberDto> mList) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border='1'>");
		sb.append("<tr>");
		sb.append("<th>");
		sb.append("회원번호");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("아이디");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("이름");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("나이");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("연락처");
		sb.append("</th>");
		sb.append("<th>");
		sb.append("이메일");
		sb.append("</th>");
		sb.append("</tr>");
		for (int i = 0; i < mList.size(); i++) {
			sb.append("<tr>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeSequence());
			sb.append("</th>");
			sb.append("<th>");
			sb.append("<a href='./memberPrivateInfo?peid='");
			sb.append(mList.get(i).getPeId());
			sb.append(">");
			sb.append(mList.get(i).getPeId());
			sb.append("</a>");
			sb.append("</th>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeName());
			sb.append("</th>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeAge());
			sb.append("</th>");
			sb.append("<th>");
			sb.append(mList.get(i).getPePhoneNumber());
			sb.append("</th>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeMail());
			sb.append("</th>");
			if (a == 2) { // 나중에 위에 있는 이메일 전화번호 삭제리스트에서 어느정도 정보를 보여주고 아이디를 클릭했을때 모든정보를 보여주려고 메소드 재활용을 위해
							// a바이트로 변별
				sb.append("<th>");
				sb.append(mList.get(i).getPePhoneNumber());
				sb.append("</th>");
				sb.append("<th>");
				sb.append(mList.get(i).getPeMail());
				sb.append("</th>");
			}
			sb.append("</tr>");

		}

		sb.append("</table>");
		return sb.toString();
	}

	public String memberSearch() {
		MemberDao mDao = new MemberDao();
		byte a = 2;
		request.setAttribute("d", makeHtmlList(a, mDao.memberList(a, request.getParameter("peid"))));
		// mDao.memberSearch(request.getParameter("peid"));
		return null;
	}

	public boolean dupliCheck() { // 회원가입시 아이디 중복체크 ajax 메소드
		MemberDao mDao = new MemberDao();
		byte a = 2;
		if (mDao.memberList(a, request.getParameter("peid")) != null)
			return true;
		else
			return false;

		
		
		
		
		
		
		
		
	}

	public String loginCookie() {			//다른 페이지를 갈때 쿠키여부를 확인해서 로그인상태인지 아닌지를 판단하는 메소드
		Cookie[] cList = request.getCookies();
		byte cookie = 0;
		
		for (int i = 0; i < cList.length && cList[i].getName() != null; i++) {
			System.out.println(cList[i].getName());
			if (cList[i].getName().equals("peid")) {
				cookie = 1;
				break;
			}
		}
		String cook = (cookie!=0)?"login":"logout";
		return cook;
		
	}

	public void logout() {
		
	}
	
	
	
	public byte login() {				//입력하는 아이디와 비밀번호가 디비의 정보와 일치하는지 판단하는 메소드  대소문자 구분해야함 오라클이 대소문자 구분함
		
		MemberDao mDao = new MemberDao();
		byte a = 2;
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		if (peid.equals(list.get(0).getPeId()) && pepwd.equals(list.get(0).getPePwd())) {
			Cookie ck = new Cookie("peid", peid);
			response.addCookie(ck);

			return 2;
		} else {
			request.setAttribute("loginch", "실패");
			return 1;
		}

	}

}
