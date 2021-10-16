package org.acme;

import org.acme.impl.GreeterImpl;

import java.util.List;

public interface Greeter {

    String greet(String name);

    static Greeter create(List<String> greetings) {
        return new GreeterImpl(greetings);
    }
}
