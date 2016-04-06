package mx.trillas.versioncfdi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Properties;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;

import org.apache.log4j.Logger;

public class IOUtil {
	private static Logger logger = Logger.getLogger(IOUtil.class);

	public static void writeFile(File file, String content) {
		Writer w = null;
		OutputStreamWriter osw = null;
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF8");
			w = new BufferedWriter(osw);
			w.append(content);
			w.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (w != null)
					w.close();
				if (osw != null)
					osw.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
	}

	public static Archivoderevision readPropertiesFromFile(File file) {
		Archivoderevision archivoderevision=new Archivoderevision();
		Properties properties = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			properties = new Properties();
			// load a properties file
			properties.load(fis);
			
			//FECHA DE REVISION
			long lFechaRevision = new Long(properties.getProperty("fecharevision")).longValue();
			Calendar cFechaRevision = Calendar.getInstance();
			cFechaRevision.setTimeInMillis(lFechaRevision);
			archivoderevision.setFechadeverificacion(cFechaRevision.getTime());
			
			//TIEMPO DE VALIDEZ
			long lTiempoValidez = new Long(properties.getProperty("tiempovalidez")).longValue();
			archivoderevision.setTiempovalidez(lTiempoValidez);
			
			//NUMERO DE REVISION
			int lNumeroRevision = new Integer(properties.getProperty("numerorevision")).intValue();
			archivoderevision.setNumeroderevision(lNumeroRevision);
			
			//HASH
			String hash = properties.getProperty("HASH");
			archivoderevision.setHash(hash);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return archivoderevision;
	}
}
