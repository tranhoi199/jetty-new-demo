package org.example.rythm;

import org.rythmengine.RythmEngine;

import java.util.HashMap;
import java.util.Map;

public class Rythm {
    private RythmEngine rythmEngine;
    public Rythm() {
        Map<String, String> configuration = new HashMap<>();
//        configuration.put("home.template.dir", "/Users/tranxuanhoi/Documents/working/day2/jetty-gradle-thrift/src/main/resources/template");
        configuration.put("home.template.dir", "/Users/tranxuanhoi/Documents/working/day2/jetty-gradle-thrift/src/main/resources/template");
        configuration.put("feature.type_inference.enabled","true");
        rythmEngine = new RythmEngine(configuration);
    }

    public RythmEngine getRythmEngine() {
        return rythmEngine;
    }
}
