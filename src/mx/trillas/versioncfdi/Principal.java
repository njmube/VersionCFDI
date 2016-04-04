package mx.trillas.versioncfdi;

import java.io.File;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.util.Local;
import mx.trillas.versioncfdi.util.Util;
import org.apache.log4j.Logger;

public class Principal {

	private static Logger log = Logger.getLogger(Principal.class);
	private static String COMMAND = "cmd /C cd C:\\SISFA\\ARCXML\\facturar\\cfdi & C:\\SISFA\\tmp\\Software\\Windows\\SlikSvn\\svn.exe info";

	public Principal() {

		String path = "revision.properties";
		File file = new File(path);
		String numberVersion = null;
		String localNumberVersion = null;
		Archivoderevision revisionObj = new Archivoderevision();

		if (!file.exists()) {
			log.error("No se pudo encontrar el archivo");
			// throw Exception("No se pudo encontrar el archivo");
		}

		if (!file.canRead()) {
			log.error("No es posible leer el archivo");
			// throw Exception("No es posible leer el archivo");
		}

		/*
		 * Verifica si los datos en el archivo revision.properties son validos
		 */

		if (Util.isKeyValueStruct(revisionObj.getNumeroderevision() + "")) {
			log.error("El dato \'Numero de Revision\' NO tiene la estructura correcta");
			// throw Exception("El archivo NO tiene la estructura correcta");
		}

		if (Util.isKeyValueStruct(revisionObj.getFechadeverificacion() + "")) {
			log.error("El dato \'Fecha de verificacion\'NO tiene la estructura correcta");
			// throw Exception("El archivo NO tiene la estructura correcta");
		}

		if (Util.isKeyValueStruct(revisionObj.getTiempovalidez() + "")) {
			log.error("El dato \'Tiempo de validez\' NO tiene la estructura correcta");
			// throw Exception("El archivo NO tiene la estructura correcta");
		}

		if (Util.timeIsValid()) {
			log.info("El archivo es valido");
		} else {
			log.error("El archivo es invalido");
		}

		try {
			numberVersion = Util.getNumberVersion();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		try {
			localNumberVersion = Local.getLocalNumberVersion(COMMAND);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		if (localNumberVersion != null) {
			log.info("localNumberVersion: " + localNumberVersion);
		} else {
			log.error("No fue posible conseguir la version local del sistema");
		}
		
		if (numberVersion != null && localNumberVersion != null) {
			if (numberVersion == localNumberVersion){
				log.info("La version del sistema local y remoto coinciden");
			} else {
				log.info("La version del sistema local y remoto NO coinciden");
			}
		}
	}

	public static void main(String args[]) {
		new Principal();
	}
}
