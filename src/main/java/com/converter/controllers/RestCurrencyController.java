package com.converter.controllers;


import com.converter.classes.CurrencyRate;
import com.converter.models.Currency;
import com.converter.models.CurrencyConvert;
import com.converter.models.User;
import com.converter.repos.CurrencyConvertRepo;
import com.converter.repos.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("currencyrateinfo")
public class RestCurrencyController {

    private Date currentDate= new Date();;

    @Autowired
    CurrencyRepo currencyRepo;

    @Autowired
    CurrencyConvertRepo currencyConvertRepo;

    @GetMapping("archive")
    public Iterable<Currency> archive(){
        return currencyRepo.findAll();
    }

    @GetMapping("actualcurrencyrate")
    public Iterable<Currency> currenciesRateInfo() throws IOException, ParseException {

        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);

        for (Currency cur:CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp")) {
            if (currencyRepo.findByValuteIdAndDate(cur.getValuteId(), cur.getDate()) == null){
                currencyRepo.save(cur);
            }
        }
        return currencyRepo.findByDate(currentDate); //CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp");
    }

    @GetMapping("convert")
    public CurrencyConvert currencyConvert(@AuthenticationPrincipal User user) throws IOException, ParseException {

        CurrencyConvert currencyConvert = new CurrencyConvert();

        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);

        currencyConvert.setFirstValute(currencyRepo.findByValuteIdAndDate("R01020A",currentDate)); //исходная валюта

        currencyConvert.setSecondValute(currencyRepo.findByValuteIdAndDate("R01720", currentDate)); // в какую конвертим

        currencyConvert.setFirstValuteAmount(100.0);

        currencyConvert.setUser(user);

        currencyConvert.setSecondValuteAmount(CurrencyRate.convert(currencyConvert.getFirstValute(),
                                                                   currencyConvert.getSecondValute(),
                                                                   currencyConvert.getFirstValuteAmount()));

        if(user != null){
            currencyConvertRepo.save(currencyConvert);
        }

        return currencyConvert;
    }

    @GetMapping("history")
    public Iterable<CurrencyConvert> history(@AuthenticationPrincipal User user){

        return currencyConvertRepo.findByUser(user);
    }

    @GetMapping("deleteall")//для тестов
    public void deleteAllCurrencies(){
        currencyConvertRepo.deleteAll();
        currencyRepo.deleteAll();
    }

    @GetMapping("findvalute")
    public Currency currency(){
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        Currency cur = currencyRepo.findByValuteIdAndDate("R01720", currentDate);
        return cur;

    }
}
