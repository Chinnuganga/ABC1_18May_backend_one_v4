package com.scripted.Enablevoice;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFileFormat.Type;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.scripted.Allure.AllureReport;
import com.scripted.Extent.ExtentUtils;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

public class VoiceEnabledReport {
	static AudioPlayer audioPlayer = null;
	public static Logger LOGGER = Logger.getLogger(VoiceEnabledReport.class);

	public static void customizeAllureGenReport() throws Exception {
		try {
			String workingDir = com.scripted.generic.FileUtils.getCurrentDir();
			String folder = AllureReport.getCurrentTimeStamp();
			Path path = Paths
					.get(fileFormatter(workingDir) + "/SkriptmateReport" + "/Allure" + File.separator + folder + "/index.html");
			String content = new String(Files.readAllBytes(path));
			String audioPth = "./Audio/AudioData.wav";
			String tags = content.replace("<body>", "<body>"
					+ "\n<script src='https://kit.fontawesome.com/a076d05399.js'></script>\n <center><button onclick='playAudio()' style='font-size:24px'><i class='fas fa-volume-up' style='font-size:48px;color:red'></i></button>\n <button onclick='pauseAudio()' style='font-size:24px'><i class='fas fa-stop-circle' style='font-size:48px;color:red'></i></button></center><script>\n var x =new Audio('file:"
					+ audioPth + "');\n function playAudio() { "
					+ "\n x.play(); } \n function pauseAudio() { \n  x.pause(); }\n</script>");
			FileWriter myWriter = new FileWriter(
					fileFormatter(workingDir) + "/SkriptmateReport" + "/Allure" + File.separator + folder + "/index.html");
			myWriter.write(tags);
			myWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while customize the allure voice report " + "Exception :" + e);
		}
	}

	public static void customizeExtentGenReport() throws Exception {
		try {
			String workingDir = com.scripted.generic.FileUtils.getCurrentDir();
			String folder = ExtentUtils.getTimeStamp();
			Path path = Paths.get(fileFormatter(workingDir) + "/SkriptmateReport" + "/Extent" + File.separator + folder + File.separator
					+ folder + "_Report.html");
			String content = new String(Files.readAllBytes(path));
			String audioPth = "./Audio/AudioData.wav";
			String tags = content.replace("<body class='extent standard default hide-overflow bdd-report'>",
					"<body class='extent standard default hide-overflow bdd-report'>"
							+ "\n<script src='https://kit.fontawesome.com/a076d05399.js'></script>\n <center><button onclick='playAudio()' style='font-size:24px'><i class='fas fa-volume-up' style='font-size:48px;color:red'></i></button>\n <button onclick='pauseAudio()' style='font-size:24px'><i class='fas fa-stop-circle' style='font-size:48px;color:red'></i></button></center><script>\n var x =new Audio('file:"
							+ audioPth + "');\n function playAudio() { "
							+ "\n x.play(); } \n function pauseAudio() { \n  x.pause(); }\n</script>");
			FileWriter myWriter = new FileWriter(fileFormatter(workingDir) + "/SkriptmateReport" + "/Extent" + File.separator
					+ folder + File.separator + folder + "_Report.html");
			myWriter.write(tags);
			myWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while customize the extent voice report " + "Exception :" + e);
		}
	}

	public static void createAllureAudio(String cucumberPath) throws Exception {
		try {
			String workingDir = com.scripted.generic.FileUtils.getCurrentDir();
			String folder = AllureReport.getCurrentTimeStamp();
			new File(fileFormatter(workingDir) + "/SkriptmateReport" + "/Allure" + File.separator + folder + "/Audio").mkdirs();
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

			AudioPlayer audioPlayer = null;
			String voiceName = "kevin16";
			VoiceManager voiceManager = VoiceManager.getInstance();
			Voice helloVoice = voiceManager.getVoice(voiceName);

			if (helloVoice == null) {
				System.err.println("Cannot find a voice named " + voiceName + ".  Please specify a different voice.");
				System.exit(1);
			}
			helloVoice.allocate();

			// create a audioplayer to dump the output file
			audioPlayer = new SingleFileAudioPlayer(
					filePathFormatter(workingDir) + "/SkriptmateReport" + "/Allure" + File.separator + folder + "/Audio/AudioData",
					Type.WAVE);

			// attach the audioplayer
			helloVoice.setAudioPlayer(audioPlayer);

			int passcount = 0;
			int failcount = 0;
			CucumberJsonExtractor cucm = new CucumberJsonExtractor();
			JSONObject jObj = cucm.getScenarioAndStepsStatus(cucumberPath);
			JSONObject featureObject = null;
			for (String feature : jObj.keySet()) {
				featureObject = jObj.getJSONObject(feature);
				for (String testCase : featureObject.keySet()) {
					String name = featureObject.getJSONObject(testCase).getString("scenarioName").toString();
					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
						helloVoice.speak("The Scenario " + name + "is Passed");
						passcount++;
					} else {
						helloVoice.speak("The Scenario " + name + "is Failed");
						failcount++;
					}
				}
			}

			helloVoice.speak("The total count of passed test case is " + passcount);
			helloVoice.speak("The total count of failed test case is " + failcount);

			helloVoice.deallocate();
			audioPlayer.close();

			customizeAllureGenReport();
			System.out.println("Successfully added voice enabled report");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating the audio for report " + "Exception :" + e);
		}
	}

	public static void createExtentAudio(String cucumberPath) throws Exception {
		try {
			String workingDir = com.scripted.generic.FileUtils.getCurrentDir();
			String folder = ExtentUtils.getTimeStamp();
			new File(fileFormatter(workingDir) + "/SkriptmateReport" + "/Extent" + File.separator + folder + "/Audio").mkdirs();
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

			AudioPlayer audioPlayer = null;
			String voiceName = "kevin16";
			VoiceManager voiceManager = VoiceManager.getInstance();
			Voice helloVoice = voiceManager.getVoice(voiceName);

			if (helloVoice == null) {
				System.err.println("Cannot find a voice named " + voiceName + ".  Please specify a different voice.");
				System.exit(1);
			}
			helloVoice.allocate();

			// create a audioplayer to dump the output file
			audioPlayer = new SingleFileAudioPlayer(
					filePathFormatter(workingDir) + "/SkriptmateReport" + "/Extent" + File.separator + folder + "/Audio/AudioData",
					Type.WAVE);

			// attach the audioplayer
			helloVoice.setAudioPlayer(audioPlayer);

			int passcount = 0;
			int failcount = 0;
			CucumberJsonExtractor cucm = new CucumberJsonExtractor();
			JSONObject jObj = cucm.getScenarioAndStepsStatus(cucumberPath);
			JSONObject featureObject = null;
			for (String feature : jObj.keySet()) {
				featureObject = jObj.getJSONObject(feature);
				for (String testCase : featureObject.keySet()) {
					String name = featureObject.getJSONObject(testCase).getString("scenarioName").toString();
					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
						helloVoice.speak("The Scenario " + name + "is Passed");
						passcount++;
					} else {
						helloVoice.speak("The Scenario " + name + "is Failed");
						failcount++;
					}
				}
			}

			helloVoice.speak("The total count of passed test case is " + passcount);
			helloVoice.speak("The total count of failed test case is " + failcount);

			helloVoice.deallocate();
			audioPlayer.close();

			customizeExtentGenReport();
			System.out.println("Successfully added voice enabled report");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating the audio for report " + "Exception :" + e);
		}
	}

	private static String fileFormatter(String path) {
		return path.replace("\\", File.separator);
	}

	private static String filePathFormatter(String path) {
		return path.replace("\\", File.separator);
	}

}
