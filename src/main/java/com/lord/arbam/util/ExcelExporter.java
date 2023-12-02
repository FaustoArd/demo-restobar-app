package com.lord.arbam.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lord.arbam.model.WorkingDay;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExcelExporter {

	private XSSFWorkbook workBook;
	private XSSFSheet sheet;
	private WorkingDay workinkDayExport;

	public ExcelExporter(WorkingDay workinkDayExport) {
		this.workinkDayExport = workinkDayExport;
		workBook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workBook.createSheet("WorkingDay");

		Row row = sheet.createRow(0);
		CellStyle style = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 1, "Fecha", style);
		createCell(row, 1, "Total incio de caja", style);
		createCell(row, 1, "Total + caja", style);
		createCell(row, 1, "Total + caja - empleados", style);
		createCell(row, 1, "Total Efectivo", style);
		createCell(row, 1, "Total tebito", style);
		createCell(row, 1, "Total credito", style);
		createCell(row, 1, "Total transferencia", style);
		createCell(row, 1, "Total credito", style);
		createCell(row, 1, "Total Mercado pago", style);
		createCell(row, 1, "Total Salario empleados", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);

	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		WorkingDay wDay = workinkDayExport;
		Row row = sheet.createRow(rowCount++);
		int columnCount = 0;
		createCell(row, columnCount++, wDay.getDate(), style);
		createCell(row, columnCount++, wDay.getTotalStartCash(), style);
		createCell(row, columnCount++, wDay.getTotalWorkingDay(), style);
		createCell(row, columnCount++, wDay.getTotalWorkingDayWithDiscount(), style);
		createCell(row, columnCount++, wDay.getTotalCash(), style);
		createCell(row, columnCount++, wDay.getTotalDebit(), style);
		createCell(row, columnCount++, wDay.getTotalTransf(), style);
		createCell(row, columnCount++, wDay.getTotalCredit(), style);
		createCell(row, columnCount++, wDay.getTotalMP(), style);
		createCell(row, columnCount++, wDay.getTotalEmployeeSalary(), style);

	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();

	}

}
