import java.util.Iterator;
//import java.util.List;
import java.util.Set;
//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.webdriven.commands.Close;


public class Tatoc {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver();
		driver.get("http://10.0.1.86/tatoc/basic/grid/gate");
	//first  page	
	  driver.findElement(By.className("greenbox")).click();
 //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  
	//second page
	
	  while(true)
      {
          driver.switchTo().defaultContent();
          driver.switchTo().frame(driver.findElement(By.id("main")));
          String s1 = driver.findElement(By.id("answer")).getAttribute("class");
          driver.switchTo().frame(driver.findElement(By.id("child")));
          String s2 = driver.findElement(By.id("answer")).getAttribute("class");
          driver.switchTo().defaultContent();
          driver.switchTo().frame(driver.findElement(By.id("main")));
          if(s1.equals(s2))
          {
              driver.findElement(By.linkText("Proceed")).click();
              break;
          }
          else
          {
              driver.findElement(By.linkText("Repaint Box 2")).click();
          }
          
      }
     
		  //third page
	      WebElement element = driver.findElement(By.id("dragbox"));
	      WebElement target = driver.findElement(By.id("dropbox"));
	      (new Actions(driver)).dragAndDrop(element, target).perform();
	     // Thread.sleep(2000);
	      driver.findElement(By.linkText("Proceed")).click();
	     
      
     //fourth page 
   /*  driver.findElement(By.linkText("Launch Popup Window")).click();
     
      Set<String> windowId = driver.getWindowHandles();    
      Iterator<String> iterator = windowId.iterator();   
      String mainWinID = iterator.next();
      System.out.println(mainWinID);
      String  newAdwinID = iterator.next();

      
      driver.switchTo().window(newAdwinID);
      System.out.println(driver.getTitle());
      driver.findElement(By.id("name")).sendKeys("abdulla");
      driver.findElement(By.id("submit")).click();
     // Thread.sleep(3000);
      driver.close();
      driver.switchTo().window(mainWinID);
   //   System.out.println(driver.getTitle());
      Thread.sleep(2000);
      driver.findElement(By.linkText("Proceed")).click();
      
      driver.close(); */
	      
      //page five
	      driver.get("http://10.0.1.86/tatoc/basic/cookie");
	      driver.findElement(By.linkText("Generate Token")).click();
	      Thread.sleep(500);
	      WebElement el=   driver.findElement(By.id("token"));
	      String tkvalue = el.getText();
	      System.out.println(tkvalue);
	      String token = tkvalue.substring(7,tkvalue.length());
	      System.out.println(token);
	      Cookie cookie = new Cookie("Token",token);
	      driver.manage().addCookie(cookie);
	      driver.findElement(By.linkText("Proceed")).click();
	      driver.close();
     }
}