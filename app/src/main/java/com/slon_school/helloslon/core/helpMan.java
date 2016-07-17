package com.slon_school.helloslon.core;

import com.slon_school.helloslon.R;

/**
 * Created by I. Dmitry on 17.07.2016.
 */
public class HelpMan {
    private boolean finishSession = false;
    private int hid = -1;
    public HelpMan(String helpID) {
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
            default: {
                hid = -1;
            }
        }
    }

    public Response getHelp() {
        String command = "";
        switch(hid) {
            case 0: {
                command += R.raw.bashorg_random_quote_help;
                return new Response(command,finishSession);
            }
            case 1: {
                return new Response(command,finishSession);
            }
            case 2: {
                command += R.raw.browser_help;
                return new Response(command,finishSession);
            }
            default: {
                return new Response("Manual not found",finishSession);
            }
        }
    }
}
