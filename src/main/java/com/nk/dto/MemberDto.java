package com.nk.dto;

public class MemberDto {
	private String personId;
	private String pwd;
	private String personName;
	private int age;
	private String personEmail;
	private String phoneNumber;

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge(int age) {
		return age;
	}
	
	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	

}
