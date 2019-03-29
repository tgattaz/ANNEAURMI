package anneau.exo4rmi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Exclusion mutuelle sur un anneau a jeton
 * 
 * Thread effectuant des calculs dont la section critique sur un site
 * 
 * @author nicolas
 *
 */
public class Traitement extends Thread {

	Semaphore debutsc;
	Semaphore finsc;

	int compteur;
	String serveur;
	int port;
	String id_site;

	/**
	 * Constructeur
	 * @param debutsc Semaphore
	 * @param finsc Semaphore
	 * @param veutentrer Booleen
	 */
	public Traitement(Semaphore debutsc, Semaphore finsc, String serveur, int port, String id_site) {
		this.debutsc = debutsc;
		this.finsc = finsc;
		this.compteur = 0;
		this.serveur = serveur;
		this.port = port;
		this.id_site = id_site;
	}

	/**
	 * Activite du thread (calcul effectue par le site)
	 */
	public void run() { 
		try {
			DatagramSocket theSocket = null;
			InetAddress receiver = InetAddress.getByName(this.serveur);
			theSocket = new DatagramSocket();
			long time;
			
			Random r = new Random();
			int alea = 0;
			
			while(compteur < 10) {
				/* du code quelconque */
				alea = 1000 + r.nextInt(9000);
				Thread.sleep(alea); // entre 1 et 10 secondes
				
				System.out.println(this.getName()+" veut entrer en sc");

				ProgrammeSite.veutentrer = true;
				debutsc.acquire();
				System.out.println(this.getName()+" entre en sc");

				/* code de la section critique */
				//envoyer un message (le nom du thread et la valeur de son compteur) en UDP au serveur central
				this.compteur = this.compteur + 1;

				time = System.currentTimeMillis();
				
				String theLine = time+" "+this.getName()+", site : "+id_site;
				byte[] data = theLine.getBytes();
				DatagramPacket theOutput = new DatagramPacket(data, data.length, receiver, this.port);
				System.out.println(this.getName()+" envoi "+theLine);
				theSocket.send(theOutput);
				
				System.out.println(this.getName()+" sort de sc");
				ProgrammeSite.veutentrer = false;
				finsc.release();
				
				/* du code quelconque */
				alea = 1000 + r.nextInt(9000);
				Thread.sleep(alea); // entre 1 et 10 secondes
			}
			theSocket.close();
			System.out.println(this.getName()+" FIN");
		} catch(InterruptedException e) { 
			System.err.println("Erreur\n"+this.getName()+"\n"+e.getMessage());
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("Erreur\n"+this.getName()+"\n"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Erreur\n"+this.getName()+"\n"+e.getMessage());
			e.printStackTrace();
		}
	}

}
