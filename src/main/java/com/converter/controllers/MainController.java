package com.converter.controllers;

import com.converter.classes.CurrencyRate;
import com.converter.models.Currency;
import com.converter.models.Message;
import com.converter.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String Add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){

        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

    @GetMapping("/exchangerate")
    public String exchangerate(Map<String, Object> model) throws IOException, ParseException {

        Iterable<Currency> currencies = CurrencyRate.GetActualCurrency();

        model.put("currencies", currencies);

        return "exchangerate";
    }

}