package com.scripted.others;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class testing {

	public static void main(String[] args) throws Exception {
		/*String postEndpoint = "https://dev.azure.com/SkriptMate/TestProject/_apis/wit/attachments?fileName=imageAsFileAttachment.png&api-version=5.1";

		File testUploadFile = new File("D:\\imageAsFailAttachment.png"); //Specify your own location and file 

		CloseableHttpClient httpclient = HttpClients.createDefault();

		// build httpentity object and assign the file that need to be uploaded 
		HttpEntity postData = MultipartEntityBuilder.create().addBinaryBody("Image",testUploadFile).build();


		// build http request and assign httpentity object to it that we build above 
		HttpUriRequest postRequest = RequestBuilder.post(postEndpoint).setEntity(postData).build();
		postRequest.setHeader("Authorization", "Basic eWFtdW5hLnJhbmlAdXN0LWdsb2JhbC5jb206bGFwczJiN3Q2d2c0emhsaXZscWl2aTdqMmJ1bGo2Zjdqd2IzcXdybm8zN3E1eDRlbWNzcQ==");
		postRequest.setHeader("Content-Type",ContentType.DEFAULT_BINARY.toString());
		System.out.println("Executing request " + postRequest.getRequestLine());

		HttpResponse response = httpclient.execute(postRequest);

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		//Throw runtime exception if status code isn't 200 
		if (response.getStatusLine().getStatusCode() != 201) {
		    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		//Create the StringBuffer object and store the response into it. 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
		    result.append(line);
		}

		System.out.println("Response : \n" + result);*/
		
		MultipartEntityBuilder s= MultipartEntityBuilder.create();
		File file = new File("D:\\imageAsFailAttachment.png");
		String message = "This is a multipart post";
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		System.out.println(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, "sample.jpeg");
		builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
		HttpEntity entity = builder.build();
		HttpPost httppost = new HttpPost();
		httppost.setHeader("Authorization", "Basic eWFtdW5hLnJhbmlAdXN0LWdsb2JhbC5jb206bGFwczJiN3Q2d2c0emhsaXZscWl2aTdqMmJ1bGo2Zjdqd2IzcXdybm8zN3E1eDRlbWNzcQ==");
		httppost.setEntity(entity);
		}
	}


