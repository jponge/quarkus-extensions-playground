package org.acme.conf;

import java.util.ArrayList;
import java.util.List;

public class GreetingModel {

    public String prefix;
    public String suffix;

    public List<String> greetings = new ArrayList<>();
    public List<String> names = new ArrayList<>();

    public void dump() {
        for (String greeting : greetings) {
            for (String name : names) {
                System.out.println(prefix + greeting + " " + name + suffix);
            }
        }
    }
}
