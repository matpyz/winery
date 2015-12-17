package winery.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONOperations {

	/**
	 * Metoda do przetworzenia JSON-a w obiekt Javy.
	 * 
	 * @param data
	 *            otrzymany JSON jako String
	 * @return obiekt Javy reprezentujący otrzymanego JSON-a; null, jeśli
	 *         napotkano błędy
	 */
	public static JSONObject parseJSONToObject(String data) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JSONObject jsonObject = objectMapper.readValue(data, JSONObject.class);
			return jsonObject;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static String parseObjectToJSONString(JSONObject obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
