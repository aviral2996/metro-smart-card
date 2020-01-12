package org.metro.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.metro.exception.MetroCardReportException;
import org.metro.models.Journey;
import org.metro.models.MetroCard;

public class CreateCardReport {

	private static String[] columns = { "Source", "Swipe In Time", "Destination", "Swipe Out Time", "Fare" };

	public static void createMetroCardReport(MetroCard metroCard, List<Journey> journeyList) throws IOException {
		Workbook workbook = null;
		FileOutputStream fileOut = null;
		try {
			// Create a Workbook
			workbook = new XSSFWorkbook();

			// Create a Sheet
			Sheet sheet = workbook.createSheet("Metro");

			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			// Create a CellStyle with the font
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			Integer i = 1;
			Row metroId = sheet.createRow(i++);
			Cell cell1 = metroId.createCell(0);
			cell1.setCellValue("Metro Card Id");
			cell1.setCellStyle(headerCellStyle);

			Cell cell2 = metroId.createCell(1);
			cell2.setCellValue(metroCard.getId());

			Row userName = sheet.createRow(i++);
			Cell cell3 = userName.createCell(0);
			cell3.setCellValue("Metro User Name");
			cell3.setCellStyle(headerCellStyle);

			Cell cell4 = userName.createCell(1);
			cell4.setCellValue(metroCard.getName());

			Row balance = sheet.createRow(i++);
			Cell cell5 = balance.createCell(0);
			cell5.setCellValue("Metro Card Balance");
			cell5.setCellStyle(headerCellStyle);

			Cell cell6 = balance.createCell(1);
			cell6.setCellValue(metroCard.getBalance());

			sheet.createRow(i++);
			sheet.createRow(i++);

			Row headerRow = sheet.createRow(i++);
			Integer k = 2;
			for (int j = 0; j < columns.length; j++) {
				Cell cell = headerRow.createCell(k++);
				cell.setCellValue(columns[j]);
				cell.setCellStyle(headerCellStyle);
			}

			for (Journey journey : journeyList) {
				Row row = sheet.createRow(i++);
				k = 2;
				Cell source = row.createCell(k++);
				source.setCellValue(journey.getSource().toString());

				Cell swipeIn = row.createCell(k++);
				swipeIn.setCellValue(journey.getSwipeIn().toString());

				Cell destination = row.createCell(k++);
				destination.setCellValue(journey.getDestination().toString());

				Cell swipeOut = row.createCell(k++);
				swipeOut.setCellValue(journey.getSwipeOut().toString());

				Cell fare = row.createCell(k++);
				fare.setCellValue(journey.getFare().toString());
			}

			for (i = 0; i < 7; i++) {
				sheet.autoSizeColumn(i);
			}

			String filePath = new File("src/main/resources/metro-report-" + metroCard.getId() + ".xlsx")
					.getAbsolutePath();
			fileOut = new FileOutputStream(filePath);

			workbook.write(fileOut);
		} catch (RuntimeException e) {
			throw new MetroCardReportException();
		} finally {
			fileOut.close();
			workbook.close();

		}
	}
}
