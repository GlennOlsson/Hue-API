package HTTP;

public class Response {
	private int responseCode;
	private String responseString;
	
	public Response(String responseString, int responseCode){
		this.responseString = responseString;
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getResponseString() {
		return responseString;
	}
}