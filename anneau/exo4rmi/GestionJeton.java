package anneau.exo4rmi;

import java.util.concurrent.Semaphore;

import anneau.exo4rmi.JetonInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
public class GestionJeton extends UnicastRemoteObject implements JetonInterface {

	Semaphore debutsc;
	Semaphore finsc;
	
	
	boolean initjeton;
	
	Jeton lejeton = null;
	public JetonInterface lol = null;
	/**
	 * Constructeur
	 * @param debutsc
	 * @param finsc
	 * @param veutentrer Booleen
	 */
	public GestionJeton(Semaphore debutsc, Semaphore finsc, boolean initjeton) throws RemoteException {
		this.debutsc = debutsc;
		this.finsc = finsc;

		
	}

	/**
	 * Activite du thread (gestion du jeton par le site)
	 */
	public void envoyerJeton(Jeton jeton) throws RemoteException {
		try {
			
			if(ProgrammeSite.veutentrer == true) {
				debutsc.release();
				
				finsc.acquire();
			}
			
			//ATTENTION
			lol.envoyerJeton(jeton);
			
		} catch (Exception e) {
			
		}
		
	}
	
}
