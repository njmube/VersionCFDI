package mx.trillas.versioncfdi;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.util.HashUtil;
import mx.trillas.versioncfdi.util.IOUtil;
import mx.trillas.versioncfdi.util.SVNUtil;
import mx.trillas.versioncfdi.util.Util;

import org.apache.log4j.Logger;

public class Principal {
	private static Logger log = Logger.getLogger(Principal.class);
	private static File REVISION_FILE = new File("revision.properties");
	private static int tiempoValidezSegundos = 300;

	public Principal() {
		if (REVISION_FILE.exists() && REVISION_FILE.canRead()) {

		} else {
			actualizarArchivoRevision();
		}

		Archivoderevision archivoderevision = IOUtil
				.readPropertiesFromFile(REVISION_FILE);

		if (archivoEsCaduco(archivoderevision)) {
			actualizarArchivoRevision();
		} else {
			if (esRevisionCorrecta(archivoderevision)) {
				log.info("Continuar con la ejecucion");
			} else {
				log.info("Terminar programa");
			}
		}

	}

	public static void main(String args[]) {
		new Principal();
	}

	public static boolean esRevisionCorrecta(Archivoderevision archivoderevision) {
		if (!HashUtil.checkHash(archivoderevision)) {
			log.error("El registro HASH parece no ser correcto");
			return false;
		}
		Integer remoteNumberVersion = null;
		try {
			remoteNumberVersion = Util.getNumberVersion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (remoteNumberVersion == null)
			return false;
		if (archivoderevision.getNumeroderevision() < remoteNumberVersion
				.intValue()) {
			log.error("La versión local es menor a la version remota");
			return false;
		}
		if (archivoderevision.getNumeroderevision() > remoteNumberVersion
				.intValue()) {
			log.error("La versión local es mayor a la version remota");
			return false;
		}
		if (archivoderevision.getNumeroderevision() == remoteNumberVersion
				.intValue()) {
			log.info("La versión local es igual a la version remota");
			return true;
		}
		return false;
	}

	private boolean archivoEsCaduco(Archivoderevision archivoderevision) {
		Calendar ultimoMomentoDeValidez = Calendar.getInstance();
		ultimoMomentoDeValidez.setTime(archivoderevision
				.getFechadeverificacion());
		int tiempoDeValidezEnEntero = (int) archivoderevision
				.getTiempovalidez();
		ultimoMomentoDeValidez.add(Calendar.SECOND, tiempoDeValidezEnEntero);

		Calendar fechaHoy = Calendar.getInstance();

		log.info("\nultimoMomentoDeValidez: " + ultimoMomentoDeValidez
				+ " \nfechaHoy: " + fechaHoy);

		if (ultimoMomentoDeValidez.after(fechaHoy)) {
			return false;
		} else {
			return true;
		}
	}

	private void actualizarArchivoRevision() {
		Integer localRevision = SVNUtil.getLocalRevision();
		long fecharevision = new Date().getTime();
		int tiempoValidez = tiempoValidezSegundos;

		Archivoderevision archivoderevision = new Archivoderevision();
		archivoderevision.setNumeroderevision(localRevision);
		archivoderevision.setFechadeverificacion(new Date(fecharevision));
		archivoderevision.setTiempovalidez(tiempoValidez);

		String cadena = HashUtil.makeCadena(archivoderevision);

		String hash = HashUtil.hashString(cadena);

		String contenidoArchivo = "";
		contenidoArchivo += "#DATO OBTENIDO DE LA REVISIÓN DE SUBVERSIÓN"
				+ "\n";
		contenidoArchivo += "numerorevision=" + localRevision + "\n";
		contenidoArchivo += "#FECHA EPOCH EN MILISEGUNDOS" + "\n";
		contenidoArchivo += "fecharevision=" + fecharevision + "\n";
		contenidoArchivo += "#TIEMPO DE VALIDEZ EN SEGUNDOS" + "\n";
		contenidoArchivo += "tiempovalidez=" + tiempoValidez + "\n";
		contenidoArchivo += "#HASH" + "\n";
		contenidoArchivo += "HASH=" + hash + "\n";
		IOUtil.writeFile(REVISION_FILE, contenidoArchivo);

	}
}
