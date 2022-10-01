package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.Serializable;
import java.util.HashMap;


public class StoreImplementation implements Serializable {
    private Response postOrder = null;
    private Response deleteOrder = null;


    @Before("@store")
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/";
    }


    @Given("the following breeding application that brings us the inventory")
    public Response getInventory() {
        return given().log().all().get("/inventory");
    }

    @Then("the response is 200 for inventory")
    public void validateResponseInventory(){
        assertEquals("The response is not 200", 200, getInventory().statusCode());
    }


    @Given("the following post request that add order")
    public void postOrder() {
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 46590);
        bodyRequestMap.put("petId", 46590001);
        bodyRequestMap.put("quantity", 2);
        bodyRequestMap.put("shipDate", "2022-05-24T16:43:14.347Z");
        bodyRequestMap.put("status", "placed");
        bodyRequestMap.put("complete", true);
        postOrder = given().contentType(ContentType.JSON).body(bodyRequestMap).post("/order");
    }


    @And("the response is 200 for the post order")
    public void validateStatusCodePostOrder() {
        assertEquals("The response is not 200", 200, postOrder.statusCode());
    }

    @Then("the body response contains update order {string}")
    public void validateResponsePostOrder(String valueComplete) {
        JsonPath jsonPathOrders = new JsonPath(postOrder.body().asString());
        String jsonOrder = jsonPathOrders.getString("complete");
        assertEquals("The value of complete is not as expected", valueComplete, jsonOrder );
    }


    @Given("the following get request brings us the created order")
    public Response getOrder() {
        postOrder();
        JsonPath jsonPathOrder = new JsonPath(postOrder.body().asString());
        String jsonOrderId = jsonPathOrder.getString("id");
        Response responseOrderId = given().get("/order/"+ jsonOrderId);
        return responseOrderId;
    }


    @Then("the response is 200 for order by id")
    public void validateResponseGetOrder() {
        assertEquals("The response is not 200", 200, getOrder().statusCode());
    }


    @Given("the following delete request that delete a order")
    public void deleteOrder() {
        postOrder();
        JsonPath jsonPathOrder = new JsonPath(postOrder.body().asString());
        String jsonOrderId = jsonPathOrder.getString("id");
        deleteOrder = given().accept(ContentType.JSON).delete("/order/"+jsonOrderId);
    }

    @Given("the response is 200 for delete order")
    public void validateResponseDeleteOrder() {
        assertEquals("The response is not 200", 200, deleteOrder.statusCode());
    }
}