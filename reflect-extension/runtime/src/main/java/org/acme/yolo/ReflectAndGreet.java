package org.acme.yolo;

import org.acme.Greeter;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.List;

@ApplicationScoped
@Path("/yolo/{name}")
public class ReflectAndGreet {

    private static final Logger logger = LoggerFactory.getLogger(ReflectAndGreet.class);

    private final Greeter greeter = Greeter.create(List.of("Bonjour"));

    @GET
    public String greet(@PathParam String name) throws Throwable {
        logger.info("Greeting {}", name);

        // Note: SVM heuristics properly register reflective access for hard-coded values,
        // hence we have to make it look like it's proper dynamic class loading.
        String pkg = "org.acme";
        String cls = "Greeter";
        Method method = Class.forName(pkg + "." + cls).getMethod("greet", String.class);

        return (String) method.invoke(greeter, name);
    }
}
