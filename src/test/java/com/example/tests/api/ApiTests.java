package com.example.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class ApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getPosts_status200() {
        Response r = RestAssured.get("/posts");
        Assert.assertEquals(r.statusCode(), 200);
    }

    @Test
    public void getPostById_1() {
        Response r = RestAssured.get("/posts/1");
        Assert.assertEquals(r.statusCode(), 200);
        Assert.assertEquals(r.jsonPath().getInt("id"), 1);
    }

    @Test
    public void getComments_post1() {
        Response r = RestAssured.get("/posts/1/comments");
        Assert.assertEquals(r.statusCode(), 200);
        Assert.assertTrue(r.jsonPath().getList("$").size() > 0);
    }

    @Test
    public void createPost_returns201() {
        Response r = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
                .post("/posts");
        Assert.assertTrue(r.statusCode() == 201 || r.statusCode() == 200);
    }

    @Test
    public void getUsers_status200() {
        Response r = RestAssured.get("/users");
        Assert.assertEquals(r.statusCode(), 200);
    }

    @Test
    public void getUserById_1() {
        Response r = RestAssured.get("/users/1");
        Assert.assertEquals(r.statusCode(), 200);
        Assert.assertEquals(r.jsonPath().getInt("id"), 1);
    }

    @Test
    public void invalidEndpoint_404() {
        Response r = RestAssured.get("/invalidEndpoint");
        Assert.assertEquals(r.statusCode(), 404);
    }

    @Test
    public void getAlbums_status200() {
        Response r = RestAssured.get("/albums");
        Assert.assertEquals(r.statusCode(), 200);
    }

    @Test
    public void getTodos_status200() {
        Response r = RestAssured.get("/todos");
        Assert.assertEquals(r.statusCode(), 200);
    }

    @Test
    public void checkPostTitleNotEmpty() {
        Response r = RestAssured.get("/posts/1");
        Assert.assertNotNull(r.jsonPath().getString("title"));
    }
}
