package org.example;

import org.rythmengine.Rythm;
import org.rythmengine.RythmEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HelloWorld {
    public static void main(String[] args) {
        RythmEngine rythmEngine;
        Map<String, String> configuration = new HashMap<>();
        configuration.put("resource.def_loader.enabled", "true");
//        String file = new File().getAbsolutePath();
        String dir = System.getProperty("user.dir");

        // directory from where the program was launched
        // e.g /home/mkyong/projects/core-java/java-io
        System.out.println(dir);
        configuration.put("home.template.dir", "/Users/tranxuanhoi/Documents/working/day2/jetty-gradle-thrift/src/main/resources/template");
        try {
            rythmEngine = new RythmEngine(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(Rythm.render("home.html", "world"));
    }
}
