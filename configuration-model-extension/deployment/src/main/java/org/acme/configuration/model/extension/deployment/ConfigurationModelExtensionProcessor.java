package org.acme.configuration.model.extension.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.http.deployment.NonApplicationRootPathBuildItem;
import io.quarkus.vertx.http.deployment.RouteBuildItem;
import org.acme.conf.GreetingConfiguration;
import org.acme.conf.GreetingModelRecorder;
import org.acme.conf.MyExtraRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

class ConfigurationModelExtensionProcessor {

    private static final String FEATURE = "configuration-model-extension";

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationModelExtensionProcessor.class);

    GreetingConfiguration configuration;

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void configureGreetingModel(GreetingModelRecorder recorder) {
        String prefix = configuration.params.prefix;
        String suffix = configuration.params.suffix;
        List<String> names = Arrays.asList(configuration.params.names.split(","));
        List<String> greetings = Arrays.asList(configuration.params.greetings.split(","));

        logger.info("prefix={}, suffix={}, names={}, greetings={}", prefix, suffix, names, greetings);

        recorder.configureGreetingModel(prefix, suffix, greetings, names);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void dumpConfigurationModel(GreetingModelRecorder recorder) {
        recorder.doDump();
    }

    @BuildStep
    RouteBuildItem addExtraRoute(NonApplicationRootPathBuildItem nonApplicationRootPathBuildItem) {
        return nonApplicationRootPathBuildItem.routeBuilder()
                .route("greetings")
                .handler(new MyExtraRoute())
                .displayOnNotFoundPage()
                .build();
    }
}
