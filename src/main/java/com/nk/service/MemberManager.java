package com.nk.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
		Secure sr = new Secure();
		String pepwd = request.getParameter("pepwd");
		String[] userInfo = sr.securePwd(pepwd);
		mDto.setPeSalt(userInfo[0]);
		mDto.setPePwd(userInfo[1]);
		mDto.setPeId(request.getParameter("peid"));
		mDto.setPeName(request.getParameter("pename"));
		mDto.setPeAge(Integer.parseInt(request.getParameter("peage"))); // Integer.parseInt == String 타입의 숫자를 int로 변환
		mDto.setPeMail(request.getParameter("pemail"));
		mDto.setPePhoneNumber(request.getParameter("pephonenumber"));
		boolean re = mDao.memberInsert(mDto);
		if (re) {
			request.setAttribute("re", "회원가입");
			return "index.jsp"; // %%%%% 회원가입후 나올 페이지 만들어야함
		} else {
			request.setAttribute("re", "회원가입실패"); // %%%% 나중에 jsp에 값 찍어야함
			return null;
		}
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
		/*
		 * sb.append("<th>"); sb.append("나이"); sb.append("</th>"); sb.append("<th>");
		 * sb.append("연락처"); sb.append("</th>"); sb.append("<th>"); sb.append("이메일");
		 * sb.append("</th>");
		 */
		sb.append("</tr>");
		for (int i = 0; i < mList.size(); i++) {
			sb.append("<tr>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeSequence());
			sb.append("</th>");
			sb.append("<th>");
			sb.append("<a href='./memberInfoUpdateFrom?peid=");
			sb.append(mList.get(i).getPeId());
			sb.append("'>");
			sb.append(mList.get(i).getPeId());
			sb.append("</a>");
			sb.append("</th>");
			sb.append("<th>");
			sb.append(mList.get(i).getPeName());
			sb.append("</th>");
			/*
			 * sb.append("<th>"); sb.append(mList.get(i).getPeAge()); sb.append("</th>");
			 * sb.append("<th>"); sb.append(mList.get(i).getPePhoneNumber());
			 * sb.append("</th>"); sb.append("<th>"); sb.append(mList.get(i).getPeMail());
			 * sb.append("</th>");
			 */
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

	public void dupliCheck() throws IOException { // 회원가입시 아이디 중복체크 ajax 메소드
		PrintWriter out = response.getWriter();
		MemberDao mDao = new MemberDao();
		byte a = 2;
		if (mDao.memberList(a, request.getParameter("peid")) != null)
			out.print("y");
		else
			out.print("n");

	}

	public String loginCookie() { // 다른 페이지를 갈때 쿠키여부를 확인해서 로그인상태인지 아닌지를 판단하는 메소드
		Cookie[] cList = request.getCookies();
		byte cookie = 0;
		for (int i = 0; i < cList.length && cList[i].getName() != null; i++) {
			if (cList[i].getName().equals("peid")) {
				cookie = 1;
				break;
			}
		}
		String cook = (cookie != 0) ? "login" : "logout";
		return cook;

	}

	public void logout() {
		Cookie[] cookie = request.getCookies();
		String coname = "";
		HttpSession session = request.getSession();
		for (int i = 0; i < cookie.length; i++) {
			coname = cookie[i].getName();
			if (coname.equals("peid")) {
				session.removeAttribute("peid");
				cookie[i].setMaxAge(0);
				response.addCookie(cookie[i]);
			}
		}
	}

	public String login() { // 입력하는 아이디와 비밀번호가 디비의 정보와 일치하는지 판단하는 메소드 대소문자 구분해야함 오라클이 대소문자 구분함 로그인시 쿠키삭제후 등록
		logout();
		MemberDao mDao = new MemberDao();
		Secure sr = new Secure();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		byte a = 2;
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		if (list == null) {
			request.setAttribute("loginch", "실패");
			return "loginForm.jsp";
		}

		String sal = list.get(0).getPeSalt();
		String[] userInfo = sr.securePwd(pepwd, sal);
		pepwd = userInfo[1];
		if (!list.get(0).getPestatus().equals("1")) {
			return "loginForm.jsp?name=a";
		}
		if (peid.equals(list.get(0).getPeId()) && pepwd.equals(list.get(0).getPePwd())) {
			HttpSession session = request.getSession();
			session.setAttribute("peid", peid);
			Cookie ck = new Cookie("peid", peid);
			response.addCookie(ck);
			return "index.jsp";
		} else {

			return "loginForm.jsp";
		}

	}

	public String WithdrawalCheck() { // 회원탈퇴메소드
		MemberDao mDao = new MemberDao();
		Secure sr = new Secure();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		byte a = 2;
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		String sal = list.get(0).getPeSalt();
		String[] userInfo = sr.securePwd(pepwd, sal);
		String re = "beforeWithdrawalCheck.jsp";
		pepwd = userInfo[1];
		if (pepwd.equals(list.get(0).getPePwd())) { // 두값이 같으면 회원탈퇴처리
			re = mDao.WithdrawalCheck(peid, pepwd);
			return re;
		} else { // 틀리다면 beforewi 로다시
			return re;
		}

	}

	public String memberInfoUpdate() {
		MemberDao mDao = new MemberDao();
		MemberDto mDto = new MemberDto();
		mDto.setPeId(request.getParameter("peid"));
		mDto.setPeName(request.getParameter("pename"));
		mDto.setPePhoneNumber(request.getParameter("pephonenumber"));
		mDto.setPeMail(request.getParameter("peemail"));

		boolean re = mDao.memberInfoUpdate(mDto);
		if (re) {
			return "index.jsp";
		} else {
			return "personalInfo.jsp";
		}

	}

	public String memberInfoUpdateForm() {
		MemberDao mDao = new MemberDao();
		String peid = null;
		if (request.getParameter("peid") != null) {
			peid = request.getParameter("peid");
		} else {
			HttpSession session = request.getSession();
			peid = (String) session.getAttribute("peid");
		}
		byte a = 2;
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		request.setAttribute("peid", list.get(0).getPeId());
		request.setAttribute("pename", list.get(0).getPeName());
		request.setAttribute("pephonenumber", list.get(0).getPePhoneNumber());
		request.setAttribute("peemail", list.get(0).getPeMail());
		return "personalInfo.jsp";
		// mDao.memberInfoUpdateForm(peid);
	}
	public void joinEmail() {
		
		Properties pro = new Properties();
	//	pro.put("mail.smtp.host", "smtp.gmail.com");
		pro.put("mail.smtp.host", "smtp.naver.com");
		pro.put("mail.smtp.port", "587");
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.starttls.enable", "true");
		pro.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(pro, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("heonamkyu93@naver.com", "hnk9383215");
			}
		});
		
		String receiver = "projectproonetest@gmail.com"; // 메일 받을 주소
		String title = "가입인증 메일입니다";
		String content = "<h2 style='color:blue'>안녕하세요</h2>"; //"+"<h3>인증번호입니다.</h3><br>"+"<h4>"+random+"<h4>";
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("heonamkyu93@naver.com", "관리자", "utf-8"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(title);
			message.setContent(content, "text/html; charset=utf-8");

			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	public void emaildup() throws IOException {          // 메일 중복체크   중복된 이메일이 없으면 인증메일까지 처리 랜덤수 보내고 값을 유효성 체크 
		MemberDao mDao = new MemberDao();
		String pemail = request.getParameter("pemail");
		PrintWriter pw = response.getWriter();
		boolean re = mDao.emaildup(pemail);
		response.setCharacterEncoding("utf-8");
		if (re) {
			pw.print("중복된 이메일이 존재합니다.");

		} else {
			pw.print("중복된 이메일이 없습니다.인증 메일이 발송되었으니 확인 바랍니다.");
			MailJava mj = new MailJava();
			int random = (int)(Math.random()*1000000);
			HttpSession session = request.getSession();
			session.setAttribute("random", random);
			mj.sendMail(pemail,random);
		}
	}

}
