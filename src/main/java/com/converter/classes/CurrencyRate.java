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

    public static List<Currency> GetActualCurrency() throws IOException, ParseException {

        ArrayList<Currency> actualCurrency = new ArrayList<Currency>();

        Document doc = Jsoup.connect("http://www.cbr.ru/scripts/XML_daily.asp").get();

        List<Element> elements = doc.getElementsByTag("Valute");

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat numberFormatormat = NumberFormat.getInstance(Locale.FRANCE);

        String dateString = doc.getElementsByTag("ValCurs").attr("Date"); // получаем дату

        for (Element e:elements) {
            actualCurrency.add(new Currency(
                    e.getElementsByTag("Valute").attr("ID"),
                    e.getElementsByTag("CharCode").text(),
                    e.getElementsByTag("Name").text(),
                    Double.parseDouble(e.getElementsByTag("Nominal").text()),
                    numberFormatormat.parse(e.getElementsByTag("Value").text()).doubleValue(),
                    format.parse(dateString)
            ));
        }

        return actualCurrency;
    }
    /*
    this.valuteId = valuteId;
        this.charcode = charcode;
        this.name = name;
        this.nominal = nominal;
        this.value = value;
        this.date = date;
    */
    /*getElementsByTag("Name").text()
     Document doc = Jsoup.connect("http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002").get();

        String dateString = doc.getElementsByTag("ValCurs").attr("Date"); // получаем дату

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date date = format.parse(dateString);

        List<Element> elements = doc.getElementsByTag("Valute");
        List<Element> lowLevelElements = elements.get(0).getAllElements();

        for (Element element:
             elements) {
            System.out.println(element);

        System.out.println(elements.size());
        for (
    Element e:elements) {
        System.out.println(e.getElementsByTag("Name").text());
    }

        System.out.println(date);
    */
}
