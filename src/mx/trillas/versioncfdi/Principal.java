package mx.trillas.versioncfdi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class Principal {

	public static void main(String args[]) {

		ArchivoderevisionDAO revisiondao = new ArchivoderevisionDAOFileimpl();
		String path = "revision.properties";
		File file = new File(path);
		Archivoderevision revision = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		try {
			if (file.exists()) {
				revision = revisiondao.get();
				System.out.println(revision);

				Calendar tiempoValidezDate = Calendar.getInstance();
				tiempoValidezDate.setTimeInMillis(revision.getTiempovalidez());

				// formatter.parse(revision.getTiempovalidez()+"");
				Long suma = revision.getFechadeverificacion().getTime()
						+ tiempoValidezDate.getTimeInMillis();

				System.out.println(suma);
			} else {
				//
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
