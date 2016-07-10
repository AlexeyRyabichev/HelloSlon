package com.slon_school.helloslon.core;

import java.util.ArrayList;


public class Key {
    private ArrayList<String> words;

    public Key(String toParse) {
        words = parse(toParse);
    }

    private ArrayList<String> parse(String toParse) {
        ArrayList<String> newKeys = new ArrayList<String>();

        String[] keys =  toParse.split(" ");
        for(String key : keys) {
            if (!key.equals("")) {
                key = key.replaceAll("[-+.^:,]","");
                newKeys.add(key.toLowerCase());
            }
        }

        return newKeys;
    }

    public ArrayList<String> get() {
        return words;
    }
}
