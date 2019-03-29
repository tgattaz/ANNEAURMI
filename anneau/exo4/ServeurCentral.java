package anneau.exo4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Exclusion mutuelle sur un anneau a jeton
 * 
 * Programme du serveur central recoltant les messages des sites
 * (ce serveur correspond a la ressource partagee)
 * 
 * @author nicolas
 *
 */
public class ServeurCentral {
	
	/**
	 * @param args[0] numero de port
	 * @param args[1] taille max des messages
	 */
	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			//construction d'un DatagramSocket
			int port = Integer.parseInt(args[0]);
			ds = new DatagramSocket(port);
			//construction d'un DatagramPacket
			int len = Integer.parseInt(args[1]);
			byte[] buffer = new byte[len];
			DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);
			while (true) {
				ds.receive(incomingPacket);
				String s = new String(incomingPacket.getData());
				System.out.println(s);
			}
		} 
		catch (Exception e) { 
			System.err.println(e);
			e.printStackTrace();
		}
		finally {
			ds.close();
		}
	} 

}
