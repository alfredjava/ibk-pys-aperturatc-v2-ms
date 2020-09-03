package pe.com.interbank.pys.aperturatcv2.microservices.util;

import org.junit.Test;

public class ConfigConstantesTest {
	
	@Test(expected= IllegalAccessError.class)
	public void constructorTest(){
		new ConfigConstantes();
	}

}
