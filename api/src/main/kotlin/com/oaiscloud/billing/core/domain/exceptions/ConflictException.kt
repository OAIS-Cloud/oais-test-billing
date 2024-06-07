package com.oaiscloud.billing.core.domain.exceptions

import jakarta.ws.rs.WebApplicationException

abstract class ConflictException(override val message: String) : WebApplicationException()