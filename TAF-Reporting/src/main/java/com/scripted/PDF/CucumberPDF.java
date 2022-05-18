package com.scripted.PDF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.scripted.generic.FileUtils;

public class CucumberPDF {
	public static PdfWriter writer = null;
	public static Document document;
	public static String cdir = System.getProperty("user.dir");
	public static String currentTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());
	public static String destination;
	static PdfPTable Table = null;
	public static Logger LOGGER = Logger.getLogger(CucumberPDF.class);

	public static void pdfGenerate() {

		String path = cdir + "/SkriptmateReport/PDFReport" + File.separator + currentTimeStamp;
		FileUtils.makeDirs(path);
		destination = path + File.separator + "PDFTest_Report.pdf";
		try {
			document = new Document(PageSize.A4, 36, 36, 68, 36);
			writer = PdfWriter.getInstance(document, new FileOutputStream(destination));
			PDFReportUtils event = new PDFReportUtils();
			writer.setPageEvent(event);
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println(e2.toString());
		}

		try {
			document.open();
			Paragraph pgraph = new Paragraph(" Skriptmate Test Report",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK));
			pgraph.setAlignment(Element.ALIGN_CENTER);
			document.add(pgraph);
			Paragraph pgraph2 = new Paragraph("Date&Time:" + (new Date().toString()),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
			pgraph2.setAlignment(Element.ALIGN_CENTER);
			document.add(pgraph2);

		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while generating pdf" +e1);
		}
	}

	public static void ExportJsondataPdf(String jsonFile) throws Exception, IOException {
		try {
		String imFile = "./src/main/resources/Reporting/Email/PieChart.jpeg";
		pdfGenerate();
		Map<?, ?> result = DataFromJSON.scenariodetails(jsonFile);
		PDFPieChart.generateChartFromJsonValue(jsonFile);
		Image image = Image.getInstance(imFile);
		image.setAlignment(Element.ALIGN_CENTER);
		image.scaleToFit(400, 190);
		document.add(image);
		Table = new PdfPTable(2);
		Table.setSpacingBefore(45f);
		Paragraph pgraph = new Paragraph("Cucumber Report",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD));
		PdfPCell cell = new PdfPCell(pgraph);
		cell.setColspan(2);
		cell.setBackgroundColor(new BaseColor(27, 131, 72));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Table.addCell(cell);
		cell = new PdfPCell(new Paragraph("Scenario Details"));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Table.addCell(cell);
		cell = new PdfPCell(new Paragraph("Status"));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Table.addCell(cell);
		String scenarioName = "";
		String passCount = null;
		String failCount = null;
		String flag = "PASS";
		for (Map.Entry<?, ?> scenarioMap : result.entrySet()) {
			scenarioName = scenarioMap.getKey().toString();
			passCount = scenarioMap.getValue().toString().split("##")[0];
			failCount = scenarioMap.getValue().toString().split("##")[1];
			PdfPCell cell2 = new PdfPCell(new Paragraph(scenarioName));
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			Table.addCell(cell2);
			if (Integer.parseInt(failCount) != 0)
				flag = "FAIL";
			cell2 = new PdfPCell(new Paragraph(flag));
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			Table.addCell(cell2);
		}
		document.add(Table);
		LOGGER.info("Successfully Pdf Report is generated in:"+destination);
		document.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while exporting json data" +e1);
		}
	}

}
