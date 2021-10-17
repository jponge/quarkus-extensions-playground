package org.acme.conf;

import io.quarkus.runtime.annotations.Recorder;

import java.util.List;

@Recorder
public class GreetingModelRecorder {

    public void configureGreetingModel(String prefix, String suffix, List<String> greetings, List<String> names) {

        GreetingModel model = new GreetingModel();
        model.prefix = prefix;
        model.suffix = suffix;
        model.greetings = greetings;
        model.names = names;

        RT.setGreetingModel(model);
    }

    public void doDump() {
        RT.getGreetingModel().dump();
    }
}
