package com.slon_school.helloslon.core;

import android.os.Build;

import java.util.ArrayList;

import static java.lang.Long.compare;


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


    public boolean contains(Key key) {
        long counter = 0;
        for (String word : key.get())
            if (words.contains(word))
                counter++;
        return compare(counter, key.get().size()) >= 0;
    }


    @Override
    public String toString() {
        String toString = "";
        for (String word : words)
            toString += word + " ";
        return toString;
    }

}
