package anneau.hellormi;

import java.rmi.Naming;
//import java.rmi.RMISecurityManager;


public class HelloClient {
	public static void main (String[ ] args){
		///* lance le SecurityManager */
		//System.setSecurityManager (new RMISecurityManager ()); // deprecated
		
		try { 
			/* cherche reference objet distant */
			HelloInterface hello = (HelloInterface) Naming.lookup("rmi://localhost/Hello1") ;

			/* appel de methode a distance */
			System.out.println (hello.sayHello()) ;
		}
		catch (Exception e) {
			System.out.println ("Erreur client : " + e) ;
		} 
	}
}
