package HTTP;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Request {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	/**
	 * HTTP GET request to obtain information about the lamps
	 * @param URL the url to the bridge, and path what you want to know
	 * @return the response of the request
	 */
	public static Response GET(String URL){
		try{
			
			//GET
			//Setting a timeout for 4 seconds
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(4 * 1000).build();
			HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			HttpGet request = new HttpGet(URL);
			
			// add request header
			request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = client.execute(request);
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer resultString = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				resultString.append(line);
			}
			
			Response result = new Response(new String(resultString), response.getStatusLine().getStatusCode());
			
			return result;
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * HTTP PUT request to change the value of certain things, like on state or brightness
	 * @param URL the url of the bridge, and the path of the change
	 * @param json change to what
	 * @return the response of the request
	 */
	public static Response PUT(String URL, JsonObject json) {
		try {
			//PUT
			String url = URL;
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPut put = new HttpPut(url);
			
			// add header
			put.addHeader("Accept","application/json");
			
			
			StringEntity jsonRequest = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
			put.setEntity(jsonRequest);
			
			HttpResponse response = client.execute(put);
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer resultString = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				resultString.append(line);
			}
			
			Response result = new Response(new String(resultString), response.getStatusLine().getStatusCode());
			
			return result;
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * HTTP POST request to for instance get a username
	 * @param URL the url of the bridge, and the path of the change
	 * @param json change to what
	 * @return the response of the request
	 */
	public static Response POST(String URL, JsonObject json) {
		try {
			//POST
			String url = URL;
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			
			// add header
			post.addHeader("Accept","application/json");
			
			
			StringEntity jsonRequest = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
			post.setEntity(jsonRequest);
			
			HttpResponse response = client.execute(post);
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer resultString = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				resultString.append(line);
			}
			
			Response result = new Response(new String(resultString), response.getStatusLine().getStatusCode());
			
			return result;
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
