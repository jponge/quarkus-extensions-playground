package org.acme.reflect.extension.it;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ReflectExtensionResourceTest {

    @Test
    public void check() {
        String body = RestAssured.given()
                .when()
                .get("/yolo/Julien")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Assertions.assertEquals("Bonjour Julien", body);
    }
}
