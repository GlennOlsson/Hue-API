import Backend.Constants;
import Backend.FileHandling;
import Backend.JSON;
import HTTP.Request;
import HTTP.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.corba.se.idl.constExpr.Not;

import java.io.IOException;
import java.util.ArrayList;

public class Bridge {
	
	protected String mainURL;
	
	private ArrayList<Lamp> lampsList;
	
	/**
	 * Constructor for Bridge. If the API has not been initialized, the constructor will throw a NotInitializedException,
	 * and you'll have to initialize the Bridge with the Bridge(String, String) constructor
	 * @throws NotInitializedException
	 */
	public Bridge() throws NotInitializedException{
		testConnection();
	}
	
	/**
	 * Use this method the first time the API is used so it can create a username and save the URL. Remember to press the Connect
	 * button on your Hue bridge before calling the method.
	 * @param url the url to the Hue bridge. This can be an IP address (like 192.168.1.10) or an url if that is set.
	 *               Include port (like ...10:209) if it is not the standard port, but do not include http://
	 * @param deviceName the name of the device that uses the API. This is used on the bridge so it can easily be recognized.
	 */
	public Bridge(String url, String deviceName){
		String createURL = "http://" + url + "/api";
		
		String deviceType = "Glenns-Hue-API#" + deviceName;
		
		JsonObject usernameRequestJSON = new JsonObject();
		
		usernameRequestJSON.addProperty("devicetype", deviceType);
		
		Response usernameResponse = Request.POST(createURL, usernameRequestJSON);
		
		String responseString = usernameResponse.getResponseString();
		
		JsonArray responseJSONArray = JSON.parseStringToJSON(responseString).getAsJsonArray();
		
		JsonObject objectInArray = responseJSONArray.get(0).getAsJsonObject();
		
		if(!objectInArray.has("success")){
			//Error with response/request
			throw new IllegalArgumentException("Bad parameters, bad connection or you forgot to press the connect button");
		}
		//Was successful!
		JsonObject successObject = objectInArray.get("success").getAsJsonObject();
		String username = successObject.get("username").getAsString();
		
		StringBuilder mainURLBuilder = new StringBuilder();
		mainURLBuilder.append(createURL);
		mainURLBuilder.append("/");
		mainURLBuilder.append(username);
		
		this.mainURL = mainURLBuilder.toString();
		
		JsonObject settingsJSON = new JsonObject();
		
		settingsJSON.addProperty(Constants.MAIN_URL_KEY, mainURL);
		
		FileHandling.saveToFile(settingsJSON.toString());
		
		try{
			testConnection();
		}
		catch (Exception e){
			System.err.println("Error with initialization");
			e.printStackTrace();
		}
	}
	
	private void testConnection() throws NotInitializedException{
		try {
			String contentOfFile = FileHandling.getContentOfFile();
			
			if(contentOfFile.length() < 2) {
				throw new NotInitializedException("Settings file is completely or almost empty. API must be initialized " +
						"with the Bridge(String, String) constructor");
			}
			
			JsonObject settingsJSON = JSON.parseStringToJSON(contentOfFile);
			
			if(!settingsJSON.has(Constants.MAIN_URL_KEY)) {
				throw new NotInitializedException("Settings file does not contain the vital key \"" + Constants.MAIN_URL_KEY + "\"");
			}
			
			mainURL = settingsJSON.get(Constants.MAIN_URL_KEY).getAsString();
			
			//Testing the url by sending a GET request. If it is successful and it returns a good JSON, it is a good request
			Response testRequestResponse = Request.GET(mainURL + "/lights");
			
			String responseString = testRequestResponse.getResponseString();
			JsonObject testRequestObject = JSON.parseStringToJSON(responseString);
			
			lampsList = new ArrayList<>();
			
			for (int i = 0; i < 255; i++) {
				if(!testRequestObject.has(Integer.toString(i))){
					continue;
				}
				JsonObject lampObject = testRequestObject.get(Integer.toString(i)).getAsJsonObject();
				lampObject.addProperty("lampID", i);
				lampObject.addProperty(Constants.MAIN_URL_KEY, mainURL);
				Lamp lamp = new Lamp(lampObject);
				lampsList.add(lamp);
			}
			
		}
		catch (IOException e){
			//If problem with file reading
			e.printStackTrace();
		}
		catch (JsonSyntaxException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Lamp> getLampsList() {
		return lampsList;
	}
}
