package mx.trillas.versioncfdi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

	// Consigue la version desde el contenido html del sitio
	public static String xmlReader() throws IOException {

		String versionString = null;

		try {

			File fXmlFile = new File("output.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return versionString;
	}

	public static String getStringFile() throws IOException{
		BufferedReader br = null;
		String sCurrentLine = "";
		String content = "";
		FileReader fileReader = new FileReader("input.html");
		
		try {

			br = new BufferedReader(fileReader);

			while ((sCurrentLine = br.readLine()) != null) {
				content += sCurrentLine;
			}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return content;
	}
	
	public static Document stringToDom() throws Exception {
		
		Document inputDocument;
		String siteContent =  getStringFile();
		InputSource is = new InputSource();
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		StringReader stringReader = new StringReader(siteContent);
		
	
		try {
			documentBuilder = dbf.newDocumentBuilder();
		} catch (Exception e){
			throw e;
		}
		is.setCharacterStream(stringReader);
		inputDocument = documentBuilder.parse(is);
		return inputDocument;
	}
	
	// Consigue el contenido de la etiqueta title html
	public static void getTitleHtml() throws IOException, ParserConfigurationException, SAXException, TransformerException {
		
		URL url;
		url = new URL(webPage);
		Document doc = null;
		URLConnection urlConnection = null;
		
		File file = new File("input.html");
		File output = new File("output.xml");
		
		DocumentBuilder dBuilder = null;
		Transformer transformer;
		
		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		try {

			urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			try {
				dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				throw e;
			}

			try {
				doc = dBuilder.parse(file);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				throw e;
			}

			transformer = TransformerFactory.newInstance().newTransformer();

			Result outputResult = new StreamResult(output);
			Source input = new DOMSource(doc);

			transformer.transform(input, outputResult);

		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
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

			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		
		try {
			String inputLine;
			String fileName = "input.html";
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

			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];

			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
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
