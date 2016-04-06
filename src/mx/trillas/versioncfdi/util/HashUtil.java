package mx.trillas.versioncfdi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;

import org.apache.log4j.Logger;

public class HashUtil {
	private static Logger logger = Logger.getLogger(HashUtil.class);

	public static String hashString(String str) {
		String hexString = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");

			md.update(str.getBytes("UTF-8")); // Change this to "UTF-16" if
												// needed
			byte[] digest = md.digest();
			hexString = String.format("%064x", new java.math.BigInteger(1,
					digest));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} finally {

		}

		return hexString == null ? null : hexString.toString();
	}

	public static boolean checkHash(Archivoderevision archivoderevision) {
		String cadena = makeCadena(archivoderevision);
		String hash = hashString(cadena);
		return hash.equals(archivoderevision.getHash());
	}

	public static String makeCadena(Archivoderevision archivoderevision) {
		String cadena = "||" + archivoderevision.getNumeroderevision() + "|"
				+ archivoderevision.getFechadeverificacion().getTime() + "|"
				+ archivoderevision.getTiempovalidez() + "|"
				+ archivoderevision.getNumeroderevision() + "|"
				+ archivoderevision.getFechadeverificacion().getTime() + "|"
				+ archivoderevision.getTiempovalidez() + "|"
				+ archivoderevision.getNumeroderevision() + "|"
				+ archivoderevision.getFechadeverificacion().getTime() + "|"
				+ archivoderevision.getTiempovalidez() + "|" + "||";
		return cadena;
	}
}
