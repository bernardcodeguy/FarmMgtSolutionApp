package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class IrrigationProgram implements Serializable {
    private String date;
    private String dripSystem;
    private String overheadSprinkler;
    private String naturalRains;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDripSystem() {
        return dripSystem;
    }

    public void setDripSystem(String dripSystem) {
        this.dripSystem = dripSystem;
    }

    public String getOverheadSprinkler() {
        return overheadSprinkler;
    }

    public void setOverheadSprinkler(String overheadSprinkler) {
        this.overheadSprinkler = overheadSprinkler;
    }

    public String getNaturalRains() {
        return naturalRains;
    }

    public void setNaturalRains(String naturalRains) {
        this.naturalRains = naturalRains;
    }
}
