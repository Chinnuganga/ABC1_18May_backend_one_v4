package com.scripted.others;

import com.scripted.PDF.CucumberPDF;

public class TC001PDFCucumberReport {
	public static void main(String[] args) {
		try {
			CucumberPDF.ExportJsondataPdf("target/cucumber.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
}

}


