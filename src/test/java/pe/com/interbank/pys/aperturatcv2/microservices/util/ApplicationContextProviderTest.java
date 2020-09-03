package pe.com.interbank.pys.aperturatcv2.microservices.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;



@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ApplicationContextProviderTest {
	
	static {
		System.setProperty("ambiente", "dev");
		System.setProperty("version", "unittest");
		System.setProperty("propertiesSensitivePath", "target/test-classes/config");
		System.setProperty("propertiesNonSensitivePath", "target/test-classes/config-sensibles");
	}
	
	private static String GOOD_CHANNEL = "asincronoChannel";
	private static String BAD_CHANNEL = "trashbeantest";
	
	@Test
	public void getChannelOKTest(){
		MessageChannel channel = ApplicationContextProvider.getChannel(GOOD_CHANNEL);
		assertNotNull(channel);
	}
	
	@Test(expected=NoSuchBeanDefinitionException.class)
	public void getChannelExceptionTest(){
		MessageChannel channel = ApplicationContextProvider.getChannel(BAD_CHANNEL);
		assertNull(channel);
	}
	
	@Test
	public void getApplicationContextTest(){
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		assertNotNull(context);
	}

}
