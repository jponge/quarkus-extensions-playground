package org.acme.basic.extension.deployment;

import io.quarkus.builder.item.MultiBuildItem;

import java.util.Arrays;
import java.util.List;

public final class GreetingsBuildItem extends MultiBuildItem {

    private final List<String> greetings;

    public GreetingsBuildItem(String... greetings) {
        this.greetings = Arrays.asList(greetings);
    }

    public List<String> getGreetings() {
        return greetings;
    }
}
