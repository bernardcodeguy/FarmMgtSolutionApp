package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class ChemicalSpray implements Serializable {
    private String date;
    private String fungicides;
    private String herbicides;
    private String inserticides;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFungicides() {
        return fungicides;
    }

    public void setFungicides(String fungicides) {
        this.fungicides = fungicides;
    }

    public String getHerbicides() {
        return herbicides;
    }

    public void setHerbicides(String herbicides) {
        this.herbicides = herbicides;
    }

    public String getInserticides() {
        return inserticides;
    }

    public void setInserticides(String inserticides) {
        this.inserticides = inserticides;
    }
}
