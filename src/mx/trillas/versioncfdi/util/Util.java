package mx.trillas.versioncfdi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import mx.trillas.versioncfdi.persistence.impl.ArchivoderevisionDAOFileimpl;
import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class Util {

	private static ArchivoderevisionDAO revisionDAO = new ArchivoderevisionDAOFileimpl();
	private static Archivoderevision revision = null;
	private static String webPage = "http://trillasfacturacion.com.mx/svn_prod";
	private static String username = "cctviga";
	private static String password = "K4l4m4#dO";
	public static Path credential = Paths.get("credencial.properties");

	static {
		try {
			revision = revisionDAO.get();
		} catch (Exception e) {
			// logger.error(e.getMessage())
		}
	}

	public static String getCredencial() throws IOException {

		Properties properties = new Properties();
		InputStream is = null;

		String credenciales = "";

		try {
			is = new FileInputStream(ArchivoderevisionDAOFileimpl.PATHREVISION.toFile());
			properties.load(is);

			credenciales += properties.getProperty("webPage");
			credenciales += properties.getProperty("username");
			credenciales += properties.getProperty("password");

		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return credenciales;
	}
	
	public static String getNumberVersion() throws IOException {
		String numberVersion;
		
		try {
			numberVersion = getTitleHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		if (isNumber(numberVersion)){
			System.out.println("Cadena numerica valida");
		} else {
			System.out.println("Cadena numerica NO valida");
		}
		
		return numberVersion;
	}
	
	// Consigue el contenido de la etiqueta title html
	public static String getTitleHtml() throws IOException {

		Document doc;
		String login = username + ":" + password;
		String base64login = new String(Base64.encodeBase64(login.getBytes()));
		String title = null;

		try {
			doc = Jsoup.connect(webPage).header("Authorization", "Basic " + base64login).get();
			title = doc.title();
		} catch (IOException e) {
			throw e;
		}
		return title;
	}

	public static boolean isNumber(String line) {

		Pattern rfc_Pattern = Pattern.compile("[0-9]*");
		Matcher rfc_matcher = rfc_Pattern.matcher(line);

		if (rfc_matcher.matches())
			return true;

		return false;
	}

	// timeIsValid devuelve TRUE si la fecha actual se encuentra dentro de
	// periodo de verificacion
	public static boolean timeIsValid() {
		Calendar ultimoMomentoDeValidez = Calendar.getInstance();
		ultimoMomentoDeValidez.setTime(revision.getFechadeverificacion());
		int tiempoDeValidezEnEntero = (int) revision.getTiempovalidez();
		ultimoMomentoDeValidez.add(Calendar.SECOND, tiempoDeValidezEnEntero);

		Calendar fechaHoy = Calendar.getInstance();

		// System.out.println(ultimoMomentoDeValidez + " " + fechaHoy);
		if (ultimoMomentoDeValidez.after(fechaHoy)) {
			return true;
		} else {
			return false;
		}
	}
}
