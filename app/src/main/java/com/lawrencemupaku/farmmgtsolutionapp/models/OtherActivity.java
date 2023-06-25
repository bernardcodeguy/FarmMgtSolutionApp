package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class OtherActivity implements Serializable {
    private String date;
    private String other1;
   /* private String other2;
    private String other3;*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    /*public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }*/
}
