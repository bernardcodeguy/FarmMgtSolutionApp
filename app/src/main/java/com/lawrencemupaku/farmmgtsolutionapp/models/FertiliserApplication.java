package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class FertiliserApplication implements Serializable {
    private String date;
    private String preplantingApp;
    private String starterApp;
    private String folioApp;
    private String topDressing;
    private String doloping;
    private String other;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPreplantingApp() {
        return preplantingApp;
    }

    public void setPreplantingApp(String preplantingApp) {
        this.preplantingApp = preplantingApp;
    }

    public String getStarterApp() {
        return starterApp;
    }

    public void setStarterApp(String starterApp) {
        this.starterApp = starterApp;
    }

    public String getFolioApp() {
        return folioApp;
    }

    public void setFolioApp(String folioApp) {
        this.folioApp = folioApp;
    }

    public String getTopDressing() {
        return topDressing;
    }

    public void setTopDressing(String topDressing) {
        this.topDressing = topDressing;
    }

    public String getDoloping() {
        return doloping;
    }

    public void setDoloping(String doloping) {
        this.doloping = doloping;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
