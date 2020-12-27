package com.example.spotify.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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
	
	private OAuth2AuthorizedClient authorizedClient;
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
	
	@GetMapping("/test")
	public Song getTest() {
		Song song = spotifyService.getTopSongs(10, authorizedClient);
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
