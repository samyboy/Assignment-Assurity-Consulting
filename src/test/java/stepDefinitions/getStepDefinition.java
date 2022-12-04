package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class getStepDefinition extends Utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;

    @Given("Get catalog payload")
    public void get_catalog_payload() throws IOException {
        res = given().spec(requestSpecification());
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (method.equalsIgnoreCase("POST"))
            response = res.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = res.when().get(resourceAPI.getResource());

    }

    @Then("The API is successful with status code {int}")
    public void the_api_is_successful_with_status_code(Integer statusCode) {
        assertEquals(Integer.valueOf(response.getStatusCode()), statusCode);
        System.out.println(response.getStatusCode());

    }

    @Then("{string} in response is {string}")
    public void in_response_is(String keyValue, String Expectedvalue) {
        assertEquals(getJsonPath(response, keyValue), Expectedvalue);
    }

    @Then("{string} in response is having {string} as {string} where {string} is {string}")
    public void in_response_is(String arrayKeyValue, String keyValue, String expectedvalue, String whereKeyValue, String whereExpectedValue) {
        assertEquals(getPropertyFromArrayInJsonWhereKeyIs(response, arrayKeyValue, keyValue, whereKeyValue, whereExpectedValue), expectedvalue);
    
        

    System.out.println("arrayKeyValue: "+ arrayKeyValue);
    System.out.println("whereKeyValue: "+ whereKeyValue);
    
    System.out.println(whereKeyValue + " : "+ whereExpectedValue); 
    System.out.println(keyValue + " : " +expectedvalue);
    }

}
