package mx.trillas.version;

import java.io.IOException;

import org.json.JSONException;

public class Principal {
	
	public static void main(String args[]){

		String path = "revision.properties";
		
		try {
			System.out.println(IOVersion.getFileData(path));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
