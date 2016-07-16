package com.slon_school.helloslon.core;

import java.util.ArrayList;

public class Response {
    private String response;
    private boolean isEnd;
    private boolean isHaveImage;
    private ArrayList<String> images;

    public Response(String response, boolean isEnd) {
        this.response = response;
        this.isEnd = isEnd;
        isHaveImage = false;
        images = new ArrayList<String>();
    }

    public Response(String response, boolean isEnd, ArrayList<String> linksToImages) {
        this.response = response;
        this.isEnd = isEnd;
        isHaveImage = true;
        this.images = new ArrayList<String>();
        this.images = linksToImages;
    }

    public boolean isHaveImages() {
        return isHaveImage;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getResponse(){
        return response;
    }

    public boolean getIsEnd(){
        return isEnd;
    }
}
