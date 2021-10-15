package org.acme.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/yolo")
@ApplicationScoped
public class YoloEndpoint {

    private final Logger logger = LoggerFactory.getLogger(YoloEndpoint.class);

    @GET
    public String yolo() {
        logger.info("Yolo!");
        return "Yolo!";
    }
}
