package com.example.spotify.entity;

import java.util.Arrays;

public class Song {
	private Item[] items;

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "Tracks [Items = " + Arrays.toString(items) + "]";
	}

}
