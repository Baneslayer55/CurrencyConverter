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

@RestController
@RequestMapping("currencyrateinfo")
public class RestCurrencyController {

    @Autowired
    CurrencyRepo currencyRepo;

    @Autowired
    CurrencyConvertRepo currencyConvertRepo;

    @GetMapping("actualcurrencyrate")
    public Iterable<Currency> currenciesRateInfo() throws IOException, ParseException {

        currencyRepo.saveAll(CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp")); // кидает дубликаты, ввести проверку

        return currencyRepo.findAll(); //CurrencyRate.getCurrenciesList("http://www.cbr.ru/scripts/XML_daily.asp");
    }

    @GetMapping("convert")
    public CurrencyConvert currencyConvert(@AuthenticationPrincipal User user) throws IOException, ParseException {

        //тестим, конвертация с юзером работает, без юзера - нет
        CurrencyConvert currencyConvert = new CurrencyConvert();

        currencyConvert.setFirstValute(currencyRepo.findByValuteId("R01010")); //исходная валюта

        currencyConvert.setSecondValute(currencyRepo.findByValuteId("R01060")); // в какую конвертим

        currencyConvert.setFirstValuteAmount(10.0);

        currencyConvert.setUser(user);

        currencyConvert.setSecondValuteAmount(CurrencyRate.convert(currencyConvert.getFirstValute(),
                                                                   currencyConvert.getSecondValute(),
                                                                   currencyConvert.getFirstValuteAmount()));
        currencyConvertRepo.save(currencyConvert);

        return currencyConvert;
    }

    @GetMapping("history")
    public Iterable<CurrencyConvert> history(){

        return currencyConvertRepo.findAll();
    }
}
