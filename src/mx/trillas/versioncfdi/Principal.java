package mx.trillas.versioncfdi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.trillas.versioncfdi.persistence.pojo.Archivoderevision;
import mx.trillas.versioncfdi.versionDAO.ArchivoderevisionDAO;

public class Principal {

	public static void main(String args[]) {
// esto de git es una locura!
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		ArchivoderevisionDAO revisiondao = new ArchivoderevisionDAOFileimpl();
	
		Long caducidad = null;
		Long fechaVerificacion = null;
		Long tiempoValidezLong = null;
		String path = "revision.properties";
		File file = new File(path);
		Calendar tiempoValidezDate = null;
		Calendar caducidadCalendar =  Calendar.getInstance();
		Archivoderevision revision = null;
		Date caducidadDate = null;
		Calendar currentTime = Calendar.getInstance();
		
		try {
			if (file.exists()) {
				revision = revisiondao.get();
				System.out.println(revision);

				tiempoValidezDate = Calendar.getInstance();
				tiempoValidezDate.setTimeInMillis(revision.getTiempovalidez());

				fechaVerificacion = revision.getFechadeverificacion().getTime();
				tiempoValidezLong = tiempoValidezDate.getTimeInMillis();
				caducidad = fechaVerificacion + tiempoValidezLong;
				caducidadDate= new Date(caducidad);
				caducidadCalendar.setTime(caducidadDate);
				
//				System.out.println("caducidad: " + caducidad +  "  \ncaducidadCalendar: " + caducidadCalendar + "  \ncurrentTime: " + currentTime);
				if(caducidadCalendar.equals(currentTime)){
					System.out.println("La version del programa ha caducado");
				}
				
				// Capturar codigo de la pagina
				NumberVersionRequest.auth();
				
				// Leer y capturar version del programa
				
				// Verificar version del programa, comparar, validar.
				

			} else {
				//
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("Done");
	}
}
