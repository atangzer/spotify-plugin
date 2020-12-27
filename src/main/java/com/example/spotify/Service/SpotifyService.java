package com.example.spotify.Service;

import com.example.spotify.entity.Song;

public interface SpotifyService {
	
	// n = limit inputed by user
	public Song getTopSongs(int n);
}
