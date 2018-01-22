import com.google.gson.JsonObject;

public class ColoredLamp extends Lamp {

	protected ColoredLamp(JsonObject coloredLampObject) throws NotInitializedException{
	    super(coloredLampObject);
	}
	
}
