/**
 * Aby z poziomu konsoli odpalić program w folderze test w module test, należy przejść poziom wyżej i uruchomić
 * java test.program
 */
package winery.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author aleks
 * @version 0.1
 * The main class of the server. 
 */
public class Server {

	private static ServerSocket server;
	private static int port = 4444;

	/**
	 * @param args arguments of calling program.
	 */
	public static void main(String[] args) {
				//Utworzenie servera
					listen();



	}
	
	/**
	 * @author aleks
	 * Method which listen and wait for clients. Client will be added only if he is needed.
	 */
	private static void listen() {				

		try {
			server = new ServerSocket(port);
			System.out.println("I am listening on the port " + port);
			//Po co w ogóle kolejkować?
			//ArrayList<ServerThread> seq = new ArrayList<ServerThread>;
			try {
				while (true) {
					Socket client = server.accept();
					System.out.println("A client trying to connect");
					ServerThread ClientServerThread = new ServerThread(client);
					//seq.add(ClientServerThread);

					ClientServerThread.start();
				}
			} 
			catch (IOException e) {
				System.out.println("Accept failed: 4444");
			}	
		}
		catch (IOException e) {
			System.out.println("I can not listen on that port " + port);
		}
	}

}
