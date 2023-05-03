package com.nk.service;

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
		mDto.setPersonId(req.getParameter("PersonId"));
		mDto.setPwd(req.getParameter("pwd"));
		mDto.setPersonName(req.getParameter("personName"));
		mDto.setAge(Integer.parseInt(req.getParameter("age"))); // Integer.parseInt == String 타입의 숫자를 int로 변환
		mDto.setPersonEmail(req.getParameter("personEmail"));
		mDto.setPhoneNumber(req.getParameter("phoneNumber"));
		mDao.memberInsert(mDto);
		return null;
	}

}
