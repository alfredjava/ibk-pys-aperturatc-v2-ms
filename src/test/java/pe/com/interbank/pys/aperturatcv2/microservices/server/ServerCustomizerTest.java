package pe.com.interbank.pys.aperturatcv2.microservices.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.Test;


public class ServerCustomizerTest {

	@Test
	public void serverCustomizerConstructTest(){
		
		Server server = new Server();
		server.addBean(new QueuedThreadPool());
		ServerCustomizer serverCustomizer = new ServerCustomizer();
		serverCustomizer.customize(server);
	}
}
