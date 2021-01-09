package com.example.spotify.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
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
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Controller
public class AppController {
	
	@Autowired
	private SpotifyService spotifyService;
	
	private WebClient webClient;
	
	public AppController(WebClient webClient) {
		this.webClient = webClient;
	}
	
	// TODO: Refactor
	@GetMapping("/redirect")
	public String getRedirect(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){   
	    String resourceUri = "api.spotify.com/v1/login/oauth2/code/spotify";
		//String resourceUri = "https://api.spotify.com/v1/me";
		
	    String body = webClient
	            .get()
	            .uri(resourceUri)
	            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
	            	      .clientRegistrationId("spotify"))
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();

	    //return "song";
	    return body;
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
	//public Song getCollage() {	
		//Song song = spotifyService.getTopSongs(10);
		Song song = spotifyService.getTopSongs(selection.getRows() * selection.getColumns());
		//System.out.println(song);
		try {
			CollageService.makeCollage(song, selection);
		} catch (IOException e) {
			return "redirect:/error-page";
		}
		return "collage";
		//return song;
		//return "test";/
	}
	
	@GetMapping("/self")
	public JsonNode getSelf() {
		String resourceUri = "https://api.spotify.com/v1/me";
		
		JsonNode body = webClient
						.get()
						.uri(resourceUri)
						.retrieve()
						.bodyToMono(JsonNode.class)
						.block();
		return body;
	}
	
	@GetMapping("/token")
	Mono<String> getToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		String resourceUri = "api.spotify.com/v1/login/oauth2/code/spotify";
		
		Mono<String> retrievedResource = webClient
		            .get()
		            .uri(resourceUri)
		            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
		            	      .clientRegistrationId("spotify"))
		            .retrieve()
		            .bodyToMono(String.class);
		
		return retrievedResource.map(string -> string);

	}
	
	@GetMapping(value = "/download", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getDownload() throws IOException {
		File file = new File("collages\\collage.png");
		InputStream in = FileUtils.openInputStream(file);
		file.delete();
		return IOUtils.toByteArray(in);
	}
	
	
}
