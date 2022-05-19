package org.openmrs.contrib.isanteplus.qaframework.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class TestsUtil {
	
	
	public static void addPatient(String url, String username, String password) throws IOException {

		String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code National\"},\"system\":\"http://localhost:8000/openmrs/fhir2/5-code-national\",\"value\":\"01581\"}],\"active\":true,\"name\":[{\"id\":\"" + generateRandomUUID() + "\",\"family\":\"Kevin\",\"given\":[\"Tan\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";
			
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
	
	private static String generateRandomUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

}
