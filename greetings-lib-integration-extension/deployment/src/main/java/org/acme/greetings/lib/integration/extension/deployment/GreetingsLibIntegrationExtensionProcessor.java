package org.acme.greetings.lib.integration.extension.deployment;

import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.ExecutorBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageResourceBuildItem;
import io.quarkus.runtime.RuntimeValue;
import org.acme.Greeter;
import org.acme.greetings.extension.GreetingsRecorder;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.Collection;

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

    @BuildStep
    void lookupGreeterImplementation(CombinedIndexBuildItem indexBuildItem, BuildProducer<NativeImageResourceBuildItem> fake) {
        logger.info("Looking for org.acme.Greeter implementations");
        IndexView index = indexBuildItem.getIndex();
        Collection<ClassInfo> allKnownImplementors = index.getAllKnownImplementors(DotName.createSimple("org.acme.Greeter"));
        for (ClassInfo implementor : allKnownImplementors) {
            logger.info("💡 {} is an implementation of org.acme.Greeter and has methods {}", implementor.name(), implementor.methods());
        }
    }
}
