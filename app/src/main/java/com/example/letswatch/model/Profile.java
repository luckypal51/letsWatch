package com.example.letswatch.model;

public class Profile {
    private String Name;
    private String Photo;

    public Profile() {
    }

    public Profile(String name, String photo) {
        Name = name;
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
