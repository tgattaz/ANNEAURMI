package anneau.exo3;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//Recepteur
public class Serveur_Central {

	public static void main(String[] args) {
		try {
			//construction d'un DatagramSocket
			int port = Integer.parseInt(args[0]);
			DatagramSocket ds = new DatagramSocket(port);
			//construction d'un DatagramPacket
			int len = Integer.parseInt(args[1]);
			byte[] buffer = new byte[len];
			DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);
			while (true) {
				ds.receive(incomingPacket);
				String s = new String(incomingPacket.getData());
				System.out.println(incomingPacket.getAddress()
						+ " at port " + incomingPacket.getPort() + " says " + s);
			}
		} 
		catch (Exception e) { 
			System.err.println(e);
			e.printStackTrace();
		}
	} 

}