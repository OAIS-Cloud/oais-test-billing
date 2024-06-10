package com.oaiscloud.billing.core.resources

import com.oaiscloud.billing.core.infra.http.Routes
import com.oaiscloud.billing.core.services.ContractService
import com.oaiscloud.billing.core.services.CurrencyService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.time.LocalDateTime

@Path(Routes.ANALYTICS)
@Produces(MediaType.APPLICATION_JSON)
class AnalyticsResource(private val contractService: ContractService, private val currencyService: CurrencyService) {
    @GET
    fun getAnalytics(): Response {
        val startDate = LocalDateTime.now().minusMonths(1);
        val endDate = LocalDateTime.now();

        val contractsPerDay = contractService.getContractsAnalyticsBetweenDates(startDate, endDate);
        val currenciesPerDay = currencyService.getCurrenciesAnalyticsBetweenDates(startDate, endDate);

        val contractsAnalytics = contractsPerDay.map { (date, count) ->
            mapOf(
                "date" to date,
                "total_contracts" to count
            )
        };

        val currenciesAnalytics = currenciesPerDay.map { (date, count) ->
            mapOf(
                "date" to date,
                "total_currencies" to count
            )
        };

        val result = listOf(
            mapOf(
                "id" to "contracts",
                "analytics" to contractsAnalytics
            ),
            mapOf(
                "id" to "currencies",
                "analytics" to currenciesAnalytics
            )
        );

        return Response.ok(result).build();
    }
}