package mx.trillas.versioncfdi;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.util.Local;
import mx.trillas.versioncfdi.util.OSValidator;
import mx.trillas.versioncfdi.util.Util;

import org.apache.log4j.Logger;

public class Principal {

	private static Logger log = Logger.getLogger(Principal.class);
	private static String COMMAND_WINDOWS = "cmd /C cd C:\\SISFA\\ARCXML\\facturar\\cfdi & C:\\SISFA\\tmp\\Software\\Windows\\SlikSvn\\svn.exe info";
	private static String COMMAND_LINUX = "svn info /home/geduardo/facturacion/cfdi";

	public Principal() {

		String remoteNumberVersion = null;
		String localNumberVersion = null;
		Archivoderevision revisionObj = new Archivoderevision();

		// if (!file.exists()) {
		// log.error("No se pudo encontrar el archivo");
		// // throw Exception("No se pudo encontrar el archivo");
		// }
		//
		// if (!file.canRead()) {
		// log.error("No es posible leer el archivo");
		// // throw Exception("No es posible leer el archivo");
		// }

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
			log.info("La fecha actual se encuentra dentro del periodo de verificacion");
		} else {
			log.error("La fecha actual NO se encuentra dentro del periodo de verificacion");
		}

		try {
			remoteNumberVersion = Util.getNumberVersion();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		if (OSValidator.getOsType().equals(OSValidator.OS_TYPE.WINDOWS))
			try {
				localNumberVersion = Local
						.getLocalNumberVersion(COMMAND_WINDOWS);
			} catch (Exception e) {
				log.error(e.getMessage());
				return;
			}
		if (OSValidator.getOsType().equals(OSValidator.OS_TYPE.UNIX)) {

			try {
				localNumberVersion = Local
						.getLocalNumberVersion(COMMAND_LINUX);
			} catch (Exception e) {
				log.error(e.getMessage());
				return;
			}
		}

		if (localNumberVersion != null) {
			log.info("localNumberVersion: " + localNumberVersion);
		} else {
			log.error("No fue posible conseguir la version local del sistema");
		}

		if (remoteNumberVersion != null && localNumberVersion != null) {
			if (remoteNumberVersion == localNumberVersion) {
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
