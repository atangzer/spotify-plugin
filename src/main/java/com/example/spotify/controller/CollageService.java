package com.example.spotify.controller;

import java.awt.Color;
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
	
	public static void makeCollage(Song song, Selection selection) throws IOException {
		//int dW = selection.getDisplayW();
		//int dH = selection.getDisplayH();
		int dW = 1940;
		int dH = 1940;
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
		
		if (dH > dW) {
			do {
				iW = (int)(dW / col);
			} while ((iW * col) > dH);
			iH = iW;
		} else {
			do {
				iH = (int)(dH / rows);
			} while ((iH * col) > dW);
			iW = iH;
		}
		
		int offX = 0;
		int offY = 0;
		
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
		
		File file = new File("collages\\collage.png");
		
		ImageIO.write(collage, "png", file);
		
	}
}