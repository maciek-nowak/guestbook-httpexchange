package com.codecool.krk.mcnowak.models;

import java.util.Date;

public class Entry {
    private String content;
    private String name;
    private Date date;

    public Entry(String content, String name) {
        this.content = content;
        this.name = name;
        this.date = new Date();
    }

    public Entry(String content, String name, Date entryDate) {
        this.content = content;
        this.name = name;
        this.date = entryDate;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
