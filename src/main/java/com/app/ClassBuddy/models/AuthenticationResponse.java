package com.app.ClassBuddy.models;

public class AuthenticationResponse {
    
    private String response;

    public AuthenticationResponse(String message){
        this.response = message;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}

   