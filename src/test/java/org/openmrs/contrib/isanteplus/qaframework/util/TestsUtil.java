package org.openmrs.contrib.isanteplus.qaframework.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class TestsUtil {
	

	public static void addPatient(String url, String jsonData, String username, String password) throws IOException {

		BASE64Encoder enc = new sun.misc.BASE64Encoder();
		String userpassword = username + ":" + password;

		String encodedAuthorization = enc.encode(userpassword.getBytes());

		URL obj = new URL(url);
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();

		postConnection.setConnectTimeout(6000);
		postConnection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
		postConnection.setDoOutput(true);
		postConnection.setRequestProperty("Content-Type", "application/json, charset = UTF-8");
		postConnection.setRequestMethod("POST");

		OutputStream os = postConnection.getOutputStream();
		System.out.println("...Creating a patient using the FHIR api....");
		os.write(jsonData.getBytes("UTF-8"));
		System.out.println("...Done creating a patient using the FHIR api....");
		os.flush();
		os.close();
		new BufferedInputStream(postConnection.getInputStream());

	}

	public static String generateRandomUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	public static String generateCodeST() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}
	
	public static String generateCodeNational() {
		Random rnd = new Random();
		int number = rnd.nextInt(99999);
		return String.format("%05d", number);
	}

} 