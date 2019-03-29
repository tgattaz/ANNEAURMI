package anneau.exo1;

import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.util.Scanner;

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
		}
		catch(Exception e) {
			
		}
	}
	}

}