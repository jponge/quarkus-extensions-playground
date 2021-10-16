package org.acme.greetings.lib.integration.extension.deployment;

import io.quarkus.builder.item.MultiBuildItem;
import io.quarkus.builder.item.SimpleBuildItem;

import java.util.Arrays;
import java.util.List;

public final class GreetingsBuildItem extends SimpleBuildItem {

    private final List<String> greetings;

    public GreetingsBuildItem(String... greetings) {
        this.greetings = Arrays.asList(greetings);
    }

    public List<String> getGreetings() {
        return greetings;
    }
}
