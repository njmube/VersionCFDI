package mx.trillas.versioncfdi.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.log4j.Logger;

public class Util {
	private static String webPage = "http://trillasfacturacion.com.mx/svn_prod";
	private static String username = "cctviga";
	private static String password = "K4l4m4#dO";
	public static Path credential = Paths.get("credencial.properties");

	private static final String CONTAIN_NUMBER = "[A-Za-z]*_[A-Za-z]* - [A-Za-z]* [0-9]*: \\/";
	private static final String NUMBER = "[0-9]+";
	private static Logger log =  Logger.getLogger(Util.class);

	public static Integer getNumberVersion() throws IOException {
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
	public static Integer getNumber(String expression) throws IllegalStateException {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(NUMBER);
		matcher = pattern.matcher(expression);
		try {
			if (matcher.find() == true) {
				log.info(matcher.group(0));
				return new Integer(matcher.group(0));
			}
		} catch (IllegalStateException e) {
			throw e;
		}
		return null;
	}
}
