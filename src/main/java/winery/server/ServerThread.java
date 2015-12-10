/**
 * 
 */
package winery.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author aleks
 *
 */
public class ServerThread extends Thread {
	private Socket client;

	private BufferedReader in = null;
	private PrintWriter out = null;

	ServerThread() {
		
	}
	
	ServerThread(Socket client) {
		try {
			this.client = client;
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.println("Welcome");
		} catch (IOException e) {
			System.out.println("Accept failed: 4444");
		}
	}
	
	protected String operateWithJSONData (String data) {
		JSONObject receivedJSONObject = JSONOperations.parseJSONToObject(data);
		if (receivedJSONObject != null) {
			if (receivedJSONObject.getType().equals("Raport")) {
				Communicator comm = new Communicator(receivedJSONObject);
				JSONObject returnedJSONObject = comm.getOutput();
				String toReturn = JSONOperations.parseObjectToJSONString(returnedJSONObject);
				return toReturn;
			}
			// TODO: Ze znakiem zapytania - czy ten typ?...
			else if (receivedJSONObject.getType().equals("Strona")) {
				//TODO: przekazać obiekt modułowi generowania faktur
				return null;
			}
		}
		return null;
	}

	public void run() {
		String dataIn = "";
		String data = "";
		try {
			dataIn = in.readLine();
			System.out.println("Odebralem1: " + dataIn);
			data += dataIn;
			
			String toReturn = operateWithJSONData(data);
			out.println(toReturn);

			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			System.out.println("Accept failed: 4444");
		}
	}

	protected void sendToOut(String content) {
		out.println(content);
	}

}
