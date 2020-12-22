package com.example.final_ex6;

import java.io.Serializable;

public class RssItem implements Serializable {
    private String description;
    private String link;
    private String title;


    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title+"\n"+description;
    }

}