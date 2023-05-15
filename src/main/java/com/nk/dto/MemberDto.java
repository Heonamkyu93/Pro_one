package com.nk.dto;

public class MemberDto {
	private String peId;
	private String pePwd;
	private String peName;
	private String peMail;
	private String pePhoneNumber;
	private String peSequence;
	private int peAge;
	private String pePower;
	private String peSalt;
	
	
	public void setPeSalt(String peSalt) {
		this.peSalt = peSalt;
	}
	public String getPeSalt() {
		return peSalt;
	}
	
	public void setPeSequence(String peSequence)

	{
		this.peSequence = peSequence;
	}
	public String getPeSequence() {
		return peSequence;
	}
	
	
	public void setPeId(String peId) {
		this.peId = peId;
	}

	public String getPeId() {
		return peId;
	}

	public void setPePwd(String pePwd) {
		this.pePwd = pePwd;
	}

	public String getPePwd() {
		return pePwd;
	}

	public void setPeName(String peName) {
		this.peName = peName;
	}

	public String getPeName() {
		return peName;
	}
	public void setPeAge(int peAge) {
		this.peAge=peAge;
	}
	public int getPeAge() {
		return peAge;
	}
	public void setPeMail(String peMail) {
		this.peMail = peMail;
	}

	public String getPeMail() {
		return peMail;
	}

	public void setPePhoneNumber (String pePhoneNumber) {
		this.pePhoneNumber = pePhoneNumber;
	}

	public String getPePhoneNumber() {
		return pePhoneNumber;
	}
	public void setPePower (String pePower) {
		this.pePower=pePower;
	}
	public String getPePower() {
		return pePower;
	}

}
