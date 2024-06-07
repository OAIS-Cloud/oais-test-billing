package com.oaiscloud.billing.core.dtos.currency

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.ws.rs.BadRequestException

data class CreateCurrencyDTO(
    @field:NotBlank()
    @field:Size(min = 1, max = 4)
    @field:Pattern(regexp = "^[A-Z]{1,4}$")
    val code: String,

    @field:NotBlank()
    @field:Size(min = 3, max = 256)
    val name: String
) {
    fun validate() {
        if (!code.all { it.isLetter() }) {
            throw BadRequestException("The currency code must contain only alphabetic characters");
        }

        if (code.isEmpty() || code.length > 4) {
            throw BadRequestException("The 'code' field must be between 1 and 4 characters");
        }

        if (name.trim().length < 3 || name.trim().length > 256) {
            throw BadRequestException("The 'name' field must be between 3 and 256 characters");
        }
    }
}