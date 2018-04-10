package com.example.memoaccountapp.entity;

public class User {
	
	private int uid;
	private String name;
	private String phone;
	private String password;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int uid, String name, String phone, String password) {
		super();
		this.uid = uid;
		this.name = name;
		this.phone = phone;
		this.password = password;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", phone=" + phone
				+ ", password=" + password + "]";
	}
	
}
