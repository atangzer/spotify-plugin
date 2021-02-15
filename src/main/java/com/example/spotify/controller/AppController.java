package com.example.spotify.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.spotify.Service.SpotifyService;
import com.example.spotify.entity.Selection;
import com.example.spotify.entity.Song;
import com.example.spotify.exceptions.NoTopSongsException;

@Controller
public class AppController {
	
	@Autowired
	private SpotifyService spotifyService;
	
	private WebClient webClient;
	
	public AppController(WebClient webClient) {
		this.webClient = webClient;
	}
	
	@GetMapping("/")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/set-up")
	public String getSetUp(Model model) {
		// dimensions
		Selection selection = new Selection();
		model.addAttribute("selection", selection);
		return "set-up";
	}
	
	@PostMapping("/collage")
	public String getCollage(@ModelAttribute("selection") @Valid Selection selection, Model model) {
		int n = selection.getRows(); 

		if ((n * n) > 50) {
			n = 50;
		} else {
			n *= n;
		}
		
		Song song = spotifyService.getTopSongs(n);

		try {
			CollageService.makeCollage(song, selection);
		} catch (IOException e) {
			return "redirect:/error-page";
		} catch (NoTopSongsException e) {
			return "redirect:/no-songs-error";
		}

		return "collage";
	}
	
	@GetMapping(value = "/download", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getDownload() throws IOException {
		File file = new File("collages\\collage.png");
		InputStream in = FileUtils.openInputStream(file);
		file.delete();
		return IOUtils.toByteArray(in);
	}
	
	
}
