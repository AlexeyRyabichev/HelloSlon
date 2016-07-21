package com.slon_school.helloslon.core;

import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HelpMan implements Helper.additionalInterface {
    private int rrid;
    private Activity activity;

    public HelpMan(final int RAW_ID, Activity activity) {
        this.activity = activity;
        rrid = RAW_ID;
    }

    public Response getHelp() {
        InputStream inputStream = activity.getResources().openRawResource(rrid);
        String manual = "";
        try { manual = stream2StringConverter(inputStream); } catch (IOException e) { e.printStackTrace(); }
        return new Response(manual, FINISH_SESSION);
    }

    private String stream2StringConverter(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Integer i;
        while ((i = inputStream.read()) != -1) byteArrayOutputStream.write(i);
        return byteArrayOutputStream.toString();
    }
}
