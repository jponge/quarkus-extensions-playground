package org.acme.greetings.lib.integration.extension.test;

import org.acme.Greeter;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/yolo/{name}")
public class YoloResource {

    private static final Logger logger = LoggerFactory.getLogger(YoloResource.class);

    @Inject
    Greeter greeter;

    @GET
    public String greet(@PathParam String name) {
        String greeting = greeter.greet(name);
        logger.info("Greeting {} -> {}", name, greeting);
        return greeting;
    }
}
