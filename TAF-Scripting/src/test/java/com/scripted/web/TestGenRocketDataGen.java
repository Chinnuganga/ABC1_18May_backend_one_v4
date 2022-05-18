package com.scripted.web;

import com.scripted.dataload.GenRocketDriver;

public class TestGenRocketDataGen {

	public static void main(String[] args) {
		GenRocketDriver.executeGenSceFile("TestScenario.grs");
		GenRocketDriver.executeGenSceFile("TestScenario.grs", "Test","ExcelFileReceiver");
		GenRocketDriver.updateSceLoopCount("TestScenario.grs","Test","ExcelFileReceiver","15");		
		GenRocketDriver.rmAndUpdateSceReceiver("TestScenario.grs","Test","ExcelFileReceiver","XMLFileReceiver");
		GenRocketDriver.rmAndUpdateSceReceiver("TestScenario.grs","Test","ExcelFileReceiver","MySQLInsertReceiver");
	}
}
