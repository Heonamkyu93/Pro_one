package com.nk.dto;


public class BoardDto {
	private String peid;
	private String boContent;
	private String boTitle;
	private String boDate;
	private int boAvailable;
	private String boSequence;
	private int boHit;
	private int boLike;
	private int disLike;
	private String boFileOri; 
	private String boFileSer;
	
	
	
	
	public int getBoHit() {
		return boHit;
	}

	public void setBoHit(int boHit) {
		this.boHit = boHit;
	}

	public int getBoLike() {
		return boLike;
	}

	public void setBoLike(int boLike) {
		this.boLike = boLike;
	}

	public int getDisLike() {
		return disLike;
	}

	public void setDisLike(int disLike) {
		this.disLike = disLike;
	}

	public String getBoFileOri() {
		return boFileOri;
	}

	public void setBoFileOri(String boFileOri) {
		this.boFileOri = boFileOri;
	}

	public String getBoFileSer() {
		return boFileSer;
	}

	public void setBoFileSer(String boFileSer) {
		this.boFileSer = boFileSer;
	}

	public void setPeid(String peid) {
		this.peid = peid;
	}

	public String getPeid() {
		return peid;
	}

	public void setBoContent(String boContent) {
		this.boContent = boContent;
	}

	public String getBoContent() {
		return boContent;
	}

	public void setBoTitle(String boTitle) {
		this.boTitle = boTitle;
	}

	public String getBoTitle() {
		return boTitle;
	}

	public void setBoDate(String boDate)  {
		this.boDate = boDate;
	}

	public String getBoDate() {
		return boDate;
	}
	public void setBoAvailable(int boAvailable) {
		this.boAvailable=boAvailable;
	}
	public int getBoAvailable() {
		return boAvailable;
	}
	public void setBoSequence(String boSequence) {
		this.boSequence=boSequence;
	}
	public String getBoSequence() {
		return boSequence;
	}
}
