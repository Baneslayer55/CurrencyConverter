package com.converter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String valuteId;

    private String charcode;

    private String name;

    private Double nominal;

    private Double value;

    private Date date;

    public Currency() {
    }

    public Currency(String valuteId, String charcode, String name, Double nominal, Double value, Date date) {
        this.valuteId = valuteId;
        this.charcode = charcode;
        this.name = name;
        this.nominal = nominal;
        this.value = value;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValuteId() {
        return valuteId;
    }

    public void setValuteId(String valuteId) {
        this.valuteId = valuteId;
    }

    public String getCharcode() {
        return charcode;
    }

    public void setCharcode(String charcode) {
        this.charcode = charcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNominal() {
        return nominal;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
