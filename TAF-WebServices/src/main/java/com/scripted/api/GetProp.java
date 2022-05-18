package com.scripted.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.scripted.generic.Exceptions;

import junit.framework.Assert;

public class GetProp {
	String result = "";
	InputStream inputStream;
	private static String cdir = System.getProperty("user.dir");
    public static Logger LOGGER = Logger.getLogger(GetProp.class);


	public HashMap<String, Object> getPropValues(String filename) throws IOException {
		HashMap<String, Object> propMap = new HashMap<String, Object>();
		try {
			Properties prop = new Properties();
			String propFileName = getFilePath(filename);
			inputStream = new FileInputStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				LOGGER.error("property file '" + propFileName + "' not found in the classpath");
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			propMap.putAll(prop.entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			LOGGER.error("Error while trying to read property file , Exception: " + e);
			Assert.fail("Error while trying to read property file , Exception: " + e);
		} finally {
			inputStream.close();
		}
		return propMap;
	}

	public static String getFilePath(String fileName) throws Exceptions {
		String filePath = cdir + File.separator + fileName;
		System.out.println(cdir);
		try {
			File file = new File(filePath);
		} catch (Exception e) {
			LOGGER.error(new RuntimeException(e.getMessage()));
			Assert.fail("Error while trying to get path for the file"+fileName+" Exception :"+e);
			throw new Exceptions(new RuntimeException(e.getMessage()));
		}
		return filePath;
	}
}