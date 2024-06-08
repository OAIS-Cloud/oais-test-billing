package com.oaiscloud.billing.core.resources

import com.oaiscloud.billing.core.domain.entities.Contract
import com.oaiscloud.billing.core.domain.exceptions.contract.ContractNotFoundException
import com.oaiscloud.billing.core.domain.exceptions.currency.CurrencyNotFoundException
import com.oaiscloud.billing.core.dtos.contract.CreateContractDTO
import com.oaiscloud.billing.core.dtos.contract.UpdateContractDTO
import com.oaiscloud.billing.core.infra.http.Routes
import com.oaiscloud.billing.core.repositories.CurrencyRepository
import com.oaiscloud.billing.core.services.ContractService
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path(Routes.CONTRACTS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class ContractResource(private val contractService: ContractService) {
    @Inject
    lateinit var currencyRepository: CurrencyRepository;

    @GET
    fun getContracts(): List<Contract>
        = contractService.listAll();

    @POST
    fun createContract(@Valid createContractDTO: CreateContractDTO): Response {
        createContractDTO.validate();

        val currency = currencyRepository.findById(createContractDTO.currencyId)
            ?: throw CurrencyNotFoundException();

        var contract = Contract().apply {
            this.currency = currency
            this.currencyValue = createContractDTO.currencyValue
            this.invoiceClosingDay = createContractDTO.invoiceClosingDay
        }

        contractService.addContract(contract);

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{contractId}")
    fun deleteContract(@PathParam("contractId") id: Long): Response {
        val deleted = contractService.removeContractById(id);
        if (!deleted) {
            throw ContractNotFoundException();
        }

        return Response.ok().build();
    }

    @PUT
    @Path("/{contractId}")
    fun updateContract(@PathParam("contractId") id: Long, updateContractDTO: UpdateContractDTO): Response {
        updateContractDTO.validate();

        val currency = currencyRepository.findById(updateContractDTO.currencyId)
            ?: throw CurrencyNotFoundException();

        var contract = Contract().apply {
            this.currency = currency
            this.currencyValue = updateContractDTO.currencyValue
            this.invoiceClosingDay = updateContractDTO.invoiceClosingDay
        }

        contractService.updateContractById(id, contract);

        return Response.ok().build();
    }
}