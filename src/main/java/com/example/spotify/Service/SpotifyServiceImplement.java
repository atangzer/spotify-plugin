package com.example.spotify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.spotify.entity.Song;

@Service
public class SpotifyServiceImplement implements SpotifyService{
	@Autowired
	private WebClient webClient;

	@Override
	public Song getTopSongs(int n) {
		String path = "https://api.spotify.com/v1/me/top/tracks" + "?limit=" + Integer.toString(n);
		
		Song songs = webClient
						.get()
						.uri(path)
						.retrieve()
						.bodyToMono(Song.class)
						.block();
		return songs;
	}
	
}
