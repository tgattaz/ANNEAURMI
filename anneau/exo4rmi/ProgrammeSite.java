package anneau.exo4rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.Semaphore;

import anneau.hellormi.HelloImpl;

/**
 * Exclusion mutuelle sur un anneau a jeton
 * 
 * Programme principal d'un site
 * 
 * @author nicolas
 *
 */
public class ProgrammeSite {

	public static boolean veutentrer = false;
	
	
	/**
	 * @param args[0] adresse IP du serveur central
	 * @param args[1] numero de port du serveur central
	 * @param args[2] L'identifiant du site
	 * @param args[3] L'identifiant du successeur
	 * @param args[4] booleen indiquant si le jeton doit etre cree
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		
		Semaphore debutsc = new Semaphore(0);
		Semaphore finsc = new Semaphore(0);
		
		String serveur = args[0];
		int portserveurcentral = Integer.parseInt(args[1]);
		String id_site = args[2];
		String successeur = args[3];
		boolean initjeton = Boolean.parseBoolean(args[4]);
		
		Traitement calculsite = new Traitement(debutsc,finsc,serveur,portserveurcentral);
		GestionJeton gestionsite = new GestionJeton(debutsc,finsc,successeur,initjeton);
		Thread th_gestionsite = new Thread(gestionsite);
		Naming.rebind ("envoyerJeton"+id_site, gestionsite);
		
		calculsite.setName("sitecalcul");
		th_gestionsite.setName("sitegestion");
				
		th_gestionsite.start();
		calculsite.start();  
	}

}
