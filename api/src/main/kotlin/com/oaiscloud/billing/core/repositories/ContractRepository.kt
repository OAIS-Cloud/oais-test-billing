package com.oaiscloud.billing.core.repositories

import com.oaiscloud.billing.core.domain.entities.Contract
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime

@ApplicationScoped
class ContractRepository : PanacheRepository<Contract> {
    fun findContractsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Contract> {
        return list("createdAt BETWEEN ?1 AND ?2", startDate, endDate)
    }
}