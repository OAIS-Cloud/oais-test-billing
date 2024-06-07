package com.oaiscloud.billing.core.domain.exceptions.currency

import jakarta.ws.rs.NotFoundException

class CurrencyNotFoundException : NotFoundException("Currency not found")