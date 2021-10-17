package org.acme.conf;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class GreetingConfig {

    /**
     * The prefix
     */
    @ConfigItem(defaultValue = ">>>")
    public String prefix;

    /**
     * The suffix
     */
    @ConfigItem(defaultValue = "!")
    public String suffix;

    /**
     * Comma-delimited greetings
     */
    @ConfigItem(defaultValue = "Hello,Bonjour")
    public String greetings;

    /**
     * Comma-delimited names
     */
    @ConfigItem(defaultValue = "Julien")
    public String names;
}
