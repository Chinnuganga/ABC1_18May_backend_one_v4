package com.scripted.PDF;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.scripted.generic.FileUtils;

public class PDFReportListner implements ITestListener {
	public static WebDriver driver = null;
	public static String cdir = System.getProperty("user.dir");
	public static String currentTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());
	public static String destination;
	public static Document document;
	public static int passCount = 0;
	public static int failCount = 0;
	public static int skipCount = 0;
	PdfPTable successTable = null, failTable = null, skiptable = null;
	public static PdfWriter writer = null;
	public HashMap<Integer, Throwable> throwableMap = null;
	public int nbExceptions = 0;
	public static Logger LOGGER = Logger.getLogger(PDFReportListner.class);
	public PDFReportListner() {
		try {
		this.document = new Document(PageSize.A4, 36, 36, 68, 36);
		this.throwableMap = new HashMap<Integer, Throwable>();
		String path = cdir + "/SkriptmateReport/PDFReport" + File.separator + currentTimeStamp;
		FileUtils.makeDirs(path);
		destination=path + File.separator + "PDFTest_Report.pdf";
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while making directory" + e1);
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}
	public static void setDriver(WebDriver driver) {
		PDFReportListner.driver = driver;
	}

	public void onTestSuccess(ITestResult result) {
		LOGGER.info("I am in onTestSuccess method (" + result + ")");
		try {
		if (successTable == null) {
			this.successTable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f });
			Paragraph pgraph = new Paragraph("Passed Testcases",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD));
			pgraph.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(pgraph);
			cell.setColspan(4);
			cell.setBackgroundColor(new BaseColor(27, 131, 72));
			this.successTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Exception"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.successTable.addCell(cell);
		}

		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName()));
		this.successTable.addCell(cell);
		passCount = passCount + result.getMethod().getInvocationCount();
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.successTable.addCell(cell);

		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			Paragraph excep = new Paragraph(throwable.toString());
			cell = new PdfPCell(excep);
			this.successTable.addCell(cell);
		} else {
			this.successTable.addCell(new PdfPCell(new Paragraph("")));
		}
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error on test success customize in report" + e1);
		}
	}

	public void onTestFailure(ITestResult result) {
		LOGGER.info("I am in onTestFailure method (" + result + ")");
		try {
		if (this.failTable == null) {
			this.failTable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f });
			Paragraph p = new Paragraph("Failed Testcases",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(4);
			cell.setBackgroundColor(new BaseColor(204, 0, 0));
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Exception"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.failTable.addCell(cell);
		}

		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName()));
		failCount = result.getMethod().getInvocationCount();
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.failTable.addCell(cell);
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			Paragraph excep = new Paragraph(throwable.toString());
			cell = new PdfPCell(excep);
			this.failTable.addCell(cell);
		} else {
			this.failTable.addCell(new PdfPCell(new Paragraph("")));
		}
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error on test failure customize the report" + e1);
		}
	}

	public void onTestSkipped(ITestResult result) {
		LOGGER.info("I am in onTestSkipped method (" + result + ")");
		try {
		if (skiptable == null) {
			this.skiptable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f });
			Paragraph pgraph = new Paragraph("Skipped Testcases",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD));
			pgraph.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(pgraph);
			cell.setColspan(4);
			cell.setBackgroundColor(new BaseColor(236, 168, 67));
			this.skiptable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.skiptable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.skiptable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.skiptable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Exception"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			this.skiptable.addCell(cell);
		}

		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().getName()));
		this.skiptable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName()));
		this.skiptable.addCell(cell);
		skipCount = skipCount + result.getMethod().getInvocationCount();
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.skiptable.addCell(cell);

		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			Paragraph excep = new Paragraph(throwable.toString());
			cell = new PdfPCell(excep);
			this.skiptable.addCell(cell);
		} else {
			this.skiptable.addCell(new PdfPCell(new Paragraph("")));
		}	} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error on test skip customize in report" + e1);
		}
	}

	public void onStart(ITestContext context) {
		LOGGER.info("I am in onStart method (" + context + ")");

		try {
		    writer = PdfWriter.getInstance(this.document, new FileOutputStream(destination));
			PDFReportUtils event = new PDFReportUtils();
			writer.setPageEvent(event);
		} catch (Exception e2) {
			e2.printStackTrace();
			LOGGER.error("Error on writing page in report" + e2);
		}
		this.document.open();
		Paragraph pgraph = new Paragraph(" Skriptmate Test Report",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK));
		pgraph.setAlignment(Element.ALIGN_CENTER);
		try {
			this.document.add(pgraph);
			Paragraph pgraph2 = new Paragraph("Date&Time:" + (new Date().toString()),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
			pgraph2.setAlignment(Element.ALIGN_CENTER);
			this.document.add(pgraph2);

		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error on writing paragraph in report" + e1);
		}
	}

	public void onFinish(ITestContext context) {
		try {
			writeChartToPDF(PDFReportUtils.generatePieChart(passCount, failCount, skipCount), destination);
			
			if (this.successTable != null) {
				LOGGER.info("Adding the passed testcases into table");
				this.successTable.setSpacingBefore(300);
				this.document.add(this.successTable);
				this.successTable.setSpacingAfter(20f);
			}
			if (this.failTable != null) {
				LOGGER.info("Adding the failed testcases into table");
				if(this.successTable == null)
					this.failTable.setSpacingBefore(300);
				else
					this.failTable.setSpacingBefore(25f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(20f);
			}

			if (this.skiptable != null) {
				LOGGER.info("Adding the skipped testcases into table");
				if(this.successTable == null && this.failTable == null)
					this.skiptable.setSpacingBefore(300);
				else
					this.skiptable.setSpacingBefore(25f);
				this.document.add(this.skiptable);
				this.skiptable.setSpacingAfter(20f);
			}
			LOGGER.info("Skriptmate PDF Report Generated Successfully in the location"+destination);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while generating the pdf report" + e);
		}

		this.document.close();
	}

	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub public static void theme(JFreeChart chart) {

	}
 
	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
	}
	public static void writeChartToPDF(JFreeChart chart, String fileName) {
		try {
			PdfContentByte contentByte = writer.getDirectContent();
			int width = 400; // width of PieChart
			int height = 250; // height of pieChart
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = new PdfGraphics2D(template, width, height);
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(graphics2d, rectangle2d);
			graphics2d.dispose();
			contentByte.addTemplate(template, 95, 455);// pdf width and hight

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while writing chart to pdf report" + e);
		}
	}

}
