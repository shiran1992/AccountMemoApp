package com.example.memoaccountapp.entity;

public class Income {
	
	private int inid;
	private User user;
	private String type;
	private String address;
	private String note;
	private String account;
	private String intime;
	public Income() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getInid() {
		return inid;
	}
	public void setInid(int inid) {
		this.inid = inid;
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
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	@Override
	public String toString() {
		return "Income [inid=" + inid + ", user=" + user + ", type=" + type
				+ ", address=" + address + ", note=" + note + ", account="
				+ account + ", intime=" + intime + "]";
	}
	
}
