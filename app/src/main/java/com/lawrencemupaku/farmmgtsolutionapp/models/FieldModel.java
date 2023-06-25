package com.lawrencemupaku.farmmgtsolutionapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;

public class FieldModel implements Serializable {
    private String date;
    private String fieldName;
    private String fieldType;
    private String cropName;
    private String numOfPlants;
    private String targetYield;
    private String actualYield;
    private String yieldVariance;


    public FieldModel(){

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getNumOfPlants() {
        return numOfPlants;
    }

    public void setNumOfPlants(String numOfPlants) {
        this.numOfPlants = numOfPlants;
    }

    public String getTargetYield() {
        return targetYield;
    }

    public void setTargetYield(String targetYield) {
        this.targetYield = targetYield;
    }

    public String getActualYield() {
        return actualYield;
    }

    public void setActualYield(String actualYield) {
        this.actualYield = actualYield;
    }

    public String getYieldVariance() {
        return yieldVariance;
    }

    public void setYieldVariance(String yieldVariance) {
        this.yieldVariance = yieldVariance;
    }

    @Override
    public String toString() {
        return "FieldModel{" +
                "date='" + date + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", cropName='" + cropName + '\'' +
                ", numOfPlants='" + numOfPlants + '\'' +
                ", targetYield='" + targetYield + '\'' +
                ", actualYield='" + actualYield + '\'' +
                ", yieldVariance='" + yieldVariance + '\'' +
                '}';
    }
}
