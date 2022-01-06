package base;



import org.json.simple.JSONObject;
import org.testng.Assert;


import Utility.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class TestBase {

	static String BASE_URL = ConfigManager.getInstance().getString("base_url");

	public static void getResponse() {

		RestAssured.baseURI=BASE_URL;

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET,"/employees");

		System.out.println("======Body======");
		String responseBody = response.getBody().asString();
		System.out.println("Respose Body is: " +responseBody);
		Assert.assertEquals(responseBody.contains("success"), true);

		int statusCode = response.statusCode();
		System.out.println("Status Code: "+statusCode);
		Assert.assertEquals(statusCode, 200);
		
		System.out.println("*******Headers*******");
		Headers allheaders = response.headers();
		for (Header header : allheaders) {
			System.out.println(header.getName()+"  :  "+header.getValue());
		}

				String statusLine = response.getStatusLine();
				System.out.println("Status Code  is:  "+statusLine);
				Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

	}

		public static void PutResponse() {
			RestAssured.baseURI=BASE_URL;
			RequestSpecification httpRequest = RestAssured.given();
			JSONObject requestParam = new JSONObject();
	
			requestParam.put("name", "dk02");
			requestParam.put("salary", "665666");
			requestParam.put("age", "28");
	
			httpRequest.header("Content-Type", "application/json");
	
			httpRequest.body(requestParam.toJSONString());
	
			Response response = httpRequest.request(Method.POST,"/create");
	
			String responseBody = response.getBody().toString();
			System.out.println("Respose Body is: " +responseBody);
			
			System.out.println("Response Body: "+response.getBody().asString());
			Assert.assertEquals(responseBody.contains("success"), false);
			
			int statusCode = response.statusCode();
			System.out.println("Status Code: "+statusCode);
			Assert.assertEquals(statusCode, 200);
	
			Headers allheaders = response.headers();
			for (Header header : allheaders) {
				System.out.println(header.getName()+"  :  "+header.getValue());
			}
			
		}
		
		public void deleteResponse(String ID) {
			
			RestAssured.baseURI=BASE_URL;
			Response resDelete = RestAssured.given().contentType(ContentType.JSON).delete(ID);
			
			int statusCode = resDelete.getStatusCode();
			
			Assert.assertEquals(statusCode, 204);
			
		}
	

}
