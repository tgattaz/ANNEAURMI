package anneau.exo3;

import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class EchoClient {

	public static void main(String[] args) {
		Socket theSocket = null;
		BufferedReader theInputStream = null;
		BufferedReader userInput = null;
		PrintStream theOutputStream = null;
		String theLine = null;

		try {
			theSocket = new Socket(args[0], Integer.parseInt(args[1]));
			theOutputStream = new PrintStream(theSocket.getOutputStream());
			theInputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			userInput = new BufferedReader(new InputStreamReader(System.in));
			boolean fini = false;
			while(!fini) {
				theLine = userInput.readLine();
				if(theLine.equals(".")) fini = true;
				theOutputStream.println(theLine);
				System.out.println(theInputStream.readLine());
			}
		} 
		catch (UnknownHostException e) { 
			System.err.println("Erreur : "+e);
			e.printStackTrace();
		} 
		catch (IOException e) { 
			System.err.println("Erreur : "+e);
			e.printStackTrace();
		} 
		finally {
			try {
				if(theSocket!=null) theSocket.close();
				if(theInputStream!=null) theInputStream.close();
				if(theOutputStream!=null) theOutputStream.close();
				if(userInput!=null) userInput.close();
			} catch (IOException e) {
				System.err.println("Erreur : "+e);
				e.printStackTrace();
			}
		}
	} 

}