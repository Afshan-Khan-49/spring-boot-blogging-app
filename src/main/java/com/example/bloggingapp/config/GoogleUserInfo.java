package com.example.bloggingapp.config;

import java.util.Map;


public class GoogleUserInfo {

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        System.out.println("attributes : " + attributes);
        this.attributes = attributes;
    }

    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    public String getLastNameName() {
        return (String) attributes.get("family_name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }
}
