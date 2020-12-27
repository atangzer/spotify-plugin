package com.example.spotify.controller;

import java.security.Principal;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HomeController {
	
	private WebClient webClient;
	
	@GetMapping("/")
	public String getHome() {
		return "home";
	}

	@GetMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
	
	@GetMapping("/login")
	public Principal login(Principal principal) {
		return principal;
	}
	
	/*@GetMapping("/redirect")
	public String getRedirect(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){   
	    //String resourceUri = "https://api.spotify.com/v1/me/top/artists";
	    //String resourceUri = "api.spotify.com/v1/login/oauth2/code/spotify-client";
		//String resourceUri = "localhost:8080/login/oauth2/code/spotify-client";
		String resourceUri = "https://api.spotify.com/v1/users/21law25x23pbass5p5xaz2bri";
		
	    String body = webClient
	            .get()
	            .uri(resourceUri)
	            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
	            	      .clientRegistrationId("spotify-client"))
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();

	    return "success";
	}*/
}

