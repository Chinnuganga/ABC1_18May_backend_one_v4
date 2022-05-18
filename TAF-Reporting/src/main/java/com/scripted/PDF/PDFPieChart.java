package com.scripted.PDF;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import com.scripted.Extent.ExtentUtils;

public class PDFPieChart {

	/**
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static Logger LOGGER = Logger.getLogger(ExtentUtils.class);
	static void generateChartFromJsonValue(String jsonFile) throws IOException, NumberFormatException {
		try {
		Map<?, ?> result = DataFromJSON.scenarioCount(jsonFile);
		String scenarioName = "";
		String passCount = null;
		String failCount = null;
		for (Map.Entry<?, ?> scenarioMap : result.entrySet()) {
			passCount = scenarioMap.getValue().toString().split("##")[0];
			failCount = scenarioMap.getValue().toString().split("##")[1];
		}

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Passed", new Double(passCount.toString()));
		dataset.setValue("Failed", new Double(failCount.toString()));
		JFreeChart chart = ChartFactory.createPieChart3D("Test Cases Report", dataset, true, true, false);
		theme(chart);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setForegroundAlpha(0.6f);
		plot.setCircular(true);
		plot.setSectionPaint("Passed", Color.decode("#1B8348"));
		plot.setSectionPaint("Failed", Color.decode("#CC0000"));
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

		int width = 400; /* Width of the image */
		int height = 250; /* Height of the image */
		File pieChart = new File("src/main/resources/Reporting/Email/PieChart.jpeg");
		ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while generating chart from json" + e1);
		}
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
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while generating theme" + e1);
		}
	}
}