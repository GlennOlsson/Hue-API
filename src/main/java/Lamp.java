import Backend.Constants;
import Backend.JSON;
import HTTP.Request;
import com.google.gson.JsonObject;

public class Lamp{
	
	private int lampID;
	private String type;
	private String name;
	private String modelID;
	private String swversion;
	private String swconfigid;
	
	private JsonObject onStateObject;
	private JsonObject brightnessStateObject;
	private JsonObject alertStateObject;
	private JsonObject modeStateObject;
	private JsonObject reachableStateObject;
	
	private String mainURL;
	
	protected Lamp(JsonObject lampObject){
		mainURL = lampObject.get(Constants.MAIN_URL_KEY).getAsString();
		
		lampID = lampObject.get("lampID").getAsInt();
		type = lampObject.get("type").getAsString();
		name = lampObject.get("name").getAsString();
		modelID = lampObject.get("modelid").getAsString();
		swversion = lampObject.get("swversion").getAsString();
		swconfigid = lampObject.get("swconfigid").getAsString();
		
		onStateObject = new JsonObject();
		brightnessStateObject = new JsonObject();
		alertStateObject = new JsonObject();
		modeStateObject = new JsonObject();
		reachableStateObject = new JsonObject();
		
		JsonObject stateObject = lampObject.get("state").getAsJsonObject();
		
		boolean isOn = stateObject.get("on").getAsBoolean();
		int brightness = stateObject.get("bri").getAsInt();
		String alert = stateObject.get("alert").getAsString();
		String mode = stateObject.get("mode").getAsString();
		boolean reachable = stateObject.get("reachable").getAsBoolean();
		
		onStateObject.addProperty("on", isOn);
		brightnessStateObject.addProperty("bri", brightness);
		alertStateObject.addProperty("alert", alert);
		modeStateObject.addProperty("mode", mode);
		reachableStateObject.addProperty("reachable", reachable);
	}
	
	public void setOn(boolean state){
		onStateObject.addProperty("on", state);
		changeState(onStateObject);
	}
	
	public void setBrightness(int brightness){
		brightnessStateObject.addProperty("bri", brightness);
		changeState(brightnessStateObject);
	}
	
	private void changeState(JsonObject jsonObject){
		Request.PUT(mainURL+ "/lights/" + lampID + "/state", jsonObject);
	}
	
	public String getName() {
		return name;
	}
	
	public enum OnState{
		on, off
	}
}
