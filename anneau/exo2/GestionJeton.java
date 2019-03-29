package anneau.exo2;

import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GestionJeton extends Thread {
	
	Semaphore debutSC;
	Semaphore finSC;
	
	public GestionJeton(Semaphore debut, Semaphore fin) {
		debutSC = debut;
		finSC = fin;
	}
	
	public void run() {
		while(true) {
		Scanner sc = new Scanner(System.in);
		sc.hasNext();
		System.out.println("le thread a reçu le jeton.");
		try {
			if (ProgrammeSite.veutentrer) {
				debutSC.release();
				finSC.acquire();
			}
			System.out.println("envoi du jeton");
			
			for(int i = 0; i < 3; i++) {
				ProgrammeSite.time = System.currentTimeMillis();
				send();
			}
			ProgrammeSite.compteur++;
		}
		catch(Exception e) {
			
		}
	}
	}
	
	public void send() {
		try {
			
			InetAddress receiver = InetAddress.getByName(ProgrammeSite.ipserveurcentral);
			int port = ProgrammeSite.portserveurcentral;
			
			DatagramSocket theSocket = new DatagramSocket();
			
			//BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			//String theLine = userInput.readLine();
			//if (theLine.equals(".")) break;
			//byte[] data = theLine.getBytes();
			
			String str = ProgrammeSite.time +":"+ ProgrammeSite.id+":"+ProgrammeSite.compteur;
			byte[] data = str.getBytes();
			DatagramPacket theOutput = new DatagramPacket(data, data.length, receiver, port);
			theSocket.send(theOutput);
			
		} 
		catch (Exception e) { 
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
