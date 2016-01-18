package winery.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server2 {
	
	public static void main(String[] args) {
		runServer();
	}
	
	public static void runServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			server.createContext("/", new Handler() );
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static class Handler implements HttpHandler {

		@Override
		public void handle(HttpExchange arg0) throws IOException {
			// TODO Auto-generated method stub
			//InputStream is = arg0.getRequestBody();
			BufferedReader is = new BufferedReader(new InputStreamReader(arg0.getRequestBody()));
			String response = "";
		//	
			if (is != null) {
				String data = is.readLine();
				System.out.println("Dostalem cos " + data);
				response = operateWithJSONData(data);
				
			}
			
			
			arg0.sendResponseHeaders(200, response.length());
			OutputStream os = arg0.getResponseBody();
			os.write(response.getBytes());
			os.close();
			
	
		}
		
		protected String operateWithJSONData(String data) {
			JSONObject receivedJSONObject = JSONOperations.parseJSONToObject(data);
			if (receivedJSONObject != null) {
				if (receivedJSONObject.getType().equals("Raport") || receivedJSONObject.getType().equals("raport")) {
					Communicator comm = new Communicator(receivedJSONObject);
					JSONObject returnedJSONObject = comm.getOutput();
					String toReturn = JSONOperations.parseObjectToJSONString(returnedJSONObject);
					return toReturn;
				}
				// TODO: Ze znakiem zapytania - czy ten typ?...
				else if (receivedJSONObject.getType().equals("Strona")) {
					// TODO: przekazać obiekt modułowi generowania faktur
					return null;
				}
			}
			return null;
		}
	}

}
