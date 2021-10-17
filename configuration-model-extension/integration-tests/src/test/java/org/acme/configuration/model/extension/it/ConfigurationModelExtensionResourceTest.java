package org.acme.configuration.model.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ConfigurationModelExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/configuration-model-extension")
                .then()
                .statusCode(200)
                .body(is("Hello configuration-model-extension"));
    }
}
