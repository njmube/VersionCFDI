package mx.trillas.version.persistence.dao;

public interface VersionDAO {
	public boolean checkExpiration();  // comprueba caducidad
	public boolean checkDate();  // comprueba fecha de verificacion
	public void updateFileData();
	public void updateFile();
}
