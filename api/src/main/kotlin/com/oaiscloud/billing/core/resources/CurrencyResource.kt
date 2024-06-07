package com.oaiscloud.billing.core.resources

import com.oaiscloud.billing.core.domain.entities.Currency
import com.oaiscloud.billing.core.domain.exceptions.currency.AlreadyExistsException
import com.oaiscloud.billing.core.dtos.currency.CreateCurrencyDTO
import com.oaiscloud.billing.core.dtos.currency.UpdateCurrencyDTO
import com.oaiscloud.billing.core.infra.http.Routes
import com.oaiscloud.billing.core.services.CurrencyService
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path(Routes.CURRENCIES)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CurrencyResource(private val currencyService: CurrencyService) {
    @GET
    fun getCurrencies(): List<Currency> {
        return currencyService.listAll();
    }

    @POST
    fun createCurrency(@Valid createCurrencyDTO: CreateCurrencyDTO): Response {
        createCurrencyDTO.validate();

        val currency = Currency().apply {
            code = createCurrencyDTO.code.uppercase();
            name = createCurrencyDTO.name.trim();
        };

        currencyService.addCurrency(currency);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{currencyId}")
    fun updateCurrency(@PathParam("currencyId") id: Long, @Valid updateCurrencyDTO: UpdateCurrencyDTO): Response {
        updateCurrencyDTO.validate();

        val currency = Currency().apply {
            code = updateCurrencyDTO.code.uppercase();
            name = updateCurrencyDTO.name.trim();
        };

        currencyService.updateCurrencyById(id, currency);

        return Response.ok().build();
    }
}