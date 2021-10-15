package org.acme.basic.extension.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.acme.basic.YoloEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BasicExtensionYoloProcessor {

    private static final String FEATURE = "basic-extension-yolo";

    private static final Logger logger = LoggerFactory.getLogger(BasicExtensionYoloProcessor.class);

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    //@BuildStep
    AdditionalBeanBuildItem registerYoloEndpoint() {
        logger.info(FEATURE + " -> register YoloEndpoint");
        return new AdditionalBeanBuildItem(YoloEndpoint.class);
    }

    @BuildStep
    void registerYoloEndpoint(BuildProducer<AdditionalBeanBuildItem> producer) {
        logger.info(FEATURE + " -> register YoloEndpoint (verbose version)");

        AdditionalBeanBuildItem yoloBean = AdditionalBeanBuildItem.builder()
                .addBeanClass(YoloEndpoint.class)
                .setUnremovable()
                .build();

        producer.produce(yoloBean);
    }
}
