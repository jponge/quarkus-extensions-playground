package org.acme.conf;

public class RT {

    private RT() {
    }

    private static GreetingModel greetingModel;

    public static GreetingModel getGreetingModel() {
        return greetingModel;
    }

    public static void setGreetingModel(GreetingModel greetingModel) {
        RT.greetingModel = greetingModel;
    }
}
