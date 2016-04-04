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
import org.apache.log4j.Logger;

public class Util {

	private static ArchivoderevisionDAO revisionDAO = new ArchivoderevisionDAOFileimpl();
	private static Archivoderevision revision = null;
	private static String webPage = "http://trillasfacturacion.com.mx/svn_prod";
	private static String username = "cctviga";
	private static String password = "K4l4m4#dO";
	public static Path credential = Paths.get("credencial.properties");

	private static final String CONTAIN_NUMBER = "[A-Za-z]*_[A-Za-z]* - [A-Za-z]* [0-9]*: \\/";
	private static final String NUMBER = "[0-9]+";
	private static final String COMMENTS_PATTERN = "#[A-Za-z]+ ([A-Za-z�����������]+\\s)*";
	private static final String KEYVALUE_PATTERN = "[A-Za-z]*=[0-9]+";
	private static Logger log =  Logger.getLogger(Util.class);
	
	static {
		try {
			revision = revisionDAO.get();
		} catch (Exception e) {
			log.error(e.getMessage());
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
		String numberVersion = null;

		try {
			numberVersion = getTitleHtml();
		} catch (IOException e) {
			throw e;
		}
		log.info("Cadena: " + numberVersion);
		if (containNumberExpression(numberVersion.trim()) == true) {
			log.info("Cadena numerica valida");
			return getNumber(numberVersion);
		} else {
			log.error("Cadena numerica NO valida");
			return null;
		}
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

	public static boolean isCommentsStruct(String line) {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(COMMENTS_PATTERN);
		matcher = pattern.matcher(line);
		return matcher.matches();
	}
	
	public static boolean isKeyValueStruct(String line) {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(KEYVALUE_PATTERN);
		matcher = pattern.matcher(line);
		return matcher.matches();
	}
	
	/**
	 * Validate string with regular expression
	 * 
	 * @param stringToValidate
	 *            number for validation
	 * @return true if string contains number expresion
	 */
	public static boolean containNumberExpression(String stringToValidate) {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(CONTAIN_NUMBER);
		matcher = pattern.matcher(stringToValidate);
		return matcher.find();
	}
	
	/**
	 * Get number with regular expression
	 * 
	 * @param expression
	 * @return string number from expresion
	*/
	public static String getNumber(String expression) throws IllegalStateException {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(NUMBER);
		matcher = pattern.matcher(expression);
		try {
			if (matcher.find() == true) {
				log.info(matcher.group(0));
				return matcher.group(0);
			}
		} catch (IllegalStateException e) {
			throw e;
		}
		return null;
	}

	public static boolean isNumber(String line) {

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(line);

		if (matcher.matches())
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

		log.info("\nultimoMomentoDeValidez: " + ultimoMomentoDeValidez + " \nfechaHoy: " + fechaHoy);
		
		if (ultimoMomentoDeValidez.after(fechaHoy)) {
			return true;
		} else {
			return false;
		}
	}
}
