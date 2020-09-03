package pe.com.interbank.pys.aperturatcv2.microservices.jwt;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class JwtUtilsTest {

	@Test(expected=IllegalAccessError.class)
	public void  JwtUtils() {
		new JwtUtils();
	}
	
	@Test
	public void issueMsInternalToken(){
		assertNotNull(JwtUtils.issueMsInternalToken("Test"));
	}

}
