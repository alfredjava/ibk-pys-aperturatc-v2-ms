package pe.com.interbank.pys.aperturatcv2.microservices.filter;

import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

public class CORSResponseFilterTest {

	@InjectMocks
	CORSResponseFilter corsResponseFilter = new CORSResponseFilter();

	@Test
	public void filterTest() throws IOException {
		;
		ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
		ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
		@SuppressWarnings("unchecked")
		MultivaluedMap<String, Object> map = mock(MultivaluedMap.class);
		Mockito.when(responseContext.getHeaders()).thenReturn(map);
		corsResponseFilter.filter(requestContext, responseContext);
	}

}
