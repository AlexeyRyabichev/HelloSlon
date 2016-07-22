package com.slon_school.helloslon.core;

import java.util.ArrayList;

import static java.lang.Long.compare;


public class Key {
    private ArrayList<String> words;

    public Key(String toParse) {
        words = parse(toParse);
    }

    private ArrayList<String> parse(String toParse) {
        ArrayList<String> newKeys = new ArrayList<>();

        String[] keys =  toParse.split(" ");
        for(String key : keys) {
            if (!key.equals("")) {
                key = key.replaceAll("[+.^,]","");
                newKeys.add(key.toLowerCase());
            }
        }

        return newKeys;
    }

    public ArrayList<String> get() {
        return words;
    }

    public long size() { return words.size(); }

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
        for (int i = 0; i < words.size(); i++) {
            if (i != words.size() - 1) {
                toString += words.get(i) + " ";
            } else {
                toString += words.get(i);
            }
        }
        return toString;
    }

}
