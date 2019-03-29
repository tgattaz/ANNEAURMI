package anneau.exo1;

import java.util.concurrent.Semaphore;
import java.lang.Thread;


public class ProgrammeSite {
	
	static boolean veutentrer = false;
	
	public static void main(String args[]) {
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