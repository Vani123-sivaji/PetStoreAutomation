package api.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setup()
	{
		
	faker=new Faker();
	userPayload=new User();
	
	userPayload.setid(faker.idNumber().hashCode());
	userPayload.setUsername(faker.name().username());
	userPayload.setFirstName(faker.name().firstName());
	userPayload.setLastName(faker.name().lastName());
	userPayload.setEmail(faker.internet().safeEmailAddress());
	userPayload.setPassword(faker.internet().password());
	userPayload.setPhone(faker.phoneNumber().cellPhone());
	//logs
	logger=LogManager.getLogger(this.getClass());
	
	
	}
	
	
	@Test(priority=1)
	
	public void testPostUser()
	{
		
		logger.info("********** Creating user  ***************");
		Response response=UserEndPoints.CreateUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********User is created  ***************");
}
	
	@Test(priority=2)
	
	public void testGetUserByName()
	{
		
		logger.info("********** Reading User Info ***************");
		Response response=UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("**********User info  is displayed ***************");
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
	//Update data using payload
		logger.info("********** Updating User ***************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		Assert.assertEquals(response.getStatusCode(),200);
		
	//Checking data after value
		logger.info("********** User updated ***************");
		Response responseAfterUpdate=UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		
		
	
}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("**********   Deleting User  ***************");
	
	Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
	Assert.assertEquals(response.getStatusCode(),200);
	
	logger.info("********** User deleted ***************");
	
}
}