package mx.trillas.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.Gson;

public class IOVersion {
	
	public static void deleteFile(){
		
	}
	
	public static void updateFile(){
		
	}
	
	public static String getFileData(String path) throws JSONException, IOException {
		Properties properties = new Properties();
		InputStream input = null;
		JSONObject json = null;
		
		try {
			json = new JSONObject();
			Gson gson = new Gson();
			input = new FileInputStream(path);
			properties.load(input);
			
			String  fechaRevision =gson.toJson(properties.getProperty("fecharevision"));
			String  tiempoValidez = gson.toJson(properties.getProperty("tiempovalidez"));
			String  numeroRevision = gson.toJson(properties.getProperty("numerorevision"));
			
			json.put("fechaRevision", fechaRevision);
			json.put("tiempoValidez", tiempoValidez);
			json.put("numeroRevision", numeroRevision);
			
		} catch (JSONException | IOException e) {
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return json.toString();
	}
	
	public static boolean fileExists(String path){
		File file = new File(path);
		if(file.exists()){
			return true;
		}
		return false;
	}
}
