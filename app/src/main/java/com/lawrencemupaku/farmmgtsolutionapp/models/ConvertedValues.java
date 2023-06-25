package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class ConvertedValues implements Serializable {
    private String date;
    private String convertedSalesRevenue;
    private String convertedCOGS;
    private String convertedExpenses;
    private String convertedGrossProfit;
    private String convertedProfitLoss;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConvertedSalesRevenue() {
        return convertedSalesRevenue;
    }

    public void setConvertedSalesRevenue(String convertedSalesRevenue) {
        this.convertedSalesRevenue = convertedSalesRevenue;
    }

    public String getConvertedCOGS() {
        return convertedCOGS;
    }

    public void setConvertedCOGS(String convertedCOGS) {
        this.convertedCOGS = convertedCOGS;
    }

    public String getConvertedExpenses() {
        return convertedExpenses;
    }

    public void setConvertedExpenses(String convertedExpenses) {
        this.convertedExpenses = convertedExpenses;
    }

    public String getConvertedGrossProfit() {
        return convertedGrossProfit;
    }

    public void setConvertedGrossProfit(String convertedGrossProfit) {
        this.convertedGrossProfit = convertedGrossProfit;
    }

    public String getConvertedProfitLoss() {
        return convertedProfitLoss;
    }

    public void setConvertedProfitLoss(String convertedProfitLoss) {
        this.convertedProfitLoss = convertedProfitLoss;
    }
}
