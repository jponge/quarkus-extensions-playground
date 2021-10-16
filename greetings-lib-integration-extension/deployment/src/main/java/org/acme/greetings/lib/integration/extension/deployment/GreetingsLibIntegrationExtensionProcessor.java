package org.acme.greetings.lib.integration.extension.deployment;

import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ExecutorBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.runtime.RuntimeValue;
import org.acme.Greeter;
import org.acme.greetings.extension.GreetingsRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

class GreetingsLibIntegrationExtensionProcessor {

    private static final String FEATURE = "greetings-lib-integration-extension";

    private static final Logger logger = LoggerFactory.getLogger(GreetingsLibIntegrationExtensionProcessor.class);

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    IndexDependencyBuildItem indexGreetingsLib() {
        logger.info("Adding greetings-lib to the Jandex index");
        return new IndexDependencyBuildItem("org.acme", "greetings-lib");
    }

    @BuildStep
    GreetingsBuildItem makeGreetings() {
        logger.info("Making greetings build item");
        return new GreetingsBuildItem("Hello", "Bonjour", "G'day", "Halo", "Guten tag");
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    SyntheticBeanBuildItem makeGreeterBean(GreetingsBuildItem greetingsBuildItem, GreetingsRecorder recorder) {
        return SyntheticBeanBuildItem.configure(Greeter.class)
                .unremovable()
                .scope(Singleton.class)
                .runtimeValue(recorder.makeGreeter(greetingsBuildItem.getGreetings()))
                .done();
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void makeSomeGreetingsDuringBoot(ExecutorBuildItem executorBuildItem, GreetingsBuildItem greetingsBuildItem, GreetingsRecorder recorder) {
        recorder.runSomeGreetings(executorBuildItem.getExecutorProxy(), greetingsBuildItem.getGreetings());
    }
}
