package pe.com.interbank.pys.aperturatcv2.microservices.jolt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class JoltFileProcessor {
	private static final Logger logger = LoggerFactory.getLogger(JoltFileProcessor.class);

	public static final String RUTA_CONVERSOR_JSON = PropertiesCache.getInstance()
			.getProperty(ConfigConstantes.APP_MICROSERVICE_LOCATION) + File.separator
			+ PropertiesCache.getInstance().getProperty(ConfigConstantes.CONVERSORS_MS_PATH) + File.separator;

	public static final String RUTA_SORTER_JSON = PropertiesCache.getInstance()
			.getProperty(ConfigConstantes.APP_MICROSERVICE_LOCATION) + File.separator
			+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SORTERS_MS_PATH) + File.separator;

	private JoltFileProcessor() {
		throw new IllegalAccessError("Clase de statica");
	}

	public static void readConversors(Map<String, List<Object>> map) {
		File file = new File(RUTA_CONVERSOR_JSON);
		JoltFileVisitor fileVisitor = new JoltFileVisitor(map);
		if (!"null".equalsIgnoreCase(file.getName())) {
			readFile(file, fileVisitor);
		}
	}

	public static void readSorters(Map<String, List<Object>> map) {
		File file = new File(RUTA_SORTER_JSON);
		JoltFileVisitor fileVisitor = new JoltFileVisitor(map);
		if (!"null".equalsIgnoreCase(file.getName())) {
			readFile(file, fileVisitor);
		}
	}

	public static void readFile(File folder, JoltFileVisitor fileVisitor) {
		try {
			Files.walkFileTree(folder.toPath(), fileVisitor);
		} catch (IOException e) {
			logger.error("Error al recorrer directorio: " + e.getMessage(), e);
		}
	}

}
