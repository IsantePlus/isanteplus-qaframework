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

	public static void main(String[] args) {
		
		String familyName = generateRandomString();
		
		String givenName = generateRandomString();
		
	    String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	    
	    System.out.println(jsonData);
		

	}

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

	public static String generateRandomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

}
