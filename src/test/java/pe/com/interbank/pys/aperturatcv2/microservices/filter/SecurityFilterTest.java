package pe.com.interbank.pys.aperturatcv2.microservices.filter;

import static org.junit.Assert.fail;

import java.net.URI;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class SecurityFilterTest {

	static {
		System.setProperty("ambiente", "dev");
		System.setProperty("version", "unittest");
		System.setProperty("propertiesSensitivePath", "target/test-classes/config");
		System.setProperty("propertiesNonSensitivePath", "target/test-classes/config-sensibles");
	}

	SecurityFilter securityFilter;

	@Test
	public void doFilterOKTest() {
		try {
			HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
			FilterChain filterChain = Mockito.mock(FilterChain.class);

			Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/test.jsp");
			URI uri1 = new URI("http", "nada", "127.0.0.1", 80, "/test", "", "");
			StringBuffer value = new StringBuffer();
			value.append(uri1.toURL());
			Mockito.when(httpServletRequest.getRequestURL()).thenReturn(value);
			Mockito.when(httpServletRequest.getHeader("X-Forwarded-Proto")).thenReturn("X-Forwarded-Proto");

			securityFilter = new SecurityFilter();

			securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		} catch (Exception e) {
			fail("Error en el filtro de seguridad:" + e.getMessage());
		}

	}

	@Test
	public void doFilterNoOKTest() {
		try {
			HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
			FilterChain filterChain = Mockito.mock(FilterChain.class);

			Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/test.jsp");
			URI uri1 = new URI("https", "nada", "127.0.0.1", 80, "/test", "", "");
			StringBuffer value = new StringBuffer();
			value.append(uri1.toURL());
			Mockito.when(httpServletRequest.getRequestURL()).thenReturn(value);
			Mockito.when(httpServletRequest.getHeader("X-Forwarded-Proto")).thenReturn("https");

			securityFilter = new SecurityFilter();

			securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		} catch (Exception e) {
			fail("Error en el filtro de seguridad:" + e.getMessage());
		}

	}

	@Test
	public void doFilterJWTFailTest() {
		try {
			HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
			FilterChain filterChain = Mockito.mock(FilterChain.class);

			Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/test.jsp");
			URI uri1 = new URI("http", "nada", "127.0.0.1", 80, "/test", "", "");
			StringBuffer value = new StringBuffer();
			value.append(uri1.toURL());
			Mockito.when(httpServletRequest.getRequestURL()).thenReturn(value);
			Mockito.when(httpServletRequest.getHeader("X-Forwarded-Proto")).thenReturn("https");

			securityFilter = Mockito.spy(new SecurityFilter());
			Mockito.when(securityFilter.validateJwt(Mockito.anyString())).thenReturn(false);
			securityFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		} catch (Exception e) {
			fail("Error en el filtro de seguridad:" + e.getMessage());
		}
	}
}
