package anneau.hellormi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface HelloInterface extends Remote {
	/** 
	 * methode affichant un message predefini dans l'objet appele
	 */
	public String sayHello() throws RemoteException ;
}
