package com.oaiscloud.billing.core.domain.exceptions.currency

import com.oaiscloud.billing.core.domain.exceptions.ConflictException

class AlreadyExistsException(code: String) : ConflictException("A currency with the code '$code' already exists")