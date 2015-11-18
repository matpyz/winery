package winery.server;

import static org.junit.Assert.*;


import org.junit.Test;

public class JSONOperationsTest {
	
	/*
	 * Test odbierania prawdiłowego komunikatu JSON dla modułu Wojtka.
	 */
	@Test
	public void testRightJSONFromApp() {
		String data = 	"{" +
			"	\"type\": \"raport\", " +
			"	\"user_id\": \"user_id_v\"," +
			"	\"user_pass\": \"user_pass_hash\"," +
			"	\"code\": \"code_of_event\"," +
			"	\"place\": [" +
			"	          \"x\", " +
			"	          \"y\", " +
			"	          \"z\"], " +
			"	\"other\": \"text\", " +
			"	\"data\": \"data\" " +
			"}";
		JSONObject jsonObject = JSONOperations.parseJSONToObject(data);
		
		assertEquals("raport", jsonObject.getType());
		assertEquals("user_id_v", jsonObject.getUser_id());
		assertEquals("user_pass_hash", jsonObject.getUser_pass());
		assertEquals("x", jsonObject.getPlace().get(0));
		assertEquals("y", jsonObject.getPlace().get(1));
	}
	
	/*
	 * Test zachowywania się funkcji przekształcającej JSON-a do klasy Javy w przypadku
	 * otrzymania JSON-a z jedynie częścią wymaganych informacji.
	 * WNIOSEK Z TESTÓW - JSONObject z polami dla każdego możliwego JSON-a,
	 * a każdy korzysta tylko z interesujących do pól; reszta NULL.
	 */
	@Test
	public void testOtherJSON() {
		String data = 	"{" +
				"	\"user_id\": \"user_id_v\"," +
				"	\"user_pass\": \"user_pass_hash\"," +
				"	\"code\": \"code_of_event\"," +
				"	\"other\": \"text\", " +
				"	\"data\": \"data\" " +
				"}";
		JSONObject jsonObject = JSONOperations.parseJSONToObject(data);
		
		assertNotEquals(null, jsonObject);
		assertEquals(null, jsonObject.getType());
		assertEquals("user_id_v", jsonObject.getUser_id());
	}
	
	/*
	 * Test zachowania się funkcji przekształcającej JSON-a do klasy Javy w przypadku
	 * otrzymania zupełnie innego JSON-a, w którym nie ma ani jednego odpowiedniego pola.
	 */
	@Test
	public void testRealyOtherJSON() {
		String data = 	"{" +
				"	\"user\": \"user_id_v\"," +
				"	\"price\": \"price_v\"," +
				"}";
		JSONObject jsonObject = JSONOperations.parseJSONToObject(data);
		
		assertEquals(null, jsonObject);
	}
	
	@Test
	public void testParseObjectToJSONString() {
		String data = 	"{" +
				"	\"user_id\": \"user_id_v\"," +
				"	\"user_pass\": \"user_pass_hash\"," +
				"	\"code\": \"code_of_event\"," +
				"	\"other\": \"text\", " +
				"	\"data\": \"data\" " +
				"}";
		JSONObject jsonObject = JSONOperations.parseJSONToObject(data);
		String jsonString = JSONOperations.parseObjectToJSONString(jsonObject);
		assertEquals("{\"type\":null,"
				+ "\"user_id\":\"user_id_v\","
				+ "\"user_pass\":\"user_pass_hash\","
				+ "\"code\":\"code_of_event\","
				+ "\"place\":null,"
				+ "\"other\":\"text\","
				+ "\"data\":\"data\"}", jsonString);
		//System.out.println(jsonString);
	}

}
