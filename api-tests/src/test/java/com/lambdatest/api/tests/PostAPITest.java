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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostAPITest {
    
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
    
    @Test(description = "Get all posts")
    @Description("Test to verify GET /posts endpoint returns all posts")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllPosts() {
        Response response = requestSpec
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue())
                .body("[0].userId", notNullValue())
                .extract().response();
        
        System.out.println("Total posts: " + response.jsonPath().getList("").size());
    }
    
    @Test(description = "Get post by ID")
    @Description("Test to verify GET /posts/{id} endpoint returns specific post")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetPostById() {
        int postId = 1;
        
        Response response = requestSpec
            .when()
                .get("/posts/" + postId)
            .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .body("userId", notNullValue())
                .extract().response();
        
        System.out.println("Post details: " + response.getBody().asString());
    }
    
    @Test(description = "Create new post")
    @Description("Test to verify POST /posts endpoint creates a new post")
    @Severity(SeverityLevel.HIGH)
    public void testCreatePost() {
        String postJson = """
            {
                "title": "New Post Title",
                "body": "This is the body of the new post",
                "userId": 1
            }
            """;
        
        Response response = requestSpec
            .body(postJson)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .body("title", equalTo("New Post Title"))
                .body("body", equalTo("This is the body of the new post"))
                .body("userId", equalTo(1))
                .body("id", notNullValue())
                .extract().response();
        
        System.out.println("Created post: " + response.getBody().asString());
    }
    
    @Test(description = "Update post")
    @Description("Test to verify PUT /posts/{id} endpoint updates post")
    @Severity(SeverityLevel.HIGH)
    public void testUpdatePost() {
        int postId = 1;
        String updatedPostJson = """
            {
                "id": 1,
                "title": "Updated Post Title",
                "body": "Updated post body",
                "userId": 1
            }
            """;
        
        Response response = requestSpec
            .body(updatedPostJson)
            .when()
                .put("/posts/" + postId)
            .then()
                .statusCode(200)
                .body("title", equalTo("Updated Post Title"))
                .body("body", equalTo("Updated post body"))
                .extract().response();
        
        System.out.println("Updated post: " + response.getBody().asString());
    }
    
    @Test(description = "Delete post")
    @Description("Test to verify DELETE /posts/{id} endpoint deletes post")
    @Severity(SeverityLevel.HIGH)
    public void testDeletePost() {
        int postId = 1;
        
        Response response = requestSpec
            .when()
                .delete("/posts/" + postId)
            .then()
                .statusCode(200)
                .extract().response();
        
        System.out.println("Delete response: " + response.getBody().asString());
    }
    
    @Test(description = "Get posts by user ID")
    @Description("Test to verify GET /posts?userId={id} endpoint returns posts by user")
    @Severity(SeverityLevel.MEDIUM)
    public void testGetPostsByUserId() {
        int userId = 1;
        
        Response response = requestSpec
            .queryParam("userId", userId)
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].userId", equalTo(userId))
                .extract().response();
        
        System.out.println("Posts by user " + userId + ": " + response.getBody().asString());
    }
    
    @Test(description = "Test API schema validation")
    @Description("Test to verify API response matches expected schema")
    @Severity(SeverityLevel.MEDIUM)
    public void testAPISchemaValidation() {
        requestSpec
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .body("id", instanceOf(Integer.class))
                .body("title", instanceOf(String.class))
                .body("body", instanceOf(String.class))
                .body("userId", instanceOf(Integer.class));
        
        System.out.println("Schema validation passed");
    }
}
