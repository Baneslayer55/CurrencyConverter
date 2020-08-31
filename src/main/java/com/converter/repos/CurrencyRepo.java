package com.converter.repos;

import com.converter.models.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {

    Currency findByValuteId(String valuteId);

    Currency findByValuteIdAndDate(String valuteId, Date date);

    Iterable<Currency> findByDate(Date date);
}
