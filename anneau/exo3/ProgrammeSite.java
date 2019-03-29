package anneau.exo3;

import java.util.concurrent.Semaphore;
import java.lang.Thread;


public class ProgrammeSite {
	
	static boolean veutentrer = false;
	
	static int id = 58;
	static long time;
	static int compteur = 0;
	
	static String ipserveurcentral;
	static int portserveurcentral;
	
	public static void main(String args[]) {
		
		ipserveurcentral = args[0];
		portserveurcentral = Integer.parseInt(args[1]);
		
		final Semaphore debutSC = new Semaphore(1);
		final Semaphore finSC = new Semaphore(1);
		
		Thread t = new Traitement(debutSC, finSC);
		Thread gj = new GestionJeton(debutSC, finSC);
		
		gj.setName("Gestion de jeton");
		t.setName("Traitement");
		
		gj.start();
		t.start();
		
	}
	
}