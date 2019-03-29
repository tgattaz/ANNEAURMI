package anneau.exo3;

import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {

	static void doService(Socket clientSocket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintStream out = new PrintStream(clientSocket.getOutputStream());
		boolean fini = false;
		while (!fini) {
			String theLine=in.readLine();
			if(theLine.equals(".")) fini = true;
			out.println(theLine); 
		}
	}

	public static void main(String[] args) {
		ServerSocket listenSocket = null;
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0]));
			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion de :" + clientSocket.getInetAddress());
				doService(clientSocket);
				clientSocket.close();
			}
		}
		catch (Exception e) { 
			System.err.println("Erreur : "+e);
			e.printStackTrace();
		}
		finally {
			try {
				if(listenSocket!=null) listenSocket.close();
			} catch (IOException e) {
				System.err.println("Erreur : "+e);
				e.printStackTrace();
			}
		}
	}

}