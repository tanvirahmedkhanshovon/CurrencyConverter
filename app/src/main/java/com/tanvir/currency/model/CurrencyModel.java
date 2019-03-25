package com.tanvir.currency.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

}