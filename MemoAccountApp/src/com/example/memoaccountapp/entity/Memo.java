package com.example.memoaccountapp.entity;

public class Memo {
	
	private int mid;
	private User user;
	private String content;
	private String mtime;
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	@Override
	public String toString() {
		return "Memo [mid=" + mid + ", user=" + user + ", content=" + content
				+ ", mtime=" + mtime + "]";
	}
	
}
