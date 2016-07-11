package com.slon_school.helloslon.core;

public class Response {
    private String response;
    private boolean isEnd;

    public Response(String response, boolean isEnd) {
        this.response = response;
        this.isEnd = isEnd;
    }

    public String getResponse(){
        return response;
    }

    public boolean getIsEnd(){
        return isEnd;
    }
}
