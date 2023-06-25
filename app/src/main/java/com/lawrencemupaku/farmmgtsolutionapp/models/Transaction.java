package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String date;
    private String totalSalesRevenue;
    private String costsOfGoodsSold;
    private String operatingExpenses;
    private String grossProfit;
    private String profitLoss;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalSalesRevenue() {
        return totalSalesRevenue;
    }

    public void setTotalSalesRevenue(String totalSalesRevenue) {
        this.totalSalesRevenue = totalSalesRevenue;
    }

    public String getCostsOfGoodsSold() {
        return costsOfGoodsSold;
    }

    public void setCostsOfGoodsSold(String costsOfGoodsSold) {
        this.costsOfGoodsSold = costsOfGoodsSold;
    }

    public String getOperatingExpenses() {
        return operatingExpenses;
    }

    public void setOperatingExpenses(String operatingExpenses) {
        this.operatingExpenses = operatingExpenses;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(String profitLoss) {
        this.profitLoss = profitLoss;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "totalSalesRevenue='" + totalSalesRevenue + '\'' +
                ", costsOfGoodsSold='" + costsOfGoodsSold + '\'' +
                ", operatingExpenses='" + operatingExpenses + '\'' +
                ", grossProfit='" + grossProfit + '\'' +
                ", profitLoss='" + profitLoss + '\'' +
                '}';
    }
}
