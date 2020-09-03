package pe.com.interbank.pys.aperturatcv2.microservices;

import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;

/**
 * 
 * @author XT7189 
 * Main: clase que se usa para ejecutar el microservicio
 */
public class Main {
	
	private Main() {
		super();
	}

	/**
	 * metodo main: invoca a la clase server iniciando el contexto de spring
	 * boot
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AperturaTcServer.main(args);
	}

}
