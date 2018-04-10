package com.example.memoaccountapp.entity;

public class Expend {
	
	private int eid;
	private User user;
	private String type;
	private String address;
	private String note;
	private String account;
	private String outtime;
	public Expend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	@Override
	public String toString() {
		return "Expend [eid=" + eid + ", user=" + user + ", type=" + type
				+ ", address=" + address + ", note=" + note + ", account="
				+ account + ", outtime=" + outtime + "]";
	}
	
}
