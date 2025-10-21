package com.lambdatest.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserAPITest {
    
    private String baseURL;
    private RequestSpecification requestSpec;
    
    @BeforeClass
    public void setUp() {
        baseURL = System.getProperty("api.base.url", "https://jsonplaceholder.typicode.com");
        RestAssured.baseURI = baseURL;
        
        requestSpec = given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json");
    }
    
    @Test(description = "Get all users")
    @Description("Test to verify GET /users endpoint returns all users")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllUsers() {
        Response response = requestSpec
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .extract().response();
        
        Assert.assertTrue(response.getBody().asString().contains("id"));
        System.out.println("Response: " + response.getBody().asString());
    }
    
    @Test(description = "Get user by ID")
    @Description("Test to verify GET /users/{id} endpoint returns specific user")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUserById() {
        int userId = 1;
        
        Response response = requestSpec
            .when()
                .get("/users/" + userId)
            .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("address", notNullValue())
                .extract().response();
        
        System.out.println("User details: " + response.getBody().asString());
    }
    
    @Test(description = "Create new user")
    @Description("Test to verify POST /users endpoint creates a new user")
    @Severity(SeverityLevel.HIGH)
    public void testCreateUser() {
        String userJson = """
            {
                "name": "John Doe",
                "username": "johndoe",
                "email": "john.doe@example.com",
                "address": {
                    "street": "123 Main St",
                    "city": "New York",
                    "zipcode": "10001"
                },
                "phone": "555-1234",
                "website": "johndoe.com"
            }
            """;
        
        Response response = requestSpec
            .body(userJson)
            .when()
                .post("/users")
            .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("username", equalTo("johndoe"))
                .body("email", equalTo("john.doe@example.com"))
                .extract().response();
        
        System.out.println("Created user: " + response.getBody().asString());
    }
    
    @Test(description = "Update user")
    @Description("Test to verify PUT /users/{id} endpoint updates user")
    @Severity(SeverityLevel.HIGH)
    public void testUpdateUser() {
        int userId = 1;
        String updatedUserJson = """
            {
                "id": 1,
                "name": "Updated User",
                "username": "updateduser",
                "email": "updated@example.com"
            }
            """;
        
        Response response = requestSpec
            .body(updatedUserJson)
            .when()
                .put("/users/" + userId)
            .then()
                .statusCode(200)
                .body("name", equalTo("Updated User"))
                .body("username", equalTo("updateduser"))
                .extract().response();
        
        System.out.println("Updated user: " + response.getBody().asString());
    }
    
    @Test(description = "Delete user")
    @Description("Test to verify DELETE /users/{id} endpoint deletes user")
    @Severity(SeverityLevel.HIGH)
    public void testDeleteUser() {
        int userId = 1;
        
        Response response = requestSpec
            .when()
                .delete("/users/" + userId)
            .then()
                .statusCode(200)
                .extract().response();
        
        System.out.println("Delete response: " + response.getBody().asString());
    }
    
    @Test(description = "Get user posts")
    @Description("Test to verify GET /users/{id}/posts endpoint returns user posts")
    @Severity(SeverityLevel.MEDIUM)
    public void testGetUserPosts() {
        int userId = 1;
        
        Response response = requestSpec
            .when()
                .get("/users/" + userId + "/posts")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].userId", equalTo(userId))
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue())
                .extract().response();
        
        System.out.println("User posts: " + response.getBody().asString());
    }
    
    @Test(description = "Test API response time")
    @Description("Test to verify API response time is within acceptable limits")
    @Severity(SeverityLevel.MEDIUM)
    public void testAPIResponseTime() {
        long startTime = System.currentTimeMillis();
        
        requestSpec
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .time(lessThan(2000L)); // Response time should be less than 2 seconds
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        System.out.println("API Response Time: " + responseTime + "ms");
        Assert.assertTrue(responseTime < 2000, "Response time should be less than 2 seconds");
    }
}
