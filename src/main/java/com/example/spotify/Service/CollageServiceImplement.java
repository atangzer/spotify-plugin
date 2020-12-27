package com.example.spotify.Service;

import java.net.URI;
import java.net.URISyntaxException;

import com.example.spotify.entity.Selection;
import com.example.spotify.entity.Song;

public class CollageServiceImplement implements CollageService{

	@Override
	public String getPlaylistId(String url) {
		String linkID = "";
		URI uri;
		
		try {
			uri = new URI(url);
			String path = uri.getPath();
			linkID = path.substring(path.lastIndexOf('/') + 1);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return linkID;
	}

	@Override
	public void makeCollage(Song song, Selection selection) {
		// TODO Auto-generated method stub
		
	}

}
