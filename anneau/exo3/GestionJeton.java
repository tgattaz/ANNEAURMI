package anneau.exo3;

import java.util.concurrent.Semaphore;

import java.lang.Thread;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GestionJeton extends Thread {
	
	Semaphore debutSC;
	Semaphore finSC;
	
	public GestionJeton(Semaphore debut, Semaphore fin) {
		debutSC = debut;
		finSC = fin;
		//crée threat d'attente de connexion
		Thread AC = new AttenteConnexion();
		AC.setName("Attente de Connexion");
		AC.start();
	}
	
//	public void runold() {
//		while(true) {
//		Scanner sc = new Scanner(System.in);
//		sc.hasNext();
//		System.out.println("le thread a reçu le jeton.");
//		try {
//			if (ProgrammeSite.veutentrer) {
//				debutSC.release();
//				finSC.acquire();
//			}
//			System.out.println("envoi du jeton");
//			
//			for(int i = 0; i < 3; i++) {
//				ProgrammeSite.time = System.currentTimeMillis();
//				run();
//				receive();
//			}
//			ProgrammeSite.compteur++;
//		}
//		catch(Exception e) {
//			
//		}
//	}
//	}
	
	public void run() {
		//code pour se connecter au suivant et configurer le output stream
		Socket theSocket = null;
		PrintStream theOutputStream = null;
		String theLine = null;

		try {
			//Je crée ma socket avec les infos du serv suivant
			theSocket = new Socket(InetAddress.getByName(ProgrammeSite.ipserveurcentral), ProgrammeSite.portserveurcentral);
			//Je configure mon outputstream
			theOutputStream = new PrintStream(theSocket.getOutputStream());
			boolean fini = false;
			theOutputStream.println("Ceci est un message de test fort sympatique");
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
				if(theOutputStream!=null) theOutputStream.close();
			} catch (IOException e) {
				System.err.println("Erreur : "+e);
				e.printStackTrace();
			}
		}
	}
	
	public void receive() {
			ServerSocket listenSocket = null;
			try {
				listenSocket = new ServerSocket(ProgrammeSite.portserveurcentral);
				while(true) {
					Socket clientSocket = listenSocket.accept();
					System.out.println("Connexion de :" + clientSocket.getInetAddress());
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintStream out = new PrintStream(clientSocket.getOutputStream());
					boolean fini = false;
					while (!fini) {
						String theLine=in.readLine();
						if(theLine.equals(".")) fini = true;
						out.println(theLine); 
					}
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
