package com.scripted.others;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.Allure.AllureReport;
import com.scripted.dataload.ExcelDriver;
import com.scripted.web.BrowserDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
@Listeners({ AllureListener.class })
public class Generic {

	public static String[] filePath = { "D:/TCOE/CIGNA/Excel.xlsx", "D:/TCOE/CIGNA/Excel1.xlsx",
			"D:/TCOE/CIGNA/Excel2.xlsx" };
	public static Sheet currentSheet = null;
	public static int colNo = 0;
	public static ExcelDriver excelDriver = null;
	public static Map<String, Integer> headerMap = new LinkedHashMap<String, Integer>();

	public static void getRowMapColumns(int colNumber) {
		try {
			for (Row row : currentSheet) { 
				Cell cell = row.getCell(colNumber); 
				if (cell.toString().equalsIgnoreCase("NULL")) {
					System.out.println(row.getRowNum());
					System.out.println(excelDriver.getRowMap(row.getRowNum()).toString());
					AllureReport.addStep("inside get row map null columns");
					AllureReport.addStep(excelDriver.getRowMap(row.getRowNum()).toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getRowMapBlankColumns(int colNumber) {
		try {
			for (Row row : currentSheet) { // For each Row.
				Cell cell = row.getCell(colNumber); // Get the Cell at the Index / Column you want.
				if (cell.toString().equalsIgnoreCase("")) {

					System.out.println(row.getRowNum());
					System.out.println(excelDriver.getRowMap(row.getRowNum()).toString());
					AllureReport.addStep("inside get row map blank columns");
					AllureReport.addStep(excelDriver.getRowMap(row.getRowNum()).toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Step("This step verifies the null values in a given column of excel")
	public static void getExcelData() {
		try {

			for (String str : filePath) {
				excelDriver = new ExcelDriver(str);
				System.out.println(str);
				System.out.println(excelDriver.getHeaderMap().toString());
				colNo = excelDriver.getColumnHeaderNo("Question Text");
				currentSheet = excelDriver.getCurrentSheet();
				AllureReport.addStep("Null value check");
				AllureReport.addStep(Integer.toString(colNo));
				AllureReport.addStep(str);
				getRowMapColumns(colNo);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Step("This step verifies the blank values in a given column of excel")
	public void blankCheck() {
		// System.out.println("Success");

		for (String str : filePath) {
			excelDriver = new ExcelDriver(str);
			System.out.println(str);
			System.out.println(excelDriver.getHeaderMap().toString());
			colNo = excelDriver.getColumnHeaderNo("Question Text");
			currentSheet = excelDriver.getCurrentSheet();
			AllureReport.addStep("Blank value check");
			AllureReport.addStep(str);
			AllureReport.addStep(Integer.toString(colNo));
			getRowMapBlankColumns(colNo);
		}
	}

	@Step("This step asserts the browser title")	
	public void browserCheck() throws Exception {
		try {
			BrowserDriver.funcGetWebdriver();
			AllureListener.setDriver(BrowserDriver.driver);
			BrowserDriver.launchWebURL("https://www.google.com");
			Thread.sleep(6000);
			String title = BrowserDriver.driver.getTitle();
			Allure.step("browser value check");
			Assert.assertEquals(title, "hello");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
		
}
