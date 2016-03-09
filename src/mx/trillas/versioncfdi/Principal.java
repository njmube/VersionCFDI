package mx.trillas.versioncfdi;

import java.io.File;

import mx.trillas.versioncfdi.util.Util;

public class Principal {

	public Principal() {
		String path = "revision.properties";
		File file = new File(path);

		if (!file.exists()) {
			// logger.error()
			// throw Exception("No se pudo encontrar el archivo");
		}

		if (!file.canRead()) {
			//
		}

		// if(El archivo NO tiene la estructura correcta){
		//
		// }

//		if (Util.timeIsValid()) {
//			System.out.println("El archivo es valido");
//		} else {
//			System.out.println("El archivo es invalido");
//		}

		// saveSiteContent(){
	
			try {
				Util.getNumberVersion();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	// versionFromSite = Util.getTitleHtml();
	}

	public static void main(String args[]) {
		new Principal();
	}
}
