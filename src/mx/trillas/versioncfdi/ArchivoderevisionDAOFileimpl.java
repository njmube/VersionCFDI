package mx.trillas.versioncfdi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
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

		} catch (ParseException | IOException e) {
=======
	public static Path PATHREVISION = Paths.get("revision.properties");

	public Archivoderevision get() throws ParseException, IOException {

		Archivoderevision revision;
		Properties properties = new Properties();
		InputStream is = null;

		long fecharevision;
		long tiempovalidez;
		int numerorevision;

		try {
			is = new FileInputStream(
					ArchivoderevisionDAOFileimpl.PATHREVISION.toFile());
			properties.load(is);

			fecharevision = new Long(properties.getProperty("fecharevision")).longValue();
			tiempovalidez = new Long(properties.getProperty("tiempovalidez")).longValue();
			numerorevision = new Integer(properties.getProperty("numerorevision")).intValue();
			revision = new Archivoderevision();
			revision.setFechadeverificacion(new Date(fecharevision));
			revision.setTiempovalidez(tiempovalidez);
			revision.setNumeroderevision(numerorevision);

		} catch (IOException e) {
>>>>>>> 13e0ca7ca5185363670bbf8a37dc1b37fc934645
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
		return revision;
	}

	@Override
	public void save(Archivoderevision revision) throws IOException {

		Properties properties = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(
					ArchivoderevisionDAOFileimpl.PATHREVISION.toFile());

			// set the properties value
<<<<<<< HEAD
			properties.setProperty("fecharevision", revision.getFechadeverificacion().toString());
			properties.setProperty("tiempovalidez", revision.getTiempovalidez() + "");
			properties.setProperty("numerorevision", revision.getNumeroderevision() + "");
=======
			properties.setProperty("fecharevision", revision
					.getFechadeverificacion().toString());
			properties.setProperty("tiempovalidez", revision.getTiempovalidez()
					+ "");
			properties.setProperty("numerorevision",
					revision.getNumeroderevision() + "");
>>>>>>> 13e0ca7ca5185363670bbf8a37dc1b37fc934645

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
