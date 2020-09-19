package com.example.firebasesample;

import com.google.firebase.database.Exclude;

public class Uploads {
    private String name;
    private String imaUrl;
    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImaUrl() {
        return imaUrl;
    }

    public void setImaUrl(String imaUrl) {
        this.imaUrl = imaUrl;    }

    public Uploads(){

    }
    public Uploads(String name, String imaUrl) {
        if (name.trim().equals("")){
            name="No name";
        }
        this.name = name;
        this.imaUrl = imaUrl;
    }

}
