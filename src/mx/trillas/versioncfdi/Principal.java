package mx.trillas.versioncfdi;

import java.io.File;
import java.util.Calendar;

import mx.trillas.versioncfdi.persistence.impl.ArchivoderevisionDAOFileimpl;
import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class Principal {
	private static ArchivoderevisionDAO revisionDAO = new ArchivoderevisionDAOFileimpl();
	private static Archivoderevision revision = null;

	static {
		try {
			revision = revisionDAO.get();
		} catch (Exception e) {
			// logger.error(e.getMessage())
		}
	}
	
	public Principal(){
		String path = "revision.properties";
		File file = new File(path);

		if (!file.exists()) {
			// logger.error()
			// throw Exception("No se pudo encontrar el archivo");
		}

		if (!file.canRead()) {
			//
		}

//		if(El archivo NO tiene la estructura correcta){
//			
//		}
		
		if(fileIsValid()){
			
		}else{
			
		}
		
	}

	public static void main(String args[]) {
		new Principal();
	}

	private static boolean fileIsValid() {
		//fileIsValid significa que la última fecha de revision bla bla bla bla bla
		Calendar ultimoMomentoDeValidez = Calendar.getInstance();
		ultimoMomentoDeValidez.setTime(revision.getFechadeverificacion());
		int tiempoDeValidezEnEntero = (int) revision.getTiempovalidez();
		ultimoMomentoDeValidez.add(Calendar.SECOND, tiempoDeValidezEnEntero);

		Calendar fechaHoy = Calendar.getInstance();

		System.out.println(ultimoMomentoDeValidez + " " + fechaHoy);
		if (ultimoMomentoDeValidez.after(fechaHoy)) {
			System.out.println("El archivo es valido");
			return true;
		} else {
			System.out.println("El archivo es invalido");
			return false;
		}
	}
}
