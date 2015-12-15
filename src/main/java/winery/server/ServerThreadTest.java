package winery.server;

import java.net.Socket;

import org.junit.Test;

public class ServerThreadTest {

	/**
	 * Po otrzymaniu takiego JSON-a metoda w ServerThread przekazuje go obiektowi
	 * Communicator, a ten odsyła (póki co - 15.12.2015) potwierdzenie otrzymania informacji.
	 * 
	 */
	@Test
	public void testOperateWithJSONData() {
		String data = 	"{" +
				"	\"type\": \"Raport\", " +
				"	\"user_id\": \"user_id_v\"," +
				"	\"user_pass\": \"user_pass_hash\"," +
				"	\"code\": \"code_of_event\"," +
				"	\"sector\": \"x\"," +
				"	\"row\": \"y\", " +
				"	\"column\": \"z\", " +
				"	\"other\": \"text\", " +
				"	\"date\": \"date\" " +
				"}";
		
		ServerThread serv = new ServerThread(new Socket());
		String returned = serv.operateWithJSONData(data);
		System.out.println(returned);
	}

}
