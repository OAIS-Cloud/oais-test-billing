package com.oaiscloud.billing.core.services

import com.oaiscloud.billing.core.types.GenerateXLSXReportData
import jakarta.enterprise.context.ApplicationScoped
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream

@ApplicationScoped
class XLSXGeneratorService {
    fun generateReport(data: GenerateXLSXReportData): ByteArray {
        val workbook = XSSFWorkbook();
        val sheet = workbook.createSheet(data.sheetName);

        val headerStyle = workbook.createCellStyle();
        val font = workbook.createFont();
        font.bold = true;
        headerStyle.setFont(font);
        headerStyle.alignment = HorizontalAlignment.CENTER;
        headerStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex()
        headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

        val headerRow = sheet.createRow(0);
        for ((index, header) in data.header.withIndex()) {
            val cell = headerRow.createCell(index);
            cell.setCellValue(header)
            cell.cellStyle = headerStyle;

            sheet.setColumnWidth(index, (header.length * 256) + 2048);
        }

        var rowIndex = 1
        for (row in data.rows) {
            val dataRow = sheet.createRow(rowIndex++);
            row.forEachIndexed { cellIndex, cellContent ->
                val cell = dataRow.createCell(cellIndex);
                when(cellContent) {
                    is String -> cell.setCellValue(cellContent);
                    is Double -> cell.setCellValue(cellContent);
                    else -> cell.setCellValue(cellContent.toString());
                }
            };
        };

        val outputStream = ByteArrayOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return outputStream.toByteArray();
    }
}