package com.example.spotify.entity;

public class Item {
	private Album album;

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	@Override
	public String toString() {
		return "Item [Album = " + album +"]";
	}
	
}
