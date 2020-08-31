package com.converter.repos;

import com.converter.models.CurrencyConvert;
import com.converter.models.User;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyConvertRepo extends CrudRepository<CurrencyConvert, Long> {

    Iterable<CurrencyConvert> findByUser(User user);
}
