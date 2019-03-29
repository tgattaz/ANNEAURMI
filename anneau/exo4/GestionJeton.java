package anneau.exo4;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Exclusion mutuelle sur un anneau a jeton
 * 
 * Thread gerant le jeton sur un site
 * 
 * @author nicolas
 *
 */
public class GestionJeton extends Thread {

	Semaphore debutsc;
	Semaphore finsc;

	ThreadServeurEcoute servlocal;
	
	int portecoute;
	Socket clientSocket;
	ObjectInput ois; // pour recevoir le jeton du predecesseur

	String successeur;
	int portsuccesseur;
	Socket theSocket;
	ObjectOutput oos; // pour envoyer le jeton au successeur

	boolean initjeton;
	
	Jeton lejeton = null;

	/**
	 * Constructeur
	 * @param debutsc
	 * @param finsc
	 * @param veutentrer Booleen
	 */
	public GestionJeton(Semaphore debutsc, Semaphore finsc, int port, String successeur, int portsuccesseur, boolean initjeton) {
		this.debutsc = debutsc;
		this.finsc = finsc;
		this.portecoute = port;
		this.successeur = successeur;
		this.portsuccesseur = portsuccesseur;
		this.initjeton = initjeton;
		//creation et lancement du thread d'attente de connexion du site predecesseur (pour ne pas rester bloque ici sur le accept())
		servlocal = new ThreadServeurEcoute(this.portecoute); //,this.listenSocket,this.clientSocket,this.in);
		servlocal.start();
	}

	/**
	 * Activite du thread (gestion du jeton par le site)
	 */
	public void run() { 
		try {
			//attente que tous les autres sites (serveurs tcp) sont bien lances
			Thread.sleep(4000); // 4 secondes

			//connexion avec le successeur
			theSocket = new Socket(successeur, portsuccesseur);
			oos = new ObjectOutputStream(theSocket.getOutputStream());

			//attente que tous les autres connexions sont bien effectuees (anneau construit)
			Thread.sleep(4000); // 4 secondes

			this.ois = servlocal.ois; 

			if(this.initjeton==true) {
				System.out.println(this.getName()+this.portecoute+" : cree et envoi le jeton");
				lejeton = new Jeton();
				oos.writeObject(lejeton);
				oos.flush();
				this.initjeton=false;
			}

			while(true) {
				System.out.println(this.getName()+this.portecoute+" : attend le jeton");

				//recevoir_jeton
				lejeton = (Jeton) ois.readObject();
				System.out.println(this.getName()+this.portecoute+" : a recu le jeton : "+lejeton);

				if(ProgrammeSite.veutentrer == true) {
					debutsc.release();
					finsc.acquire();
				}
				
				//envoyer le jeton
				Thread.sleep(1000); // 1 seconde
				System.out.println(this.getName()+this.portecoute+" : envoi le jeton a "+this.successeur+" "+this.portsuccesseur);
				oos.writeObject(lejeton);
				oos.flush();
			}
		} catch (InterruptedException e) {
			System.err.println("Erreur\n"+this.getName()+"\n"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Erreur\n"+this.getName()+"\n"+e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
