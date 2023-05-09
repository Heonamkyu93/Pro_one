package com.nk.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.nk.dao.MemberDao;
import com.nk.dto.MemberDto;

public class MemberManager {
	private HttpServletRequest req;

	public MemberManager(HttpServletRequest req) {
		this.req = req;
	}

	public String memberInsert() { // 멤버 등록 메소드
		MemberDto mDto = new MemberDto();
		MemberDao mDao = new MemberDao();
		mDto.setPeId(req.getParameter("peid"));
		mDto.setPePwd(req.getParameter("pepwd"));
		mDto.setPeName(req.getParameter("pename"));
		System.out.println(req.getParameter("peage"));
		mDto.setPeAge(Integer.parseInt(req.getParameter("peage"))); // Integer.parseInt == String 타입의 숫자를 int로 변환
		mDto.setPeMail(req.getParameter("pemail"));
		mDto.setPePhoneNumber(req.getParameter("pephonenumber"));
		boolean re = mDao.memberInsert(mDto);
		System.out.println("ddd=" + re);
		if (re) {
			req.setAttribute("re", "회원가입");
			return "index.jsp"; // %%%%% 회원가입후 나올 페이지 만들어야함
		} else {
			req.setAttribute("re", "회원가입실패"); // %%%% 나중에 jsp에 값 찍어야함
			return null;
		}
	}

	public String memberLogin() {				//######쿠키에 정보 등록한거랑 비교
		MemberDto mDto = new MemberDto();
		MemberDao mDao = new MemberDao();
		mDto.setPeId(req.getParameter("PeId"));
		mDto.setPePwd(req.getParameter("pePwd"));
		int a = mDao.memberLogin(mDto);
		if (a == 1) {
			// 로그인 성공
		} else {
			// 로그인 실패
		}

		return null;
	}

	public String memberList() {			//makehtmllist 메소드와 DAO클래스의 memberList 메소드 재활용을위해 바이트로 어떤 메소드의 호출인지 변별 
		MemberDao mDao = new MemberDao();	
		byte a = 1;
		String gar = "d";
		req.setAttribute("mList", makeHtmlList(a, mDao.memberList(a, gar)));
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
			if (a == 2) {				//나중에 위에 있는 이메일 전화번호 삭제리스트에서 어느정도 정보를 보여주고 아이디를 클릭했을때 모든정보를 보여주려고 메소드 재활용을 위해 a바이트로 변별  
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
		req.setAttribute("d", makeHtmlList(a, mDao.memberList(a, req.getParameter("peid"))));
		// mDao.memberSearch(req.getParameter("peid"));
		return null;
	}

	public boolean dupliCheck() {
		MemberDao mDao = new MemberDao();
		byte a = 2;
		if (mDao.memberList(a, req.getParameter("peid")) != null) {
			return true;
		} else {
			return false;
		}
	}
}
