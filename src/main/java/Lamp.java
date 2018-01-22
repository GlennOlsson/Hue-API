public class Lamp extends Bridge{
	
	private int lampID;
	
	public Lamp(int lampNumber){
		lampID = lampNumber;
	}
	
	public void setOnState(OnState state){
	
	}
}

enum OnState{
	on, off
}
