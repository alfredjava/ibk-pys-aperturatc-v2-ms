package pe.com.interbank.pys.aperturatcv2.microservices.jolt;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bazaarvoice.jolt.JsonUtils;

public class JoltFileVisitor extends SimpleFileVisitor<Path> {

	private static final Logger logger = LoggerFactory.getLogger(JoltFileVisitor.class);
	private Map<String, List<Object>> specList;

	public JoltFileVisitor(Map<String, List<Object>> specList) {
		super();
		this.specList = specList;
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		logger.info("Inicio carga JOLT de carpeta: " + dir.getFileName());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		if (attr.isRegularFile()) {
			String nombreArchivo = file.getFileName().toString().replace(".json", "");
			specList.put(nombreArchivo, JsonUtils.filepathToList(file.toString()));
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * imprime el directorio actual
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		logger.info("Finalizo carga JOLT de carpeta: " + dir.getFileName());
		return FileVisitResult.CONTINUE;
	}

	/**
	 * control de errores
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		logger.error("Error al recorrer archivos: ", exc);
		return FileVisitResult.CONTINUE;
	}

}
