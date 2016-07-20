package com.slon_school.helloslon.core;

import android.app.Activity;

import com.slon_school.helloslon.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.slon_school.helloslon.core.Helper.BTS;

/**
 * Created by I. Dmitry on 17.07.2016.
 */
public class HelpMan {
    private boolean finishSession = false;
    private int hid;
    private Activity activity;
    public HelpMan(String helpID, Activity activity) {
        this.activity = activity;
        switch(helpID) {
            case "BashOrgRandomQuoteWorker": {
                hid = 0;
            } break;
            case "FateBallWorker": {
                hid = 1;
            } break;
            case "BrowserWorker": {
                hid = 2;
            } break;
            case "PhoneWorker": {
                hid = 3;
            } break;
            case "SMSWorker": {
                hid = 4;
            }
            default: {
                hid = -1;
            }
        }
    }
    //
    public Response getHelp() {
        String command = "";
        boolean isFound = true;
        InputStream inputStream = null;
        switch(hid) {
            case 0: {
                inputStream = activity.getResources().openRawResource(R.raw.bashorg_random_quote_help);
            } break;
            case 1: {
                inputStream = activity.getResources().openRawResource(R.raw.fateball_help);
            } break;
            case 2: {
                inputStream = activity.getResources().openRawResource(R.raw.browser_help);
            } break;
            case 3: {
                inputStream = activity.getResources().openRawResource(R.raw.phone_help);
            } break;
            case 4: {
                inputStream = activity.getResources().openRawResource(R.raw.sms_send_help);
            }
            default: {
                isFound = false;
            }
        }
        if (isFound) {
            try {
                command = stream2StringConverter(inputStream);
            } catch (IOException e) {
                BTS(12);
                e.printStackTrace();
            }
        } else {
            command = activity.getString(R.string.help_not_found);
        }
        return new Response(command, finishSession);
    }



    private String stream2StringConverter(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Integer i;
        while ((i = inputStream.read()) != -1) byteArrayOutputStream.write(i);
        return byteArrayOutputStream.toString();
    }
}
