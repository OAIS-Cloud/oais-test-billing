package com.oaiscloud.billing.core.repositories

import com.oaiscloud.billing.core.domain.entities.Currency
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CurrencyRepository : PanacheRepository<Currency>