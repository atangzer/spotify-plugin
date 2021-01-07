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
		
		int iW = 0;
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
		
		int offX = 0;
		int offY = 0;
		
		if (dH > dW) {
			offX = (int)(dW * ((100.0f - percent) / 100.0f)) / (col + 1);
			offY = (int)(dH * (iH * rows)) / (rows + 1);
		} else {
			offX = (int)(dW - (iW * col)) / (col + 1);
			offY = (int)(dH * ((100.0f - percent) / 100.0f)) / (rows + 1);
		}
		
		int posX = offX;
		int posY = offY;
		
		int p = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				graphics.drawImage(images[p], posX, posY, iW, iH, null);
				posX += iW + offX;
				if (p < images.length - 1) p++;
				else p = 0;
			}
			
			posX = offX;
			
			posY += iH + offY;
		}
		
		graphics.dispose();
		
		File file = new File("collages\\collage.jpeg");
		
		ImageIO.write(collage, "jpeg", file);
		
	}
}