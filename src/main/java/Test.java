import HTTP.Request;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

public class Test {
	
	String user = "FS-NbfhOkoPq2xpX4bKyDkcNji-MNZ4dN7rhb8pN";
	String url = "http://192.168.1.180/api/FS-NbfhOkoPq2xpX4bKyDkcNji-MNZ4dN7rhb8pN";
	
	public static void main(String[] args) {
		new Test();
	}
	
	public Test(){
		
		Bridge bridge;
		try{
			bridge = new Bridge();
		}
		catch (Exception e){
			e.printStackTrace();
			bridge = new Bridge("192.168.1.180", "Glenns-MBP");
		}
		
		ArrayList<Lamp> lamps = bridge.getLampsList();
		
		Lamp guestLamp = lamps.get(2);
		
		guestLamp.setBrightness(10);
		
		Random random = new Random();
		boolean whileTrue = true;
		while(whileTrue){
			try{
				int randInt = random.nextInt(255);
				guestLamp.setBrightness(randInt);
			    Thread.sleep(100);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
		JsonObject object = new JsonObject();
		object.addProperty("alert", "none");
		
		Request.PUT(url + "/lights/4/state", object);
	}
	
}
