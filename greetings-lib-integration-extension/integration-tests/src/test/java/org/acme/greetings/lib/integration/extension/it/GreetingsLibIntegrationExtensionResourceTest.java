package org.acme.greetings.lib.integration.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingsLibIntegrationExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/greetings-lib-integration-extension")
                .then()
                .statusCode(200)
                .body(is("Hello greetings-lib-integration-extension"));
    }
}
