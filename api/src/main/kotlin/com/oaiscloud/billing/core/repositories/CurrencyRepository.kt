package com.oaiscloud.billing.core.repositories

import com.oaiscloud.billing.core.domain.entities.Currency
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime

@ApplicationScoped
class CurrencyRepository : PanacheRepository<Currency> {
    fun findCurrenciesByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Currency> {
        return list("createdAt BETWEEN ?1 AND ?2", startDate, endDate);
    }
}