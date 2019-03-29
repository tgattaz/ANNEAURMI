package anneau.exo2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.lang.Thread;

public class Traitement extends Thread {
	
	Semaphore debutSC;
	Semaphore finSC;
	
	public Traitement(Semaphore debut, Semaphore fin) {
		debutSC = debut;
		finSC = fin;
	}
	
	public void run(){
		Random r = new Random();
		int alea = 0;
		alea = 1000 + r.nextInt(9000);
		while(true) {
		try {
			sleep(alea);
			System.out.println("le thread veut entrer en SC");
			ProgrammeSite.veutentrer = true;
			debutSC.acquire();
			System.out.println("le thread est en SC");
			sleep(alea);
			System.out.println("le thread sort de la SC");
			ProgrammeSite.veutentrer = false;
			finSC.release();
			sleep(alea);			
		}
		catch(Exception e) {
			
		}
		}
	}
}