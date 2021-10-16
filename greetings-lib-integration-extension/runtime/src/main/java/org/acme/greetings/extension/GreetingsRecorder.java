package org.acme.greetings.extension;

import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import org.acme.Greeter;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Recorder
public class GreetingsRecorder {

    public RuntimeValue<Greeter> makeGreeter(List<String> greetings) {
        System.out.println("⚡️ Creating a Greeter of: " + greetings);
        return new RuntimeValue<>(Greeter.create(greetings));
    }

    public void runSomeGreetings(ExecutorService executorService, List<String> greetings) {
        Logger logger = Logger.getLogger(Greeter.class);
        Greeter greeter = Greeter.create(greetings);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                logger.info(greeter.greet("Julien"));
            });
        }
    }
}
