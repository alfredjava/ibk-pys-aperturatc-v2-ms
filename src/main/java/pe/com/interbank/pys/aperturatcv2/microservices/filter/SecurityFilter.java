package pe.com.interbank.pys.aperturatcv2.microservices.filter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.Jwts;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class SecurityFilter implements Filter {

	private static final String HTTPS = "https";
	private static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// No se inicializa parametros

	}

	@Override
	public void destroy() {
		// No se ejecuta nada despues del proceso

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		logger.debug("protocolo:" + httpRequest.getHeader(X_FORWARDED_PROTO));
		// Get the HTTP Authorization header from the request
		String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (httpRequest.getHeader(X_FORWARDED_PROTO) != null
				&& HTTPS.equalsIgnoreCase(httpRequest.getHeader(X_FORWARDED_PROTO))) {
			logger.debug("HTTPS");
			if (validateJwt(authorizationHeader)) {
				chain.doFilter(request, response);
			} else {
				logger.info("validacion JWT Fallida");
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Autenticacion fallida");
				return;
			}
		} else {
			logger.debug("HTTP");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			try {
				URI uri1 = new URI(httpRequest.getRequestURL().toString());
				logger.debug("uri1 Path:" + uri1.getPath());

				URI uri2 = new URI(HTTPS, uri1.getUserInfo(), uri1.getHost(), -1, uri1.getPath(),
						httpRequest.getQueryString(), null);
				logger.debug("uri2 Path:" + uri1.getPath());

				httpResponse.sendRedirect(uri2.toString());
			} catch (URISyntaxException e) {
				logger.info("Ocurrio un error al obtener las URIs");
				throw new ServletException("Ocurrio un error al obtener las URIs", e);
			}
		}
	}

	public boolean validateJwt(String authorizationHeader) {
		String jwtEnabledString = PropertiesCache.getInstance().getProperty(ConfigConstantes.JWT_ENABLED);
		if (Boolean.parseBoolean(jwtEnabledString)) {
			logger.info("validacion jwt habilitada");
			// Extract the token from the HTTP Authorization header
			if (authorizationHeader != null) {
				String token = authorizationHeader.substring("Bearer".length()).trim();
				try {
					String keyString = PropertiesCache.getInstance().getProperty(ConfigConstantes.JWT_SECRET_KEY);
					Jwts.parser().setSigningKey(keyString.getBytes()).parseClaimsJws(token);
					logger.info("token valido");
				} catch (Exception e) {
					logger.error("token no valido: "+ e.getMessage(), e);
					return false;
				}
			} else {
				logger.error("no se encuentra el authorization Header");
				return false;
			}
		}
		return true;
	}
}
