package com.oaiscloud.billing.core.types

data class GenerateXLSXReportData(
    val sheetName: String,
    val header: Array<String>,
    val rows: Array<Array<*>>,
);