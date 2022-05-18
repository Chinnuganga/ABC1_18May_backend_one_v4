package com.scripted.PDF;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.scripted.Extent.ExtentUtils;

public class PDFReportUtils extends PdfPageEventHelper {

	public static PdfTemplate temp;
	public static Image total;
	private static String cdir = System.getProperty("user.dir");
	public static Logger LOGGER = Logger.getLogger(ExtentUtils.class);

	public void onOpenDocument(PdfWriter writer, Document document) {
		temp = writer.getDirectContent().createTemplate(30, 16);
		try {
			total = Image.getInstance(temp);
			total.setRole(PdfName.ARTIFACT);
		} catch (DocumentException de) {
			LOGGER.error("Error while opening the document" + de);
			throw new ExceptionConverter(de);
		}
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {
			PdfContentByte cb = writer.getDirectContent();
			addFooter(writer, document);
			addHeader(writer, document);
			Image imgSoc = Image.getInstance(cdir + "/src/main/resources/Reporting/Artefacts/logo_2.png");
			imgSoc.scaleToFit(100, 90);
			imgSoc.setAbsolutePosition(280, 760);
			cb.addImage(imgSoc);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while adding the image" + e);
		}
	}

	public static JFreeChart generatePieChart(int pass, int fail, int skip) throws IOException {
		JFreeChart chart = null;
		try {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("Passed", pass);
		dataSet.setValue("Failed", fail);
		dataSet.setValue("Skipped", skip);
		chart = ChartFactory.createPieChart3D("Testcase Report", dataSet, true, true, false);
		theme(chart);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setForegroundAlpha(0.6f);
		plot.setCircular(true);
		plot.setSectionPaint("Passed", Color.decode("#1B8348"));
		plot.setSectionPaint("Failed", Color.decode("#CC0000"));
		plot.setSectionPaint("Skipped", Color.decode("#ECA843"));
		Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
		plot.setLabelOutlinePaint(transparent);
		plot.setLabelBackgroundPaint(transparent);
		plot.setLabelShadowPaint(transparent);
		plot.setLabelLinkPaint(Color.BLACK);
		Font font = new Font("SansSerif", Font.BOLD, 10);
		plot.setLabelFont(font);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while generating pie chart" + e);
		}
		return chart;
	}

	public static void theme(JFreeChart chart) {
		try {
		String fontName = "Lucida Sans";

		StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();

		theme.setTitlePaint(Color.decode("#2f9492"));
		theme.setExtraLargeFont(new Font(fontName, Font.BOLD, 12)); // title
		theme.setLargeFont(new Font(fontName, Font.BOLD, 12)); // axis-title
		theme.setRegularFont(new Font(fontName, Font.BOLD, 11));
		theme.setRangeGridlinePaint(Color.WHITE);
		theme.setPlotBackgroundPaint(Color.white);
		theme.setChartBackgroundPaint(Color.white);
		theme.setGridBandPaint(Color.red);
		theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		theme.setBarPainter(new StandardBarPainter());
		theme.setAxisLabelPaint(Color.WHITE);
		theme.apply(chart);
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating theme" + e);
		}
	}

	public static void addHeader(PdfWriter writer, Document document) {
		try {
		PdfContentByte cb = writer.getDirectContent();
		Phrase header = new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
				(document.right() - document.left()) / 2 + document.leftMargin(), document.top() - 15, 0);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while adding header" + e);
		}

	}

	public static void addFooter(PdfWriter writer, Document document) {
		try {
		PdfContentByte cb = writer.getDirectContent();
		Phrase footer = new Phrase("" + writer.getPageNumber(),
				FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
		ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer,
				(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while adding footer" + e);
		}
	}

}