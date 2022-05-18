package com.scripted.ado;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.scripted.dataload.PropertyDriver;

public class ADOAPIClient {
	static String auth = "";

	public static void StatusUpdate(String cucumberJsonPath) {

		try {
			CucumberJsonExtractor cucm = new CucumberJsonExtractor();
			JSONObject jObj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);
			JSONObject featureObject = null;
			JSONArray testPointIds = new JSONArray();
			PropertyDriver propDriver = new PropertyDriver();
			String resultid = "";
			String errMsg = "";
			String defId = "";
			String imgurl = "";
			propDriver.setPropFilePath("src/main/resources/Integrations/AzureDevOps/Properties/ADO.properties");
			if (PropertyDriver.readProp().containsKey("https.proxyHost")
					&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
				String host = PropertyDriver.readProp("https.proxyHost");
				String port = PropertyDriver.readProp("https.proxyPort");
				if (!host.isEmpty() && !port.isEmpty()) {
					System.setProperty("https.proxyHost", host);
					System.setProperty("https.proxyPort", port);
				}
			}

			trustAllHosts();

			String BaseUri = PropertyDriver.readProp("ADO.url");
			auth = PropertyDriver.readProp("ADO.authorization");
			String reponame = PropertyDriver.readProp("ADO.reponame");
			String Proid = PropertyDriver.readProp("ADO.projectid");
			String planid = PropertyDriver.readProp("ADO.planid");
			String suiteid = PropertyDriver.readProp("ADO.suiteid");
			String projectKey = PropertyDriver.readProp("ADO.projectkey");
			String outcome = "";

			for (String feature : jObj.keySet()) {
				featureObject = jObj.getJSONObject(feature);
				for (String testCase : featureObject.keySet()) {
					String testcaseid = testCase.replace("@" + projectKey + "-", "").trim();
					GetTestPoint tc = new GetTestPoint();
					String tcId = tc.getTstPoint(testcaseid);

					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
						outcome = "Passed";
					} else {
						outcome = "Failed";
					}

					// Create Test Run

					URL url = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/test/runs?api-version=5.1");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					conn.addRequestProperty("Authorization", "Basic " + auth);
					conn.addRequestProperty("Content-Type", "application/json");
					conn.setRequestMethod("POST");

					JSONObject dataobj = new JSONObject();
					String name = featureObject.getJSONObject(testCase).getString("scenarioName").toString();
					dataobj.put("name", "Automation: " + name);
					JSONObject data = new JSONObject();
					data.put("id", planid);
					dataobj.put("plan", data);
					testPointIds.put(tcId);
					dataobj.put("pointIds", testPointIds);
					testPointIds = new JSONArray();

					byte[] block = JSONValue.toJSONString(dataobj).getBytes("UTF-8");
					conn.setDoOutput(true);
					OutputStream ostream = conn.getOutputStream();
					ostream.write(block);
					ostream.flush();
					InputStream istream;
					int status = conn.getResponseCode();
					istream = conn.getInputStream();
					String text = "";
					if (istream != null) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));
						String line1;
						while ((line1 = reader.readLine()) != null) {
							text += line1;
							text += System.getProperty("line1.separator");
						}
						reader.close();
					}
					JSONObject jobj = new JSONObject(text);
					String runid = jobj.get("id").toString();
					System.out.println("Test Run Created Successfully");

					// Get TestResultID

					URL rurl = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/test/runs/" + runid
							+ "/results?api-version=5.1");
					HttpURLConnection con = (HttpURLConnection) rurl.openConnection();

					con.addRequestProperty("Authorization", "Basic " + auth);
					con.addRequestProperty("Content-Type", "application/json");
					con.setRequestMethod("GET");

					InputStream istm;
					int stat = con.getResponseCode();
					istm = con.getInputStream();
					String val = "";
					if (istm != null) {
						BufferedReader read = new BufferedReader(new InputStreamReader(istm, "UTF-8"));
						String line;
						while ((line = read.readLine()) != null) {
							val += line;
							val += System.getProperty("line.separator");
						}
						read.close();
					}

					JSONObject gobj = new JSONObject(val);
					JSONArray value = gobj.getJSONArray("value");
					for (Object object : value) {
						JSONObject obj = (JSONObject) object;

						resultid = obj.get("id").toString();
						System.out.println("Got Test Result ID");
					}

					// Update Status

					URL surl = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/test/runs/" + runid
							+ "/results?api-version=5.1");
					HttpURLConnection cont = (HttpURLConnection) surl.openConnection();

					cont.addRequestProperty("Authorization", "Basic " + auth);
					cont.addRequestProperty("Content-Type", "application/json");
					cont.setRequestProperty("X-HTTP-Method-Override", "PATCH");
					cont.setRequestMethod("POST");

					JSONArray dataArr = new JSONArray();
					JSONObject dataob = new JSONObject();
					dataob.put("id", resultid);
					dataob.put("outcome", outcome);
					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Failed")) {
						errMsg = featureObject.getJSONObject(testCase).getString("scenarioError");
						dataob.put("errorMessage", errMsg);
						dataob.put("state", "Completed");
					} else {

						dataob.put("state", "Completed");
					}
					dataArr.put(dataob);

					byte[] blocks = JSONValue.toJSONString(dataArr).getBytes("UTF-8");
					cont.setDoOutput(true);
					OutputStream os = cont.getOutputStream();
					os.write(blocks);
					os.flush();
					InputStream inps;
					int stats = cont.getResponseCode();
					inps = cont.getInputStream();
					String tex = "";
					if (inps != null) {
						BufferedReader read = new BufferedReader(new InputStreamReader(inps, "UTF-8"));
						String line;
						while ((line = read.readLine()) != null) {
							tex += line;
							tex += System.getProperty("line.separator");
						}
						read.close();
					}

					System.out.println("Execution Status Updated Successfully");

					// Add Failure Comments at Test Run

					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Failed")) {

						URL curl = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/test/runs/" + runid
								+ "?api-version=5.1");
						HttpURLConnection cnct = (HttpURLConnection) curl.openConnection();

						cnct.addRequestProperty("Authorization", "Basic " + auth);
						cnct.addRequestProperty("Content-Type", "application/json");
						cnct.setRequestProperty("X-HTTP-Method-Override", "PATCH");
						cnct.setRequestMethod("POST");
						JSONObject cobj = new JSONObject();
						cobj.put("id", resultid);
						cobj.put("errorMessage", errMsg);

						byte[] bks = JSONValue.toJSONString(cobj).getBytes("UTF-8");
						cnct.setDoOutput(true);
						OutputStream otsm = cnct.getOutputStream();
						otsm.write(bks);
						otsm.flush();
						InputStream inpm;
						int scode = cont.getResponseCode();
						inpm = cnct.getInputStream();
						String texts = "";
						if (inpm != null) {
							BufferedReader read = new BufferedReader(new InputStreamReader(inpm, "UTF-8"));
							String line;
							while ((line = read.readLine()) != null) {
								texts += line;
								texts += System.getProperty("line.separator");
							}
							read.close();
						}
						System.out.println("Failure Comments Updated Successfully");
					}

					// Add Failure Screenshot in Test Run

					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Failed")) {

						if (featureObject.getJSONObject(testCase).has("embededdata")) {

							URL furl = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/test/runs/" + runid
									+ "/attachments?api-version=5.1-preview.1");
							HttpURLConnection cone = (HttpURLConnection) furl.openConnection();

							cone.addRequestProperty("Authorization", "Basic " + auth);
							cone.addRequestProperty("Content-Type", "application/json");
							cone.setRequestMethod("POST");

							JSONObject objc = new JSONObject();
							objc.put("fileName", "imageAsFailAttachment.png");
							objc.put("comment", "Failure Screenshot Upload");
							objc.put("attachmentType", "GeneralAttachment");
							String stream = featureObject.getJSONObject(testCase).getString("embededdata").toString();
							objc.put("stream", stream);

							byte[] bk = JSONValue.toJSONString(objc).getBytes("UTF-8");
							cone.setDoOutput(true);
							OutputStream ot = cone.getOutputStream();
							ot.write(bk);
							ot.flush();
							InputStream inp;
							int sta = cone.getResponseCode();
							inp = cone.getInputStream();
							String out = "";
							if (inp != null) {
								BufferedReader read = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
								String line;
								while ((line = read.readLine()) != null) {
									out += line;
									out += System.getProperty("line.separator");
								}
								read.close();
							}

							System.out.println("Failure Screenshot Added");

						}
					}
					// Create bug for failure test case

					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Failed")) {

						URL burl = new URL(BaseUri + "/" + reponame + "/" + Proid
								+ "/_apis/wit/workitems/$Bug?api-version=3.0-preview");
						HttpURLConnection bcon = (HttpURLConnection) burl.openConnection();

						bcon.addRequestProperty("Authorization", "Basic " + auth);
						bcon.addRequestProperty("Content-Type", "application/json-patch+json");
						bcon.setRequestProperty("X-HTTP-Method-Override", "PATCH");
						bcon.setRequestMethod("POST");
						String tname = featureObject.getJSONObject(testCase).getString("scenarioName").toString();

						JSONArray jarr = new JSONArray();

						JSONObject jobjt = new JSONObject();
						jobjt.put("op", "add");
						jobjt.put("path", "/fields/System.Title");
						jobjt.put("value", tname);

						JSONObject jobjs = new JSONObject();
						jobjs.put("op", "add");
						jobjs.put("path", "/fields/Microsoft.VSTS.TCM.ReproSteps");
						jobjs.put("value", errMsg);

						jarr.put(jobjt);

						jarr.put(jobjs);

						byte[] btk = JSONValue.toJSONString(jarr).getBytes("UTF-8");
						bcon.setDoOutput(true);
						OutputStream om = bcon.getOutputStream();
						om.write(btk);
						om.flush();
						InputStream ism;
						int sta = bcon.getResponseCode();
						ism = bcon.getInputStream();
						String bug = "";
						if (ism != null) {
							BufferedReader read = new BufferedReader(new InputStreamReader(ism, "UTF-8"));
							String line;
							while ((line = read.readLine()) != null) {
								bug += line;
								bug += System.getProperty("line.separator");
							}
							read.close();
						}

						JSONObject bjobj = new JSONObject(bug);
						defId = bjobj.get("id").toString();
						System.out.println("Defect Created Successfully for " + tname);

					}

					// Add Failure Screenshot In Defect

					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Failed")) {

						if (featureObject.getJSONObject(testCase).has("embededdata")) {
							String byteStream = featureObject.getJSONObject(testCase).getString("embededdata")
									.toString();

							byte[] imageByteArray = Base64.decodeBase64(byteStream);

							URL attachUrl = new URL(
									"https://dev.azure.com/SkriptMate/TestProject/_apis/wit/attachments?fileName=imageAsFileAttachment.png&api-version=5.1");
							HttpURLConnection cox = (HttpURLConnection) attachUrl.openConnection();
							cox.addRequestProperty("Authorization", "Basic " + auth);
							cox.addRequestProperty("Content-Type", "application/octet-stream");
							cox.setDoOutput(true);
							DataOutputStream wr = new DataOutputStream(cox.getOutputStream());
							wr.write(imageByteArray);
							wr.flush();
							wr.close();

							int responseCode = cox.getResponseCode();

							BufferedReader in = new BufferedReader(new InputStreamReader(cox.getInputStream()));
							String output;
							StringBuffer response = new StringBuffer();

							while ((output = in.readLine()) != null) {
								response.append(output);
							}
							in.close();

							JSONObject bjobj = new JSONObject(response.toString());
							imgurl = bjobj.get("url").toString();

							URL defurl = new URL(BaseUri + "/" + reponame + "/" + Proid + "/_apis/wit/workitems/"
									+ defId + "?api-version=5.0");
							HttpURLConnection cnc = (HttpURLConnection) defurl.openConnection();

							cnc.addRequestProperty("Authorization", "Basic " + auth);
							cnc.addRequestProperty("Content-Tyspe", "application/json-patch+json");
							cnc.setRequestProperty("X-HTTP-Method-Override", "PATCH");
							cnc.setRequestMethod("POST");

							JSONArray arr = new JSONArray();

							JSONObject jsobj = new JSONObject();
							jsobj.put("op", "add");
							jsobj.put("path", "/relations/-");

							JSONObject jsobj1 = new JSONObject();
							jsobj1.put("rel", "AttachedFile");
							jsobj1.put("url", imgurl);

							JSONObject jsobj2 = new JSONObject();
							jsobj2.put("comment", "FailureScreenshot");

							jsobj.put("value", jsobj1);
							jsobj.put("attributes", jsobj2);
							arr.put(jsobj);

							byte[] bts = JSONValue.toJSONString(arr).getBytes("UTF-8");
							cnc.setDoOutput(true);
							OutputStream oms = cnc.getOutputStream();
							oms.write(bts);
							oms.flush();
							InputStream inpsm;
							int res = cnc.getResponseCode();
							inpsm = cnc.getInputStream();
							String resout = "";
							if (inpsm != null) {
								BufferedReader read = new BufferedReader(new InputStreamReader(inpsm, "UTF-8"));
								String line;
								while ((line = read.readLine()) != null) {
									resout += line;
									resout += System.getProperty("line.separator");
								}
								read.close();
							}

							System.out.println("Failure Screenshot Added in Defect " + defId);

						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void trustAllHosts() {
		// TODO Auto-generated method stub
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
