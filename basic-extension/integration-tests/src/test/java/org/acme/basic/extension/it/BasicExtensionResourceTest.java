package org.acme.basic.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BasicExtensionResourceTest {

    @Test
    @DisplayName("Check the presence of the Yolo RestEasy endpoint")
    void checkYoloPresence() {
        RestAssured.given()
                .when()
                .get("/yolo")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("Yolo!"));
    }

    @Test
    @DisplayName("Check the presence of greetings file")
    void checkGreetings() {
        String body = RestAssured.given()
                .when()
                .get("/greetings")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Assertions.assertTrue(body.contains("Guten tag"));
        Assertions.assertTrue(body.contains("Bonjour"));
    }
}
