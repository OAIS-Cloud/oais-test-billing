package com.oaiscloud.billing.core.services

import com.oaiscloud.billing.core.domain.entities.Currency
import com.oaiscloud.billing.core.domain.exceptions.currency.AlreadyExistsException
import com.oaiscloud.billing.core.domain.exceptions.currency.CurrencyNotFoundException
import com.oaiscloud.billing.core.repositories.CurrencyRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class CurrencyService(private val repository: CurrencyRepository) {
    fun listAll(): List<Currency>
        = repository.listAll();

    @Transactional
    fun addCurrency(currency: Currency) {
        val currencyCountWithSameCode = repository.count("code", currency.code);
        if (currencyCountWithSameCode > 0) {
            throw AlreadyExistsException(currency.code);
        }

        repository.persist(currency);
    }

    @Transactional
    fun removeCurrencyById(id: Long): Boolean {
        return repository.deleteById(id);
    }

    @Transactional
    fun updateCurrencyById(id: Long, currencyUpdate: Currency): Currency {
        val currency = repository.findById(id) ?: throw CurrencyNotFoundException();

        currency.code = currencyUpdate.code;
        currency.name = currencyUpdate.name;
        repository.persist(currency);

        return currency;
    }
}