package com.oaiscloud.billing.core.dtos.contract

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.ws.rs.BadRequestException
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateContractDTO(
    @JsonProperty("currency_id")
    val currencyId: Long,

    @JsonProperty("currency_value")
    val currencyValue: BigDecimal,

    @field:Min(1)
    @field:Max(31)
    @JsonProperty("invoice_closing_day")
    val invoiceClosingDay: Short
) {
    fun validate() {
        if (currencyValue <= BigDecimal(0)) {
            throw BadRequestException("The 'currency_value' field must be greater than zero");
        }

        if (invoiceClosingDay < 1 || invoiceClosingDay > 31) {
            throw BadRequestException("The 'invoice_closing_day' field must be between 1 and 31");
        }
    }
}