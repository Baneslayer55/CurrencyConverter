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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("currencyrateinfo")
public class RestCurrencyController {

    private Date currentDate= new Date();

    private void setDate(){
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
    }

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

        setDate();

        for (Currency cur:CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp")) {
            if (currencyRepo.findByValuteIdAndDate(cur.getValuteId(), cur.getDate()) == null){
                currencyRepo.save(cur);
            }
        }
        return currencyRepo.findByDate(currentDate); //CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp");
    }

    @GetMapping("convert")
    public CurrencyConvert currencyConvert(@AuthenticationPrincipal User user,
                                           @RequestParam(value = "first") String firstValuteId, //"R01020A"
                                           @RequestParam(value = "second") String secondValuteId,//"R01720"
                                           @RequestParam(value = "amount") Double amount)
                                            throws IOException, ParseException {

        CurrencyConvert currencyConvert = new CurrencyConvert();

        setDate();

        currencyConvert.setFirstValute(currencyRepo.findByValuteIdAndDate(firstValuteId,currentDate)); //исходная валюта

        currencyConvert.setSecondValute(currencyRepo.findByValuteIdAndDate(secondValuteId, currentDate)); // в какую конвертим

        currencyConvert.setFirstValuteAmount(amount);

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
    public Currency currency(@RequestParam(value = "valuteId") String valuteId){

        setDate();

        Currency cur = currencyRepo.findByValuteIdAndDate(valuteId, currentDate);//R01720
        return cur;

    }
}
