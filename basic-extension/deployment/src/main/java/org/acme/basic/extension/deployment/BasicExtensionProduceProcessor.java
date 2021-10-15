package org.acme.basic.extension.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BasicExtensionProduceProcessor {

    private static final String FEATURE = "basic-extension-produce";

    private static final Logger logger = LoggerFactory.getLogger(BasicExtensionProduceProcessor.class);

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void makeSomeGreetings(BuildProducer<GreetingsBuildItem> producer) {
        logger.info("Making some greetings");
        producer.produce(new GreetingsBuildItem("Hello", "Bonjour", "Hallo"));
    }

    @BuildStep
    void makeSomeFurtherGreetings(BuildProducer<GreetingsBuildItem> producer) {
        logger.info("Making some further greetings");
        producer.produce(new GreetingsBuildItem("Guten tag", "G'day", "Hola"));
    }
}
