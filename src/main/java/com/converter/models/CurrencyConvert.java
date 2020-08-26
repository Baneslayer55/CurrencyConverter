package com.converter.models;

import javax.persistence.*;

@Entity
public class CurrencyConvert {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Currency firstValute;

    private Double firstValuteValue;

    private Currency secondValute;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public CurrencyConvert() {
    }

    public CurrencyConvert(Currency firstValute, Double firstValuteValue, Currency secondValute, User user) {
        this.firstValute = firstValute;
        this.firstValuteValue = firstValuteValue;
        this.secondValute = secondValute;
        this.user = user;
    }

    public Currency getFirstValute() {
        return firstValute;
    }

    public void setFirstValute(Currency firstValute) {
        this.firstValute = firstValute;
    }

    public Double getFirstValuteValue() {
        return firstValuteValue;
    }

    public void setFirstValuteValue(Double firstValuteValue) {
        this.firstValuteValue = firstValuteValue;
    }

    public Currency getSecondValute() {
        return secondValute;
    }

    public void setSecondValute(Currency secondValute) {
        this.secondValute = secondValute;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
