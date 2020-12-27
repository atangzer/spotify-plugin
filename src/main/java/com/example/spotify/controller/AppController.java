package com.example.spotify.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.spotify.Service.SpotifyService;
import com.example.spotify.entity.Selection;
import com.example.spotify.entity.Song;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@RestController
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
	    String resourceUri = "api.spotify.com/v1/login/oauth2/code/spotify-client";
		//String resourceUri = "https://api.spotify.com/v1/me";
		
	    String body = webClient
	            .get()
	            .uri(resourceUri)
	            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
	            	      .clientRegistrationId("spotify-client"))
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
	
	@GetMapping("/Collage")
	//public Song getCollage(@ModelAttribute("selection") @Valid Selection selection, Model model) {
	public Song getCollage() {	
		Song song = spotifyService.getTopSongs(10);
		//Song song = spotifyService.getTopSongs(selection.getRows() * selection.getColumns());
		//System.out.println(song);
		return song;
		//return "test";
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
		String resourceUri = "api.spotify.com/v1/login/oauth2/code/spotify-client";
		
		Mono<String> retrievedResource = webClient
		            .get()
		            .uri(resourceUri)
		            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
		            	      .clientRegistrationId("spotify-client"))
		            .retrieve()
		            .bodyToMono(String.class);
		
		return retrievedResource.map(string -> string);

	}
	
}
