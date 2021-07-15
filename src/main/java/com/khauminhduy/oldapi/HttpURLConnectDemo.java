package com.khauminhduy.oldapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectDemo {

	public static void main(String[] args) {
		try {
			URL url = new URL("https://pluralsight.com");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Java 1.1");
			if(connection.getResponseCode() == 200) {
				System.out.println("Success");
			} else {
				System.out.println("Something wrong there!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
