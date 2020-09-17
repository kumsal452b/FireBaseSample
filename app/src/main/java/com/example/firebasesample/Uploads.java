package com.example.firebasesample;

public class Uploads {
    String name,imaUrl;

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
