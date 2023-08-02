package com.restassured.api;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class restAPI {
	@Test
	public void GetUserDetails() { 
		RestAssured.baseURI = "https://reqres.in/api/users"; 
		RequestSpecification httpRequest = RestAssured.given(); 
		Response response = httpRequest.request(Method.GET, "2");
		 
	//	System.out.println("Response=>" + response.prettyPrint());
		Assert.assertEquals(response.getStatusCode(), 200,"Correct status code returned");
		
		String contentType = response.header("Content-Type"); 
		Assert.assertEquals(contentType, "application/json; charset=utf-8"); 
		
		String serverType = response.header("Server"); 
		Assert.assertEquals(serverType, "cloudflare");
		
				
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		System.out.println("Email received from Response :" + jsonPathEvaluator.get("data.email"));
		System.out.println("first name received from Response :" + jsonPathEvaluator.get("data.first_name"));
		System.out.println("last name received from Response :" + jsonPathEvaluator.get("data.last_name"));
		System.out.println("URL received from Response :" + jsonPathEvaluator.get("support.url"));
   	
	}
	
		
	@Test
	public void verifyPetCreation() {
	    	RestAssured.baseURI = "https://petstore.swagger.io/v2/pet"; 
	        // Request Body
	        String requestBody = "{\n" +
	                "  \"id\": 1,\n" +
	                "  \"category\": {\n" +
	                "    \"id\": 1,\n" +
	                "    \"name\": \"poodle\"\n" +
	                "  },\n" +
	                "  \"name\": \"jackie\",\n" +
	                "  \"photoUrls\": [\n" +
	                "    \"string\"\n" +
	                "  ],\n" +
	                "  \"tags\": [\n" +
	                "    {\n" +
	                "      \"id\": 1,\n" +
	                "      \"name\": \"vaccinated\"\n" +
	                "    }\n" +
	                "  ],\n" +
	                "  \"status\": \"available\"\n" +
	                "}";

	        
	        RequestSpecification httpRequest = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .body(requestBody);
	        Response response = httpRequest.post();
	        
	        
	        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code returned");
	        Assert.assertEquals(response.getContentType(), "application/json", "Correct content type returned");
	        Assert.assertEquals(response.getHeader("Server"), "Jetty(9.2.9.v20150224)", "Correct server type returned");
	        
	      //  System.out.println("Response=>" + response.prettyPrint());
	        JsonPath jsonPathEvaluator = response.jsonPath();

	        Assert.assertEquals(jsonPathEvaluator.get("name"), "jackie");
	        System.out.println("Category received from Response :" + jsonPathEvaluator.get("category.name"));
	        System.out.println("Name received from Response :" + jsonPathEvaluator.get("name"));
	        System.out.println("Vaccination Status received from Response :" + jsonPathEvaluator.get("tags[0].name"));
	        

	       
	    }
		

}