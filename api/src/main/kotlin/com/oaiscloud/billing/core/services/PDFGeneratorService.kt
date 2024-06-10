package com.oaiscloud.billing.core.services

import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.oaiscloud.billing.core.types.GeneratePdfReportData
import jakarta.enterprise.context.ApplicationScoped
import java.io.ByteArrayOutputStream

@ApplicationScoped
class PDFGeneratorService {
    fun generateReport(data: GeneratePdfReportData): ByteArray {
        val outputStream = ByteArrayOutputStream();
        val writer = PdfWriter(outputStream);
        val pdf = PdfDocument(writer);
        val document = Document(pdf);

        val title = Paragraph(data.title)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(20f)
            .setBold();

        document.add(title);

        if (data.paragraphs != null && data.paragraphs.size > 0) {
            data.paragraphs.forEach { paragraph ->
                document.add(Paragraph(paragraph).setMarginTop(10f).setFontSize(12f));
            };

            document.add(Paragraph().setMarginTop(10f));
        }

        if (!data.tableTitle.isNullOrBlank()) {
            val tableTitle = Paragraph(data.tableTitle)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14f)
                .setBold();

            document.add(tableTitle);
        }

        val table = createTable(data.tableHeaders, data.tableData, data.tableFooter);

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    private fun createTable(headers: Array<String>, data: Array<Array<String>>, footer: Array<Array<String>>?): Table {
        val columnWidths = headers.map { 1f }.toFloatArray();
        val table = Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        val headerColor = DeviceRgb(200, 200, 200);
        val rowColor = DeviceRgb(230, 230, 230);

        headers.forEach { header ->
            val cell = Cell().add(Paragraph(header).setBold());
            cell.setBackgroundColor(headerColor);
            cell.setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(cell);
        };

        data.forEachIndexed { rowIndex, row ->
            row.forEach { cellData ->
                val cell = Cell().add(Paragraph(cellData));
                cell.setTextAlignment(TextAlignment.CENTER);
                if (rowIndex % 2 != 0) {
                    cell.setBackgroundColor(rowColor);
                }
                table.addCell(cell);
            };
        };

        footer?.forEach { row ->
            row.forEach { cellData ->
                val cell = Cell().add(Paragraph(cellData).setBold());
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setBackgroundColor(rowColor);
                table.addCell(cell);
            };
        }

        return table;
    }
}