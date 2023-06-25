package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class LandPreparation implements Serializable {
    private String date;
    private String plough;
    private String disc_harrow;
    private String ripper;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlough() {
        return plough;
    }

    public void setPlough(String plough) {
        this.plough = plough;
    }

    public String getDisc_harrow() {
        return disc_harrow;
    }

    public void setDisc_harrow(String disc_harrow) {
        this.disc_harrow = disc_harrow;
    }

    public String getRipper() {
        return ripper;
    }

    public void setRipper(String ripper) {
        this.ripper = ripper;
    }
}
