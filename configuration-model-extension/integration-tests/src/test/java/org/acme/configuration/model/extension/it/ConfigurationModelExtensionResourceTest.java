package org.acme.configuration.model.extension.it;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class ConfigurationModelExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        RestAssured.given()
                .when()
                .get("/q/greetings")
                .then()
                .statusCode(200)
                .body("greetings.size()", equalTo(2))
                .body("greetings.find { it.contains 'Bonjour Julien' }", equalTo(">>>Bonjour Julien!"));
    }
}
