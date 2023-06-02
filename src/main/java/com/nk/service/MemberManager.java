package com.nk.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
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
		String pemail = request.getParameter("pemail");
		String pepwd = request.getParameter("pepwd");
		String peid = request.getParameter("peid");
		String[] userInfo = sr.securePwd(pepwd);
		mDto.setPeSalt(userInfo[0]);
		mDto.setPePwd(userInfo[1]);
		mDto.setPeId(peid);
		mDto.setPeName(request.getParameter("pename"));
		mDto.setPeAge(Integer.parseInt(request.getParameter("peage"))); // Integer.parseInt == String 타입의 숫자를 int로 변환
		mDto.setPeMail(request.getParameter("pemail"));
		mDto.setPePhoneNumber(request.getParameter("pephonenumber"));
		boolean re = mDao.memberInsert(mDto);
		if (re) {
			request.setAttribute("pemail", pemail);
			mailCertiForm(pemail);
			return "mailCertification.jsp";
		} else {
			return"index.jsp?nav=logoutheader.jsp";
		}
	}

	public void mailCertiForm(String pemail) { // 인증메일 + 난수 보내는 메소드 random이 난수고 스트링타입으로 변환된 난수가 certi 그걸 암호화한값이 sha +
												// salt 이값을 세션에 저장했다 사용자가 난수 certi를 반환하면 이걸 sec에 salt랑 같이 넣어서 세션에
		// 저장된 값이랑 비교해서 맞으면 회원가입 틀리면 mailcertiform으로
		MailJava mj = new MailJava();
		Secure sc = new Secure();
		HttpSession session = request.getSession();
// session.invalidate();
		if (session.getAttribute("salt") != null && session.getAttribute("sha") != null) {
			session.removeAttribute("salt");
			session.removeAttribute("sha");
		}
		int random = (int) (Math.random() * 1000000);
		String certi = random + "";
		String secur[] = sc.securePwd(certi); // 반환되는값 0이 salt 1이 암호화된값
		String salt = secur[0];
		String sha = secur[1];
		session.setAttribute("salt", salt);
		session.setAttribute("sha", sha);
		session.setMaxInactiveInterval(10 * 60);
		mj.sendMail(pemail, random);
	}

	public String certiCheck() { // 반환된 난수 검증하는 메소드
		String certi = request.getParameter("certi");
		String pemail = request.getParameter("pemail");
		HttpSession session = request.getSession();
		String salt = (String) session.getAttribute("salt");
		String sha = (String) session.getAttribute("sha");
		Secure sc = new Secure();
		String re[] = sc.securePwd(certi, salt);
		if (re[1].equals(sha)) {
			session.removeAttribute("sha");
			session.removeAttribute("salt");
			// 값이 동일하면 인증성공 인증에 성공하면 db에 저장된 status를 1로 변경해줘야함
			MemberDao mDao = new MemberDao();
			boolean res = mDao.mialChekFin(pemail);
			if (res) { // status 1로 변경이 안될경우 만들어야함
				return "loginForm.jsp";
			}
			return "loginForm.jsp";

		} else {
			return "mailCertification.jsp";
		}
	}

	public String memberList() { // makehtmllist 메소드와 DAO클래스의 memberList 메소드 재활용을위해 바이트로 어떤 메소드의 호출인지 변별
		String login = loginCookie();
		if (login.equals("logout")) {
			return "loginForm.jsp";
		}
		int cur = 1;
		int listco = 1;
		MemberDao mDao = new MemberDao();
		String memberTable = makeHtmlList(mDao.memberList(listco, listco + 9));
		String memPage = makeHtmlPage(cur);
		request.setAttribute("mList", memberTable);
		request.setAttribute("mpage", memPage);
		return "showList.jsp";
	}

	private String makeHtmlPage(int cur) {
		Page mp = new Page();
		MemberDao mDao = new MemberDao();
		int total = mp.totlaPage(mDao.toal(), 10);
		int start = mp.startPage(cur, 10);
		int end = mp.endPage(cur, 10, total);
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class='pagination'>");
		if (mp.pre(start)) {
			sb.append("<li class='page-item'>");
			sb.append("<a class=page-link' href='./listPage?page=");
			sb.append(start - 1);
			sb.append("'aria-label='Previous'>");
			sb.append("<span aria-hidden='true'>&laquo;</span>");
			sb.append("</a>");
			sb.append("</li>");
		}
		for (start = start; start <= end; start++) {
			sb.append("<li class='page-item'>");
			sb.append("<a class='page-link' href='./listPage?page=");
			sb.append(start);
			sb.append("'>");
			sb.append(start);
			sb.append("</a>");
			sb.append("</li>");
		}
		if (mp.next(total, end)) {
			sb.append("<li class='page-item'>");
			sb.append("<a class=page-link' href='./listPage?page=");
			sb.append(start);
			sb.append("'aria-label='Next'>");
			sb.append("<span aria-hidden='true'>&raquo;</span>");
			sb.append("</a>");
			sb.append("</li>");
		}
		return sb.toString();
	}

	private String makeHtmlList(ArrayList<MemberDto> mList) {
		StringBuilder sb = new StringBuilder();

		sb.append("<div class='container'>");

		sb.append("<div class='row'>");
		sb.append("<div class='col-md-2'>");
		sb.append("</div>");
		sb.append("<div class='col-md-1 b'>");
		sb.append("회원번호");
		sb.append("</div>");
		sb.append("<div class='col-md-4 b'>");
		sb.append("아이디");
		sb.append("</div>");
		sb.append("<div class='col-md-1 b'>");
		sb.append("이름");
		sb.append("</div>");
		sb.append("<div class='col-md-2 b'>");
		sb.append("가입일");
		sb.append("</div>");
		sb.append("<div class='col-md-2'>");
		sb.append("</div>");
		sb.append("</div>");
		for (int i = 0; i < mList.size(); i++) {

			sb.append("<div class='row'>");
			sb.append("<div class='col-md-2'>");
			sb.append("</div>");
			sb.append("<div class='col-md-1 b'>");
			sb.append(mList.get(i).getPeSequence());
			sb.append("</div>");
			sb.append("<div class='col-md-4 b'>");
			sb.append("<a href='./memberInfoUpdateFrom?peid=");
			sb.append(mList.get(i).getPeId());
			sb.append("'>");
			sb.append(mList.get(i).getPeId());
			sb.append("</a>");
			sb.append("</div>");
			sb.append("<div class='col-md-1 b'>");
			sb.append(mList.get(i).getPeName());
			sb.append("</div>");
			sb.append("<div class='col-md-2 b'>");
			sb.append(mList.get(i).getPejoinDate());
			sb.append("</div>");
			sb.append("<div class='col-md-2'>");
			sb.append("</div>");
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String memberSearch() {
		MemberDao mDao = new MemberDao();
		request.setAttribute("d", makeHtmlList(mDao.memberSearch(request.getParameter("peid"))));
		return null;
	}

	public void dupliCheck() throws IOException { // 회원가입시 아이디 중복체크 ajax 메소드
		PrintWriter out = response.getWriter();
		MemberDao mDao = new MemberDao();
		if (mDao.memberSearch(request.getParameter("peid")) != null)
			out.print("y");
		else
			out.print("n");

	}

	public String loginCookie() { // 다른 페이지를 갈때 쿠키여부를 확인해서 로그인상태인지 아닌지를 판단하는 메소드
		/*
		 * Cookie[] cList = request.getCookies(); for (int i = 0; i < cList.length &&
		 * cList[i].getName() != null; i++) { if (cList[i].getName().equals("peid")) {
		 * cookie = 1; break; } }
		 */
		HttpSession session = request.getSession();
		byte cookie = 0;
		if (session.getAttribute("peid") != null) {
			cookie = 1;
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
		logout(); // 세션 쿠키를 삭제하는 부분을 따로 메소드로 만들면 비밀번호 변경이나 탈퇴시 더블체크하는 부분에서 이메소드를 재활용할수있다
		MemberDao mDao = new MemberDao();
		Secure sr = new Secure();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		ArrayList<MemberDto> list = mDao.memberSearch(peid);

		if (list == null) {
			return "loginForm.jsp";
		}
		String sta = list.get(0).getPestatus();
		if (sta.equals("0")) {       //0이면 회원탈퇴
			return "joinForm.jsp";
		}
		String sal = list.get(0).getPeSalt();
		String[] userInfo = sr.securePwd(pepwd, sal);
		pepwd = userInfo[1];
		if (!list.get(0).getPestatus().equals("1")) {
			String pemail = list.get(0).getPeMail();
			request.setAttribute("alert", "테스트");
			request.setAttribute("pemail", pemail);
			mailCertiForm(pemail);
			return "mailCertification.jsp";
		}
		if (peid.equals(list.get(0).getPeId()) && pepwd.equals(list.get(0).getPePwd())) {
			HttpSession session = request.getSession();
			session.setAttribute("peid", peid);
			session.setAttribute("pepower", list.get(0).getPePower());
			Cookie ck = new Cookie("peid", peid);
			response.addCookie(ck);

			return "index.jsp?nav=loginheader.jsp";
		} else {

			return "loginForm.jsp?nav=logoutheader.jsp";
		}

	}

	public String WithdrawalCheck() { // 회원탈퇴메소드
		MemberDao mDao = new MemberDao();
		MemberDto mDto = new MemberDto();
		Secure sr = new Secure();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		ArrayList<MemberDto> list = mDao.memberSearch(peid);
		String sal = list.get(0).getPeSalt();
		String[] userInfo = sr.securePwd(pepwd, sal);
		String re = "beforeWithdrawalCheck.jsp";
		pepwd = userInfo[1];
		mDto.setPeId(peid);
		mDto.setPePwd(pepwd);
		if (pepwd.equals(list.get(0).getPePwd())) { // 두값이 같으면 회원탈퇴처리
			re = mDao.WithdrawalCheck(mDto);       //status를 0으로 만든다
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
			return "index.jsp?nav=loginheader.jsp";
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
		ArrayList<MemberDto> list = mDao.memberSearch(peid);
		request.setAttribute("peid", list.get(0).getPeId());
		request.setAttribute("pename", list.get(0).getPeName());
		request.setAttribute("pephonenumber", list.get(0).getPePhoneNumber());
		request.setAttribute("peemail", list.get(0).getPeMail());
		return "personalInfo.jsp";
		// mDao.memberInfoUpdateForm(peid);
	}

	public void emaildup() { // 메일 중복체크 중복된 이메일이 없으면 인증메일까지 처리 랜덤수 보내고 값을 유효성 체크
		MemberDao mDao = new MemberDao();
		String pemail = request.getParameter("pemail");

		try (PrintWriter pw = response.getWriter();) {
			MemberDto mDto = mDao.emaildup(pemail);
			response.setCharacterEncoding("utf-8");
			mDao.close();
			if (mDto != null) {
				pw.print("중복된 값이 있습니다.");
			} else {
				pw.print("중복된 이메일이 없습니다.");
			}
		} catch (Exception e) {

		}

	}

	public void resend() { // 인증번호 다시보내는 메소드
		String pemail = request.getParameter("pemail");
		mailCertiForm(pemail);

		try (PrintWriter pw = response.getWriter();) {
			pw.print("통신");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String pwdchange() { // 비밀번호 변경전 더블체크 비밀번호가 동일한지 체크하고 새로운 비밀번호 입력할 form으로 이동
		Secure sr = new Secure();
		MemberDao mDao = new MemberDao();
		String peid = request.getParameter("peid");
		String pepwd = request.getParameter("pepwd");
		ArrayList<MemberDto> list = mDao.memberSearch(peid);
		String salt = list.get(0).getPeSalt();
		String hx[] = sr.securePwd(pepwd, salt);
		if (hx[1].equals(list.get(0).getPePwd())) {
			request.setAttribute("peid", peid);
			return "rePwdInsertForm.jsp";
		} else {
			return "beforePwdChangeForm.jsp";
		}

	}

	public String repwd() { // 변경할 비밀번호 받아와서 새로등록하는 메소드
		MemberDao mDao = new MemberDao();
		MemberDto mDto = new MemberDto();
		Secure sr = new Secure();
		String peid = request.getParameter("peid");
		String rePwd = request.getParameter("pepwd");
		String hex[] = sr.securePwd(rePwd); // 0이 salt salt까지 다 바꾼다
		mDto.setPeId(peid);
		mDto.setPePwd(hex[1]);
		mDto.setPeSalt(hex[0]);
		boolean re = mDao.rePwd(mDto);
		mDao.close();
		if (re) { // 디비에 저장 성공이면 처리
			logout();
			System.out.println("성공");
			return "loginForm.jsp";
		} else { // 비밀번호 변경이 실패한경우
			System.out.println("실패");
			return "rePwdInsertForm.jsp";
			
		}

	}

	public String index() {
		String status = loginCookie();
		if (status.equals("login")) {
			return "index.jsp?nav=loginheader.jsp";
		} else {
			return "index.jsp?nav=logoutheader.jsp";
		}
	}

	public String loginForm() {
		String status = loginCookie();
		if (status.equals("login")) {
			return "index.jsp?nav=loginheader.jsp";
		} else {
			return "loginForm.jsp";
		}

	}

	public String tryLogout() {
		logout();
		return "index.jsp?nav=logoutheader.jsp";
	}

	public String beforepwdchangeForm() {
		String status = loginCookie();
		if (!status.equals("login")) {
			return "loginForm.jsp";
		} else {
			String peid = request.getParameter("peid");
			request.setAttribute("peid", peid);

			return "beforePwdChangeForm.jsp";
		}
	}

	public void findidemailch() { // 아이디찾기 ajax 이메일이 가입된거 확인하면 메일로 아이디를 찾아준다
		MemberDao mDao = new MemberDao();
		String pemail = request.getParameter("pemail");
		MailJava mj = new MailJava();
		try (PrintWriter pw = response.getWriter();) {
			MemberDto mDto = mDao.emaildup(pemail);
			JsonObject jo = new JsonObject();
			if (mDto != null) {
				mj.sendMailFindId(pemail, mDto.getPeId());
				jo.addProperty("al", "이메일을 확인해보세요");
				jo.addProperty("url","./loginForm" );
				pw.print(jo);
			} else {
				jo.addProperty("al", "가입된 이메일이 없습니다.");
				jo.addProperty("url","./findIdForm" );
				pw.print(jo);
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			mDao.close();
		}

	}

	public String findIdForm() {
		return "findIdForm.jsp";
	}

	public String findPwdForm() {
		return "findPwdForm.jsp";
	}

	public String findpwdemailch() {
		String pemail = request.getParameter("pemail");
		String peid = request.getParameter("peid");
		MemberDao mDao = new MemberDao();
		MemberDto mDto= mDao.emaildup(pemail);
		
			try(PrintWriter pw = response.getWriter();) {
				JsonObject jo = new JsonObject();
				if(mDto!=null) {
					if(mDto.getPeId().equals(peid)) {    //null이 아니면 이메일정보는 디비에 있는거고 이퀄스도 맞으면 이메일로 불러온 정보가 아이디랑도 일치하는거니깐 임의로 비밀번호를 만들고 보내주면 된다.
						SecureRandom random;
						try {
							random = SecureRandom.getInstance("SHA1PRNG");
							byte[] bytes = new byte[16];
							random.nextBytes(bytes);
							String imPwd1 = new String(Base64.getEncoder().encode(bytes)); // 임시비밀번호 이거는 사용자한테 메일로 줘야하는값이고 이걸 암호화해서 디비에 저장해야한다
							String imPwd=imPwd1.substring(0, 10);
							MailJava mj = new MailJava();
							mj.sendMailFindPwd(imPwd, pemail);
							Secure sr = new Secure();
							String hex[] =sr.securePwd(imPwd);   //0이 솔트 1이 해싱된 비번 여기서 새로운 비번 솔트값을 가지고 디비에 저장
							mDto.setPePwd(hex[1]);
							mDto.setPeSalt(hex[0]);
							mDao.rePwd(mDto);
							jo.addProperty("al", "이메일로 임시비밀번호가 발송되었습니다.");
							jo.addProperty("url","./loginForm" );
							pw.print(jo);
							mDao.close();
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {								//이메일은 맞지만 이퀄스가 아니면 아이디가 틀린거니 빠꾸 멕이면 된다
						jo.addProperty("al", "아이디를 다시확인하세요");
						jo.addProperty("url","./findPwdForm" );
						pw.print(jo);
						mDao.close();
					}
				}else {									//이메일이 틀리면 아이디도 못불러오니 이것도 빠구 
					jo.addProperty("al", "이메일을 다시확인하세요");
					jo.addProperty("url","./findPwdForm" );
					pw.print(jo);
					mDao.close();
				}		
					
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				mDao.close();
			}
			
			
			
			
			
		
		
		
		
		
		
		
		
		return null;
	}
}
