package mx.trillas.versioncfdi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class ArchivoderevisionDAOFileimpl implements ArchivoderevisionDAO {
	
	private String path = "revision.properties";
	
	public Archivoderevision get() throws ParseException, IOException {
		
		Archivoderevision revision = new Archivoderevision();
		Properties properties = new Properties();
		InputStream input = null;
		
		String fecharevision = null;
		String tiempovalidez = null;
		String numerorevision = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			input = new FileInputStream(path);
			properties.load(input);
			
			fecharevision = properties.getProperty("fecharevision");
			tiempovalidez = properties.getProperty("tiempovalidez");
			numerorevision = properties.getProperty("numerorevision");
			
			revision.setFechadeverificacion(formatter.parse(fecharevision));
			revision.setTiempovalidez(Long.parseLong(tiempovalidez));
			revision.setNumeroderevision(Integer.parseInt(numerorevision));
			
		} catch (ParseException|IOException e) {
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return revision;
	}

	@Override
	public void save(Archivoderevision revision) throws IOException {

		Properties properties = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("revision.properties");

			// set the properties value
			properties.setProperty("fecharevision", revision.getFechadeverificacion().toString());
			properties.setProperty("tiempovalidez", revision.getTiempovalidez() + "" );
			properties.setProperty("numerorevision", revision.getNumeroderevision() + "");

			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					throw e;
				}
			}

		}
	  }

}
