package anneau.exo4rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JetonInterface extends Remote {
	
	public void envoyerJeton(Jeton jeton) throws RemoteException ;

}
