package com.khauminhduy.newapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LinkValidatorAsync {

	private static HttpClient client;

	public static void main(String[] args) {
		client = HttpClient.newBuilder()
						.connectTimeout(Duration.ofSeconds(5))
						.followRedirects(Redirect.NORMAL)
						.build();

		try {
			var futures = Files.lines(Path.of("src/main/resources/urls.txt"))
										.map(LinkValidatorAsync::validateLink)
										.collect(Collectors.toList());
			futures.stream()
					.map(CompletableFuture::join)
					.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static CompletableFuture<String> validateLink(String link) {
		HttpRequest request = HttpRequest.newBuilder(URI.create(link))
					.timeout(Duration.ofSeconds(3))
					.GET()
					.build();
		return client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
					.thenApply(LinkValidatorAsync::responseToString)
					.exceptionally(e -> String.format("%s -> %s", link, false));

	}

	private static String responseToString(HttpResponse<Void> response) {
		int statusCode = response.statusCode();
		boolean success = statusCode >= 200 && statusCode <= 299;
		return String.format("%s -> %s (status: %s)", response.uri(), success, statusCode);
	}
}
