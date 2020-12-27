package com.example.spotify.Service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import com.example.spotify.entity.Song;

public interface SpotifyService {
	
	// n = limit inputed by user
	public Song getTopSongs(int n, OAuth2AuthorizedClient authorizedClient);
}
