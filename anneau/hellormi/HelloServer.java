package anneau.hellormi;

import java.rmi.Naming;
//import java.rmi.RMISecurityManager;


public class HelloServer {
	public static void main (String[ ] args) {
		///* lancer SecurityManager */
		//System.setSecurityManager (new RMISecurityManager ());  // deprecated

		try {
			/* creer une instance de la classe Hello et l'enregistrer dans le serveur de noms */
			Naming.rebind ("Hello1", new HelloImpl ("Hello world"));
			System.out.println ("Serveur pret") ;
		} 
		catch (Exception e) {
			System.out.println("Erreur serveur : " + e) ;
		} 
	}
}
