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
	private String dataIn = "";

	ServerThread(Socket client) {
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.println("Welcome");
		} catch (IOException e) {
			System.out.println("Accept failed: 4444");
		}
	}

	public void run() {
		try {
			// na pewno pętla? Moze raczej jedno odebranie danych?
			/*
			 * // read from the URL Scanner scan = new
			 * Scanner(url.openStream()); String str = new String(); while
			 * (scan.hasNext()) str += scan.nextLine(); scan.close();
			 */
			while (dataIn != null) {
				dataIn = in.readLine();
				if (dataIn != null) {
					// Zapewne rozpakowywanie jakiegoś JSON-a
					// JSONObject json = new JSONObject(dataIn);
					// if (json.has("Raport")) {
					// przekaż Wojtkowi
					// }
					// System.out.println(id + "received: " + dataIn + " " +
					// statusInAction);
					// codeOfOperation = dataIn.substring(4, 6);
				}
			}

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
