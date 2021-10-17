package org.acme.conf;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyExtraRoute implements Handler<RoutingContext> {

    private static final Logger logger = LoggerFactory.getLogger(MyExtraRoute.class);

    @Override
    public void handle(RoutingContext context) {
        logger.info("Extra route handler");

        GreetingModel greetingModel = RT.getGreetingModel();
        JsonArray array = new JsonArray();

        for (String name : greetingModel.names) {
            for (String greeting : greetingModel.greetings) {
                String str = greetingModel.prefix + greeting + " " + name + greetingModel.suffix;
                array.add(str);
            }
        }

        String payload = new JsonObject()
                .put("greetings", array)
                .encode();

        logger.info("Extra route payload: {}", payload);

        context.response()
                .putHeader("Content-Type", "application/json")
                .end(payload);
    }
}
