package com.example.spotify.entity;

import java.util.Arrays;

public class Album {
	private Image[] images;
	private String name;
	
	public Image[] getImages() {
		return images;
	}
	public void setImages(Image[] images) {
		this.images = images;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Album [Images = " + Arrays.toString(images) + "]";
	}
	
}
