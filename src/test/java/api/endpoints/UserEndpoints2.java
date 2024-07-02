package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;

//craete for perform Create, Raed, Update and Delete request for User Api's
public class UserEndpoints2 {
	
	//method created for get url from properties file
	static ResourceBundle getURL()
	{
		ResourceBundle routes = ResourceBundle.getBundle("routes"); //Load Properties File
		return routes;
	}
	
	public static Response createUser(User payload){
		String Post_url = getURL().getString("post_url");
		Response response = 
		given()
		 .contentType(ContentType.JSON)
		 .accept(ContentType.JSON)
		 .body(payload)
		
		.when()
		  .post(Post_url);
		
		return response;
		
	}
	
public static Response readUser(String username){
	String Get_url = getURL().getString("get_url");
		Response response = given()
		 .pathParam("username", username)
		
		.when()
		  .get(Get_url);
		
		return response;
		
	}

public static Response updateUser(String username,User payload){
	String Update_url = getURL().getString("update_url");
	Response response = given()
	 .contentType(ContentType.JSON)
	 .accept(ContentType.JSON) 
	 .pathParam("username", username)
	 .body(payload)
	
	.when()
	  .put(Update_url);
	
	return response;
}
public static Response deleteUser(String username){
	String Delete_url = getURL().getString("delete_url");
	Response response = given()
	 .pathParam("username", username)
	
	.when()
	  .delete(Delete_url);
	
	return response;
	
}


}
