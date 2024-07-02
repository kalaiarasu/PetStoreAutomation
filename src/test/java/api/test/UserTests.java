package api.test;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;


import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DatabaseUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void testPostUser() {
		logger.info("****************** Creating User ***************");
		Response response = UserEndpoints.createUser(userPayload);
		response.then().log().all();
            
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****************** User is created ***************");
	}
	
	@Test(priority=3)
	public void testGetUserByName() {
		logger.info("****************** Reading User info ***************");
		Response response = UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
            
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****************** User info is deleted ***************");
	}
	@Test(priority=4)
	public void testUpdateUserByName() {
		//update using payload
		logger.info("****************** Updating User ***************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		Response response = UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();           
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after update
		Response responseafterupdate = UserEndpoints.readUser(this.userPayload.getUsername());
		responseafterupdate.then().log().all();
            
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****************** User is upadted ***************");
	}
	@Test(priority=5)
	public void testdeleteUserByName() {
		logger.info("****************** Deleting User ***************");
		Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());
		response.then().log().all();           
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****************** User is deleted ***************");
	}
	
}
