package winery.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 *
 */
public class ClientTest {

	private static Socket s;
	private static Scanner localIn = new Scanner(System.in);
	private static BufferedReader in;
	private static PrintWriter out;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			s = new Socket(InetAddress.getLocalHost(), 4444);
			System.out.println("Connected");
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
			System.out.println(in.readLine());
			System.out.println("Wprowadź tekst komunikatu JSON do wysłania");
			String textToSend = localIn.nextLine();
			System.out.println("Wpisales: " + textToSend);
			out.println(textToSend);
			
			String textReceived = in.readLine();
			System.out.println(textReceived);
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host");
		} catch (IOException e) {
			System.out.println("IO Connection failure");
		}
	}
}
