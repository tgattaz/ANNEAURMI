package anneau.exo4rmi;

import java.util.concurrent.Semaphore;

import anneau.exo4rmi.JetonInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



/**
 * Exclusion mutuelle sur un anneau a jeton
 * 
 * Thread gerant le jeton sur un site
 * 
 * @author nicolas
 *
 */
public class GestionJeton extends UnicastRemoteObject implements JetonInterface,Runnable {

	Semaphore debutsc;
	Semaphore finsc;
	
	String idSuccesseur;

	boolean initjeton;
	
	Jeton lejeton = null;

	/**
	 * Constructeur
	 * @param debutsc
	 * @param finsc
	 * @param veutentrer Booleen
	 */
	public GestionJeton(Semaphore debutsc, Semaphore finsc, String successeur, boolean initjeton) throws RemoteException {
		this.debutsc = debutsc;
		this.finsc = finsc;
		this.idSuccesseur = successeur;
		this.initjeton = initjeton;
	}

	/**
	 * Activite du thread (gestion du jeton par le site)
	 */
	public void envoyerJeton(Jeton jeton) throws RemoteException {
		try {
			System.out.println("envoyer jeton dist");
			if(ProgrammeSite.veutentrer == true) {
				debutsc.release();
				finsc.acquire();
			}
			//ATTENTION
			System.out.println("envoyer jeton dist 2");
			JetonInterface lol = (JetonInterface) Naming.lookup("rmi://localhost/envoyerJeton" + idSuccesseur) ;
			lol.envoyerJeton(jeton);
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void run() { 
	
			
			try {
				Thread.sleep(4000);// 4 secondes

			if(this.initjeton==true) {
				lejeton = new Jeton();
				
				this.initjeton=false;
				envoyerJeton(lejeton);
			}
			
			

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

	}

}
