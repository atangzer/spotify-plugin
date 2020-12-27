package com.example.spotify.Service;

import com.example.spotify.entity.Selection;
import com.example.spotify.entity.Song;

public interface CollageService {
	public String getPlaylistId(String url);
	
	public void makeCollage(Song song, Selection selection);
}
