package com.oaiscloud.billing.core.domain.exceptions.contract

import jakarta.ws.rs.NotFoundException

class ContractNotFoundException : NotFoundException("Contract not found")