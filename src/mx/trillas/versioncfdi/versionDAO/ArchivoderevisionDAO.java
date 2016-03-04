package mx.trillas.versioncfdi.versionDAO;

import java.io.File;
import java.io.IOException;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;

public interface ArchivoderevisionDAO {
	public void save(Archivoderevision Archivoderevision) throws Exception;
	public Archivoderevision get() throws Exception;
	//public void update(Archivoderevision Archivoderevision) throws Exception;
	//public void delete() throws Exception;
}
