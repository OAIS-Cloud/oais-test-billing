package com.oaiscloud.billing.core.types

data class GeneratePdfReportData(
    val title: String,
    val paragraphs: Array<String>?,
    val tableTitle: String?,
    val tableHeaders: Array<String>,
    val tableData: Array<Array<String>>,
    val tableFooter: Array<Array<String>>?
);