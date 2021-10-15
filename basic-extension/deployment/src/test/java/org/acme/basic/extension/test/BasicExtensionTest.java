package org.acme.basic.extension.test;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.CoreMatchers.is;

class BasicExtensionTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClass(GreetingsEndpoint.class));

    @Test
    @DisplayName("Check the presence of the Yolo RestEasy endpoint")
    void checkYoloPresence() {
        RestAssured.given()
                .when()
                .get("/yolo")
                .then()
                .statusCode(200)
                .body(is("Yolo!"));
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
