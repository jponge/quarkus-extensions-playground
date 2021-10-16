package org.acme.impl;

import org.acme.Greeter;

import java.util.List;
import java.util.Random;

public final class GreeterImpl implements Greeter {

    private final List<String> greetings;
    private final Random random = new Random();

    public GreeterImpl(List<String> greetings) {
        this.greetings = greetings;
    }

    @Override
    public String greet(String name) {
        return greetings.get(random.nextInt(greetings.size())) + " " + name;
    }
}
