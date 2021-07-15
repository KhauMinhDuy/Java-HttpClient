package com.khauminhduy.newapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class LinkValidatorSync {

	private static HttpClient client;
	
	public static void main(String[] args) {
		client = HttpClient.newHttpClient();
		
		try {
			Files.lines(Path.of("src/main/resources/urls.txt"))
				.map(LinkValidatorSync::validateLink)
				.forEach(System.out::println);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String validateLink(String link) {
		HttpRequest request = HttpRequest.newBuilder(URI.create(link)).GET().build();
		
		try {
			HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
			return responseToString(response);
		} catch (IOException | InterruptedException e) {
			return String.format("%s -> %s", link, false);
		} 
		
	}
	
	private static String responseToString(HttpResponse<Void> response) {
		int statusCode = response.statusCode();
		boolean success = statusCode >= 200 && statusCode <= 299;
		return String.format("%s -> %s (status: %s)", response.uri(), success, statusCode);
	}
}
