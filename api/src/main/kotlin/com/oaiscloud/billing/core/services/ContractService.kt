package com.oaiscloud.billing.core.services

import com.oaiscloud.billing.core.domain.entities.Contract
import com.oaiscloud.billing.core.domain.exceptions.contract.ContractNotFoundException
import com.oaiscloud.billing.core.repositories.ContractRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@ApplicationScoped
class ContractService(private val repository: ContractRepository) {
    fun listAll(): List<Contract>
        = repository.listAll();

    @Transactional
    fun addContract(contract: Contract) {
        repository.persist(contract);
    }

    @Transactional
    fun removeContractById(id: Long): Boolean {
        return repository.deleteById(id);
    }

    @Transactional
    fun updateContractById(id: Long, contractUpdate: Contract): Contract {
        val contract = repository.findById(id) ?: throw ContractNotFoundException();

        contract.currency = contractUpdate.currency;
        contract.currencyValue = contractUpdate.currencyValue;
        contract.invoiceClosingDay = contractUpdate.invoiceClosingDay;
        repository.persist(contract);

        return contract;
    }

    fun getContractsAnalyticsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Map<LocalDate, Long> {
        val contracts = repository.findContractsByDateRange(startDate, endDate);

        return contracts
            .groupingBy { it.createdAt.toLocalDate() }
            .eachCount()
            .mapValues { it.value.toLong() };
    }
}