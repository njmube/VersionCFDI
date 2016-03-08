package mx.trillas.versioncfdi;

import java.io.File;
import java.io.IOException;

import mx.trillas.versioncfdi.util.Util;

public class Principal {
	
	public Principal(){
		String path = "revision.properties";
		File file = new File(path);
		String siteContent = null;
		String versionFromSite = null;
		
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
		
		System.out.println(Util.getSiteContent());
		
		try {
			Util.getVersionFromSite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (Util.timeIsValid()) {
			System.out.println("El archivo es valido");
		} else {
			System.out.println("El archivo es invalido");
		}
		
		if (Util.getSiteContent() != null) {
			siteContent = Util.getSiteContent();
			System.out.println(siteContent);
			
			try {
				versionFromSite = Util.getVersionFromSite();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (versionFromSite != null){
				
			} else {
//				throw new Exception("No encontré la versión del programa");
			}
		} else {
//			throw new Exception("Ocurrio un problema al conseguir la version del programa");
		}
		
		System.exit(0);
	}

	public static void main(String args[]) {
		new Principal();
	}
}
