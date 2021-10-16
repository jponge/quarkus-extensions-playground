package org.acme.greetings.lib.integration.extension.test;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Set;

class GreetingsLibIntegrationExtensionTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClass(YoloResource.class));

    private static final Set<String> greetings = Set.of("Hello", "Bonjour", "G'day", "Halo", "Guten tag");

    @RepeatedTest(10)
    void writeYourOwnUnitTest() {
        String body = RestAssured.given()
                .when()
                .get("/yolo/Julien")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Assertions.assertTrue(body.contains("Julien"));
        Assertions.assertTrue(greetings.contains(body.substring(0, body.indexOf(" Julien"))));
    }
}
