package com.example.spotify.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.example.spotify.entity.Selection;
import com.example.spotify.entity.Song;

public class CollageService {
	
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
	
	public static void makeCollage(Song song, Selection selection) throws IOException {
		// TODO Auto-generated method stub
		int dW = selection.getDisplayW();
		int dH = selection.getDisplayH();
		int rows = selection.getRows();
		int col = selection.getColumns();
		
		String[] urls = new String[song.getItems().length];
		
		for (int i = 0; i < urls.length; i++) {
			try {
				if (song.getItems()[i].getAlbum().getImages()[0] != null) urls[i] = (song.getItems()[i].getAlbum().getImages()[0].getUrl());
				else if (song.getItems()[i].getAlbum().getImages()[1] != null) urls[i] = (song.getItems()[i].getAlbum().getImages()[1].getUrl());
				else if (song.getItems()[i].getAlbum().getImages()[2] != null) urls[i] = (song.getItems()[i].getAlbum().getImages()[2].getUrl());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		BufferedImage images[] = new BufferedImage[urls.length];
		
		for (int j = 0; j < images.length; j++) {
			images[j] = ImageIO.read(new URL(urls[j]));
		}
		
		BufferedImage collage = new BufferedImage(dW, dH, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = collage.createGraphics();
		
		int iW;
		int iH = 0;
		float percent = 100.0f;
		
		if (dH > dW) {
			do {
				percent -= 10.0f;
				iW = (int)((dW * (percent / 100.0f))) / col;
			} while ((iW * col) > dH);
			iH = iW;
		} else {
			do {
				percent -= 10.0f;
				iH = (int)((dH * (percent / 100.0f))) / rows;
			} while ((iH * col) > dW);
			iW = iH;
		}
		
		graphics.dispose();
		
		File file = new File("test.jpeg");
		
		ImageIO.write(collage, "jpeg", file);
		
	}
}