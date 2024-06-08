package com.oaiscloud.billing.core.domain.exceptions.currency

import com.oaiscloud.billing.core.domain.exceptions.ConflictException

class CannotDeleteCurrencyException : ConflictException("Unable to delete this currency because it has existing contracts")