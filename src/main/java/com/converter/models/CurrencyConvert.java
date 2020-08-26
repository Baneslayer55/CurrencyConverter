package com.converter.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CurrencyConvert {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Currency firstValute;

    private Double firstValuteAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Currency secondValute;

    private Double secondValuteAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;

    public CurrencyConvert() {
        date = new Date();
    }

    public Currency getFirstValute() {
        return firstValute;
    }

    public void setFirstValute(Currency firstValute) {
        this.firstValute = firstValute;
    }

    public Double getFirstValuteAmount() {
        return firstValuteAmount;
    }

    public void setFirstValuteAmount(Double firstValuteValue) {
        this.firstValuteAmount = firstValuteValue;
    }

    public Currency getSecondValute() {
        return secondValute;
    }

    public void setSecondValute(Currency secondValute) {
        this.secondValute = secondValute;
    }

    public Double getSecondValuteAmount() {
        return secondValuteAmount;
    }

    public void setSecondValuteAmount(Double secondValuteAmount) {
        this.secondValuteAmount = secondValuteAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
