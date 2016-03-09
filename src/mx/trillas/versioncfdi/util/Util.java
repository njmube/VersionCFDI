package mx.trillas.versioncfdi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
			is = new FileInputStream(
					ArchivoderevisionDAOFileimpl.PATHREVISION.toFile());
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

	// Consigue la version desde el contenido html del sitio
	public static String getVersionFromSite() throws IOException {
		String versionString = null;

		try {
			String titleTag = getTitleHtml();
			titleTag = titleTag.replace("-", "");
			titleTag = titleTag.replace(":", "");
			titleTag = titleTag.replace("_", " ");
			String titleTagSplit[] = titleTag.split("\\s");

			if (isNumber(titleTagSplit[4])) {
				System.out.println(titleTagSplit[4]);
				versionString = titleTagSplit[4];
			}
		} catch (IOException e) {
			throw e;
		}

		return versionString;
	}

	// Consigue el contenido de la etiqueta title html
	public static String getTitleHtml() throws IOException {

		Document doc;
		String login = username + ":" + password;
		String base64login = new String(Base64.encodeBase64(login.getBytes()));
		String title = null;

		try {
			doc = Jsoup.connect(webPage)
					.header("Authorization", "Basic " + base64login).get();
			title = doc.title();
		} catch (IOException e) {
			throw e;
		}
		return title;
	}

	public static boolean getHtmlTag(String line) {

		Pattern rfc_Pattern = Pattern.compile("(<h2>([^@])*<\\/h2>)");
		Matcher rfc_matcher = rfc_Pattern.matcher(line);

		if (rfc_matcher.matches())
			return true;

		return false;
	}

	public static boolean isNumber(String line) {

		Pattern rfc_Pattern = Pattern.compile("[0-9]*");
		Matcher rfc_matcher = rfc_Pattern.matcher(line);

		if (rfc_matcher.matches())
			return true;

		return false;
	}

	public static void saveSiteContent() {

		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		URL url;
		URLConnection urlConnection = null;
		try {
			url = new URL(webPage);
			urlConnection = url.openConnection();

			urlConnection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			String inputLine;
			String fileName = "test.html";
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			// use FileWriter to write file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			isr = new InputStreamReader(urlConnection.getInputStream());
			br = new BufferedReader(isr);

			while ((inputLine = br.readLine()) != null) {
				bw.write(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String getSiteContent() {

		String result = null;

		try {
			String authString = username + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();

			urlConnection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];

			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
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
