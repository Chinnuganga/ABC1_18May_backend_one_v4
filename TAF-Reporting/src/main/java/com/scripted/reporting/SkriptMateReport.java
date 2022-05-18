package com.scripted.reporting;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class SkriptMateReport {

	public static String screenshotPath = "";
	public static WebDriver driver = null;
	private static String cdir = System.getProperty("user.dir");

	public static void addStep(String stepDescription) {
		Allure.step(stepDescription);
	}

	public static void addScreenshot(String screenShotname) {

		try {

			// Save screenshot in the SkriptMate Report

			driver = TestListener.getDriver();
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
			screenshotPath = cdir + "\\src\\main\\resources\\Screenshots\\" + timeStamp + "\\";
			com.scripted.generic.FileUtils.makeDirs(screenshotPath);
			File DestFile = new File(screenshotPath + SrcFile.getName());
			FileUtils.copyFile(SrcFile, DestFile);
			Path content = Paths.get(DestFile.getPath());
			try (InputStream is = Files.newInputStream(content)) {
				Allure.addAttachment(screenShotname, is);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * public static void addLog(String logName) { try { // Save log in the
	 * SkriptMate Report Allure.addAttachment(logName,
	 * "C:\\Users\\U51582\\Desktop\\sample.text");
	 * 
	 * }catch(Exception e) { System.out.println(e.getMessage()); } }
	 */

	public static void customizeReport() throws Exception {
		try {
			// Customize the SkriptMate Report
			String workingDir = com.scripted.generic.FileUtils.getCurrentDir();
			String reportGenFolder = "SkriptMateReport";
			String currentTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String filePath = workingDir + "\\" + reportGenFolder + "\\" + currentTimeStamp
					+ "\\widgets\\environment.json";
			System.out.println(currentTimeStamp);

			// Execute the command to generate allure report
			String command = "allure generate -c " + "\"" + workingDir + "\\allure-results" + "\"" + " -o " + "\""
					+ workingDir + "\\" + reportGenFolder + "\\" + currentTimeStamp + "\"";
			Runtime.getRuntime().exec(new String[] { "cmd", "/K", "start " + command });
			Thread.sleep(11000);

			String line;
			String pidInfo = "";
			Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				pidInfo += line;
			}
			input.close();
			if (pidInfo.contains("cmd.exe")) {
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
			}

			System.out.println("allure generation triggered");
			Thread.sleep(3000);
			com.scripted.generic.FileUtils
					.deleteFile(workingDir + "\\" + reportGenFolder + "\\" + currentTimeStamp + "\\" + "styles.css");
			com.scripted.generic.FileUtils.fileCopy("src\\main\\resources\\SkriptmateArtefacts" + "\\styles.css",
					workingDir + "\\" + reportGenFolder + "\\" + currentTimeStamp + "\\styles.css");
			// com.scripted.generic.FileUtils.fileCopy("src\\main\\resources\\SkriptmateArtefacts"
			// + "\\Picture1.png" , workingDir + "\\" + reportGenFolder + "\\" +
			// currentTimeStamp + "\\Picture1.png");
			File f1 = new File("src\\main\\resources\\SkriptmateArtefacts" + "\\Logo.png");
			File f2 = new File(workingDir + "\\" + reportGenFolder + "\\" + currentTimeStamp + "\\Logo.png");
			FileUtils.copyFile(f1, f2);
			System.out.println("CSS updated");
			String apackageName = "SkriptMate Package";
			String start = "---";
			String end = "---";
			String totalcount = "---";
			String passedcount = "---";
			String failedcount = "---";
			String bname = "SkriptMate";

			StringBuffer sb = new StringBuffer();
			sb.append("[{");
			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Project Name :" + apackageName + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + apackageName + "\"");
			 */
			/*
			 * sb.append(" :\"" + "apackageName" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Start Date and Time :" + start + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + start + "\"");
			 */
			/*
			 * sb.append(" :\"" + "start" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "End Date and Time :" + end + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + end + "\"");
			 */
			/*
			 * sb.append(" :\"" + "end" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Total Number of Test Cases :" + totalcount + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + totalcount + "\"");
			 */
			/*
			 * sb.append(" :\"" + "totalcount" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Passed :" + passedcount + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + passedcount + "\"");
			 */
			/*
			 * sb.append(" :\"" + "passedcount" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Failed :" + failedcount + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + failedcount + "\"");
			 */
			/*
			 * sb.append(" :\"" + "failedcount" + "\"");
			 */ sb.append(" }");

			sb.append(",{");

			sb.append("\n");
			sb.append("\"name\"");
			sb.append(" :\"" + "Execution browser :" + bname + "\"");
			sb.append("\n");
			/*
			 * sb.append("\"buildName\""); sb.append(" :\"" + bname + "\"");
			 */
			/*
			 * sb.append(" :\"" + "bname" + "\"");
			 */ sb.append("\n");

			sb.append("}]");

			/*
			 * FileWriter writer = new FileWriter( workingDir + "//" + reportGenFolder +
			 * "//" + currentTimeStamp + "//" + "widgets//environment.json");
			 */
			Files.write(new File(filePath).toPath(), sb.toString().getBytes());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
