package winery.server;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;

public class ServerThreadTest {

	@Test
	public void testOperateWithJSONData() {
		String data = 	"{" +
				"	\"type\": \"raport\", " +
				"	\"user_id\": \"user_id_v\"," +
				"	\"user_pass\": \"user_pass_hash\"," +
				"	\"code\": \"code_of_event\"," +
				"	\"sector\": \"x\"," +
				"	\"row\": \"y\", " +
				"	\"column\": \"z\", " +
				"	\"other\": \"text\", " +
				"	\"data\": \"data\" " +
				"}";
		
		ServerThread serv = new ServerThread(new Socket());
		String returned = serv.operateWithJSONData(data);
	}

}
