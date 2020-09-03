package pe.com.interbank.pys.aperturatcv2.microservices.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	protected JwtUtils() {
		throw new IllegalAccessError("Clase Estática");
	}
	
	public static String issueMsInternalToken(String request) {
		String keyString = PropertiesCache.getInstance().getProperty(ConfigConstantes.JWT_MS_SECRET_KEY);
		String token = Jwts.builder().setPayload(request).setSubject("").setIssuer("").signWith(SignatureAlgorithm.HS256, keyString.getBytes())
				.compact();
		logger.info("issued token ");
		return token;
	}

}
