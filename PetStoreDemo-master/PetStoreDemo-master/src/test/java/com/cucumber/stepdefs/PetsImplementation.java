package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PetsImplementation implements Serializable {
    private Response postPet = null;
    private Response putPet = null;
    private Response deletePet = null;

    @Before("@pets")
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }


    @Given("the following get request that brings us the pets list")
    public Response getAllPets(){

        Response responseGetAllPets = given().log().all().get("/pet/findByStatus?status=available&status=pending&status=sold");
        return responseGetAllPets;
    }
    @Then("the response is 200")
    public void validateResponse(){
        assertEquals("The response is not 200", 200, getAllPets().statusCode());
    }



    @Given("the following get request that brings us the pets availables")
    public Response listPetAvailable() {
        return given().param("status", "availables").get("/pet/findByStatus");
    }

    @Then("the response is 200 for availables")
    public void validateResponseAvalaibles(){
        assertEquals("The response is not 200", 200, listPetAvailable().statusCode());
    }

    @Given("the following post that add pet")
    public void postPet() {
        File jsonFile = new File("src/main/resources/data/newPet.json");

        postPet =
                given().contentType(ContentType.JSON).body(jsonFile).post("/pet");
    }

    @And("the response is 200 for the post pet")
    public void validateStatusCodePostPet() {
        assertTrue("The response is not 200",postPet.statusCode()==200);
    }

    @Then("the body response contains the {string} of the pet created")
    public void validateResponsePostPet(String updateName) {
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonPet = jsonPathPet.getString("name");
        assertEquals("The value of the name field is not what is expected",updateName,jsonPet);
    }


    @Given("the following put request that update a pet")
    public void putPet() throws IOException {
        String JSON_SOURCE = "{\n" +
                "  \"id\": 3006670003455,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"puppi\"\n" +
                "  },\n" +
                "  \"name\": \"puppi\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"sold\"\n" +
                "}";
        Map<String,Object> bodyRequest =
                new ObjectMapper().readValue(JSON_SOURCE, HashMap.class);

        putPet =
                given().contentType(ContentType.JSON).body(bodyRequest).put("/pet");
    }

    @And("the response is 200 for the put pet")
    public void validateStatusCodePutPet() {
        assertTrue("The response is not 200",putPet.statusCode()==200);
    }

    @Then("the body response contains update {string}")
    public void validateResponsePutPet(String updatedStatus) {
        JsonPath jsonPathPets = new JsonPath(putPet.body().asString());
        String jsonPetStatus = jsonPathPets.getString("status");
        assertEquals("The value of the status field is not what is expected",updatedStatus,jsonPetStatus);
    }


    @And( "the following delete request that delete a pet")
    public void deletePets(){
        JsonPath jsonPathPets = new JsonPath(postPet.body().asString());
        String jsonPetId = jsonPathPets.getString("id");
        deletePet = given().accept(ContentType.JSON).delete("/pet/"+ jsonPetId);
    }

    @Then("the body response is 200 for the delete pet")
    public void validateResponseDeletePet() {
        assertEquals("The response is not 200", 200, deletePet.statusCode());
    }
}
