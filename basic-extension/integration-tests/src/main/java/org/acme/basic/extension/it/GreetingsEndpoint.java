package org.acme.basic.extension.it;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.Null;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Path("/greetings")
public class GreetingsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GreetingsEndpoint.class);

    @GET
    public String get() {
        logger.info("Greetings!");
        try {
            byte[] bytes = GreetingsEndpoint.class.getResourceAsStream("/data/greetings.txt").readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) {
            logger.error("Could not read the greetings resource", e);
            return "Failed";
        }
    }
}
