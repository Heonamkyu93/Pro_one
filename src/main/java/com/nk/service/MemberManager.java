package com.nk.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import javax.mail.PasswordAuthentication;
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
		String pepwd = request.getParameter("pepwd");
		String[] userInfo = securePwd(pepwd);
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
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		byte a = 2;
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		if (list == null) {
			request.setAttribute("loginch", "실패");
			return "loginForm.jsp";
		}

		String sal = list.get(0).getPeSalt();
		String[] userInfo = securePwd(pepwd, sal);
		pepwd = userInfo[1];
		if (peid.equals(list.get(0).getPeId()) && pepwd.equals(list.get(0).getPePwd())) {
			HttpSession session = request.getSession();
			session.setAttribute("peid", peid);
			Cookie ck = new Cookie("peid", peid);
			response.addCookie(ck);
			return "index.jsp";
		} else {
			request.setAttribute("loginch", "실패");
			return "loginForm.jsp";
		}

	}

	private String[] securePwd(String pepwd, String sal) { // 로그인시 이용
		String pePwd = pepwd;
		String hex = "";
		String userInfo[] = new String[2];
		String hexSalt = "";
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			// SALT 생성
			String salt = sal;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 평문 암호화
			md.update(pePwd.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			// 평문암호화 + 솔트
			hexSalt = hex + salt;
			md.update(hexSalt.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			userInfo[0] = salt;
			userInfo[1] = hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userInfo;

	}

	public String[] securePwd(String pwd) { // db에 저장되는 값은 salt , (salt+해싱)을 해싱한 값 회원가입시 이용
		String pepwd = pwd;
		String hex = "";
		String userInfo[] = new String[2];
		String hexSalt = "";
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			// SALT 생성
			String salt = new String(Base64.getEncoder().encode(bytes));
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 평문 암호화
			md.update(pepwd.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			// 평문암호화 + 솔트
			hexSalt = hex + salt;
			md.update(hexSalt.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			userInfo[0] = salt;
			userInfo[1] = hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/*
	 * public void page() { MemberDao mDao = new MemberDao(); int page = 1; // 시작페이지
	 * int pageNumber = 10; // 한페이지에 출력할게시물수 ArrayList<MemberDto> mList =
	 * mDao.memberList((byte) 1, "쓸모없는값"); int totalCount = mList.size(); // 전체 게시물
	 * 카운트 int totalPage = (int) Math.ceil(totalCount / (double) pageNumber);
	 * 
	 * }
	 */

	public String WithdrawalCheck() { // 회원탈퇴메소드
		MemberDao mDao = new MemberDao();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		byte a = 2;
		ArrayList<MemberDto> list = mDao.memberList(a, peid);
		String sal = list.get(0).getPeSalt();
		String[] userInfo = securePwd(pepwd, sal);
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
			return"personalInfo.jsp";
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
	
	public void resetPwd() {
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("projectproonetest@gmail.com","dhponnucpfrmrquw");
		//dhponnucpfrmrquw
	}
	public void mail() {
		
		
		
		
		
	}

	public void emaildup() throws IOException {
		MemberDao mDao = new MemberDao();
		String pemail = request.getParameter("pemail");
		PrintWriter pw = response.getWriter();
		boolean re= mDao.emaildup(pemail);
		response.setCharacterEncoding("utf-8");
		if(re) {
			pw.print("중복된 이메일이 존재합니다.");
				
		}else {
			pw.print("중복된 이메일이 없습니다.");
		}
	}

}
