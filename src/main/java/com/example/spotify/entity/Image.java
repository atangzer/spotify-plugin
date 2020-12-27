package com.example.spotify.entity;

public class Image {
	private int h;
	private int w;
	private String url;
	
	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Image [Height = " + h + ",Width = " + w + ",Link = " + url + "]";
	}
}
