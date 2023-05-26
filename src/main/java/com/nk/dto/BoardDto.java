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
	private int bodisLike;
	private String boFileOri; 
	private String boFileSer;
	private String repeid;
	private String reple;
	private String redate;
	private String reSequence;
	
	
	public String getRepeid() {
		return repeid;
	}

	public void setRepeid(String repeid) {
		this.repeid = repeid;
	}

	public String getReple() {
		return reple;
	}

	public void setReple(String reple) {
		this.reple = reple;
	}

	public String getRedate() {
		return redate;
	}

	public void setRedate(String redate) {
		this.redate = redate;
	}

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

	

	public int getBodisLike() {
		return bodisLike;
	}

	public void setBodisLike(int bodisLike) {
		this.bodisLike = bodisLike;
	}

	public String getReSequence() {
		return reSequence;
	}

	public void setReSequence(String reSequence) {
		this.reSequence = reSequence;
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
