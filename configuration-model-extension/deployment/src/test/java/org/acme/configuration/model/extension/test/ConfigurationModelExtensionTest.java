package org.acme.configuration.model.extension.test;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.CoreMatchers.equalTo;

public class ConfigurationModelExtensionTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("application.properties"));

    @Test
    public void checkExtraRoute() {
        RestAssured.given()
                .when()
                .get("/q/greetings")
                .then()
                .statusCode(200)
                .body("greetings.size()", equalTo(6))
                .body("greetings.find { it.contains 'Bonjour Julien' }", equalTo("::[Bonjour Julien]::"));
    }
}
