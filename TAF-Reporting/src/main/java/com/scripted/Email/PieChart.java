package com.scripted.Email;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {

	/**
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static Logger LOGGER = Logger.getLogger(PieChart.class);

	static void generateChartFromJsonValue(String jsonFile) throws IOException, NumberFormatException {
		try {
			Map result = ScenarioCount.scenarioCountdetails(jsonFile);

			Object passcount = result.get("passcount");
			Object failcount = result.get("failcount");

			DefaultPieDataset dataset = new DefaultPieDataset();

			dataset.setValue("Scenarios Failed", new Double(failcount.toString()));
			dataset.setValue("Scenarios Passed", new Double(passcount.toString()));

			JFreeChart chart = ChartFactory.createPieChart("Scenarios Status", // chart title
					dataset, // data
					true, // include legend
					true, false);

			PiePlot piePlot = (PiePlot) chart.getPlot();
			piePlot.setSectionPaint(piePlot.getDataset().getKey(1), java.awt.Color.green);
			piePlot.setBackgroundPaint(java.awt.Color.white);
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			File pieChart = new File("src/main/resources/Email/PieChart.jpeg");
			ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while generating chart from json file " + "Exception :" + e);
		}
	}
}