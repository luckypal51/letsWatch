package com.example.letswatch;

public class Model {
    private String Name;
    private String video;

    public Model() {
    }

    public Model(String name, String video) {
        this.Name = name;
        this.video = video;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
       this.video = video;
    }
}
