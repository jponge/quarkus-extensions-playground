package org.acme.reflect.extension.test;

import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class ReflectExtensionTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
        .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

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
