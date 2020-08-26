package com.converter.classes;

import com.converter.models.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurrencyRate {

    public static Currency getCurrencyById(String url, String valuteId) throws IOException, ParseException {

        List<Currency> actualCurrency = getCurrenciesList(url);

        return actualCurrency.stream().filter(currency -> valuteId.equals(currency.getValuteId())).findAny().orElse(null);
    }

    public static List<Currency> getCurrenciesList(String url) throws IOException, ParseException {

        ArrayList<Currency> actualCurrency = new ArrayList<>();

        Document doc = Jsoup.connect(url).get(); //http://www.cbr.ru/scripts/XML_daily.asp

        List<Element> elements = doc.getElementsByTag("Valute");

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);

        String dateString = doc.getElementsByTag("ValCurs").attr("Date"); // получаем дату

        for (Element e : elements) {
            actualCurrency.add(new Currency(
                    e.getElementsByTag("Valute").attr("ID"),
                    e.getElementsByTag("CharCode").text(),
                    e.getElementsByTag("Name").text(),
                    Double.parseDouble(e.getElementsByTag("Nominal").text()),
                    numberFormat.parse(e.getElementsByTag("Value").text()).doubleValue(),
                    format.parse(dateString)
            ));
        }

        return actualCurrency;
    }

    public static Double convert(Currency firstCurrency,
                                 Currency secondCurrency,
                                 Double amount){
        return amount*(secondCurrency.getNominal()*firstCurrency.getValue()/
                      (secondCurrency.getValue()*firstCurrency.getNominal()));
    }
}
