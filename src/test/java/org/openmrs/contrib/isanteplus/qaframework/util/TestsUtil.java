package org.openmrs.contrib.isanteplus.qaframework.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class TestsUtil {
	
	
	
	public static void main(String [] args) {
	
	 String restUrl  = "ws/rest/v1/idgen/nextIdentifier?source=1";
	 
	 String familyName = TestsUtil.generateRandomString();

	 String givenName = TestsUtil.generateRandomString();
	 
     String familyName2 = TestsUtil.generateRandomString();

     String givenName2 = TestsUtil.generateRandomString();
	 
	 String username = "admin";

     String password = "Admin123";
	 
     String jsonData1 = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/8d6c993e-c2cc-11de-8d13-0010c6dffd0f\",\"type\":\"Location\"}}],\"use\":\"official\",\"type\":{\"text\":\"iSantePlus ID\"},\"system\":\"http://isanteplus.org/openmrs/fhir2/3-isanteplus-id\",\"value\":\"" + TestsUtil.generateIsantePlusId(restUrl,username,password) + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";
	 
	 
	 System.out.println(jsonData1);
	 
	}

	public static void addPatient(String endPointToAppend, String jsonData, String username, String password)
			throws IOException {

		String url = getBaseUrl() + endPointToAppend;

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

	private static String getBaseUrl() throws IOException {
		File file = new File("src/test/resources/test.properties");

		InputStream input = new FileInputStream(file.getAbsolutePath());

		Properties prop = new Properties();
		// load a properties file
		prop.load(input);

		String baseUrl = prop.getProperty("webapp.url");
		return baseUrl;

	}

	public static String generateIsantePlusId(String restUrl, String username, String password) {

		BASE64Encoder enc = new sun.misc.BASE64Encoder();
		String userpassword = username + ":" + password;
		StringBuffer response = new StringBuffer();

		String encodedAuthorization = enc.encode(userpassword.getBytes());

		URL obj;
		try {
			String url = getBaseUrl() + restUrl;
			obj = new URL(url);
			HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();

			postConnection.setConnectTimeout(6000);
			postConnection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
			postConnection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));

			String output;

			while ((output = reader.readLine()) != null) {
				response.append(output);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString().substring(32, 38);

	}

}
