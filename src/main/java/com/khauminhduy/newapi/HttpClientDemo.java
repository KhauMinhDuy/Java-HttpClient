package com.khauminhduy.newapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientDemo {
	
	public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		
		
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://pluralsight.com")).build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
