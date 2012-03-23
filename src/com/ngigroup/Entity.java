package com.ngigroup;

public class Entity {
	
	private String Bookmark;
	private String URL;
	public String getBookmark() {
		return Bookmark;
	}
	public void setBookmark(String bookmark) {
		Bookmark = bookmark;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	
	public Entity(String bookmark,String URL){
		this.Bookmark = bookmark;
		this.URL = URL;
	}

}
