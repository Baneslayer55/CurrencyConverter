package com.converter.repos;

import com.converter.models.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {

    Currency findByValuteId(String valuteId);
}
