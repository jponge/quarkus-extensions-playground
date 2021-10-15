package org.acme.basic.extension.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.GeneratedResourceBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageResourceBuildItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

class BasicExtensionConsumeProcessor {

    public static final String GREETINGS_TXT = "data/greetings.txt";

    private static final String FEATURE = "basic-extension-consume";

    private static final Logger logger = LoggerFactory.getLogger(BasicExtensionConsumeProcessor.class);

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void writeGreetings(List<GreetingsBuildItem> greetings, BuildProducer<GeneratedResourceBuildItem> producer) throws IOException {
        String greetingsLine = greetings.stream()
                .flatMap(greetingsBuildItem -> greetingsBuildItem.getGreetings().stream())
                .collect(Collectors.joining(","));

        logger.info("Writing greetings: {}", greetingsLine);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {

            writer.write(greetingsLine);
            writer.write("\n");
            writer.flush();

            producer.produce(new GeneratedResourceBuildItem(GREETINGS_TXT, out.toByteArray()));
            logger.info("Wrote greetings to {}", GREETINGS_TXT);
        }
    }

    @BuildStep
    void includeGreetingsFile(BuildProducer<NativeImageResourceBuildItem> producer) {
        logger.info("Registering {} as a native image resource", GREETINGS_TXT);
        producer.produce(new NativeImageResourceBuildItem(GREETINGS_TXT));
    }
}
