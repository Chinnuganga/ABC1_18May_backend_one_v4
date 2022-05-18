package com.scripted.others;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.io.FileHandler;
import org.apache.commons.io.FileUtils;

import com.scripted.web.BrowserDriver;

public class testcrhome {

	public static void main(String[] args) throws Exception
	{
		test();
	}
	
	public static void test() throws Exception
	{
		WebDriver driver = BrowserDriver.funcGetWebdriver();
		BrowserDriver.launchWebURL("https://ust.com/en/insights");
		BrowserDriver.pageWait();
		WebElement ele = driver.findElement(By.xpath("//*[@id=\"ust-insights-landing-grid\"]/div[3]/div/div/div/div/div[6]/div/div/a/div[2]/div[1]/img"));
		Coordinates coordinate = ((Locatable) ele).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
		
        //File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                
        File source = ele.getScreenshotAs(OutputType.FILE);            
        File destination = new File("d:\\particularElementScreenshot.PNG");
        
        FileHandler.copy(source, destination);
        
		/*ele = driver.findElement(By.xpath("//*[@id=\"ust-insights-landing-grid\"]/div[3]/div/div/div/div/div[6]/div/div/a/div[2]/div[1]/img"));

        BufferedImage fullScreen = ImageIO.read(screenshot);
        Point location = ele.getLocation();
        
        //Find width and height of the located element logo
        int width = ele.getSize().getWidth();
        int height = ele.getSize().getHeight();

	//cropping the full image to get only the logo screenshot
        BufferedImage logoImage = fullScreen.getSubimage(location.getX(), location.getY(),
                width, height);
        
        ImageIO.write(logoImage, "png", screenshot);
        
        //Save cropped Image at destination location physically.
        FileUtils.copyFile(screenshot, new File("C:\\particularElementScreenshot.PNG"));*/

	}
}
