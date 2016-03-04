package mx.trillas.versioncfdi;

import java.io.File;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class Principal {

	public static void main(String args[]) {

		ArchivoderevisionDAO revisiondao = new ArchivoderevisionDAOFileimpl();
		File file = ArchivoderevisionDAOFileimpl.PATHREVISION.toFile();
		Archivoderevision revision = null;

		try {
			if (file.exists()) {
				revision = revisiondao.get();
				System.out.println(revision);

				// formatter.parse(revision.getTiempovalidez()+"");
				Long suma = revision.getFechadeverificacion().getTime()
						+ revision.getTiempovalidez();

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
