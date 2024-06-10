package com.oaiscloud.billing.core.resources

import com.oaiscloud.billing.core.infra.http.Routes
import com.oaiscloud.billing.core.repositories.ContractRepository
import com.oaiscloud.billing.core.services.PDFGeneratorService
import com.oaiscloud.billing.core.services.XLSXGeneratorService
import com.oaiscloud.billing.core.types.GeneratePdfReportData
import com.oaiscloud.billing.core.types.GenerateXLSXReportData
import com.oaiscloud.billing.core.utils.NumberUtils
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Path(Routes.REPORTS)
class ReportResource(
    private val pdfGeneratorService: PDFGeneratorService,
    private val xlsxGeneratorService: XLSXGeneratorService
) {
    @Inject
    lateinit var contractRepository: ContractRepository;

    @GET
    @Path("/contracts")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun getContractsReport(@QueryParam("xlsx") xlsx: String?): Response {
        val contracts = contractRepository.listAll();

        val fileBytes : ByteArray;

        if (xlsx == "true") {
            fileBytes = xlsxGeneratorService.generateReport(
                GenerateXLSXReportData(
                    sheetName = "Contratos",
                    header = arrayOf("ID", "Moeda", "Valor da moeda", "Dia/fatura"),
                    rows = contracts.map {
                        arrayOf(
                            it.id.toString(),
                            it.currency.name,
                            it.currencyValue.toDouble(),
                            it.invoiceClosingDay.toString()
                        );
                    }.toTypedArray(),
                )
            );
        } else {
            fileBytes = pdfGeneratorService.generateReport(
                GeneratePdfReportData(
                    title = "Relatório de Contratos",
                    paragraphs = arrayOf("Total de contratos: ${contracts.size}"),
                    tableTitle = "Contratos",
                    tableHeaders = arrayOf("ID", "Moeda", "Valor da moeda", "Dia/fatura"),
                    tableData = contracts.map {
                        arrayOf(
                            it.id.toString(),
                            it.currency.name,
                            NumberUtils.currencyFormat.format(it.currencyValue),
                            it.invoiceClosingDay.toString()
                        );
                    }.toTypedArray(),
                    tableFooter = null
                )
            );
        }

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val formattedDateTime = currentDateTime.format(formatter)

        return Response.ok(fileBytes)
            .header(
                "Content-Disposition",
                "inline; filename=\"contracts_report_$formattedDateTime.${if (xlsx == "true") "xlsx" else "pdf"}\""
            )
            .build()
    }
}