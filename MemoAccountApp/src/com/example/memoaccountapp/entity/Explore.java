package com.example.memoaccountapp.entity;

public class Explore {

	private int eid;
	private String title;
	private String url;
	private String img;
	public Explore(int eid, String title, String url, String img) {
		super();
		this.eid = eid;
		this.title = title;
		this.url = url;
		this.img = img;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "Explore [eid=" + eid + ", title=" + title + ", url=" + url
				+ ", img=" + img + "]";
	}
}
