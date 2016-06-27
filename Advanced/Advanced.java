import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.Assert;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.seleniumhq.jetty9.http.HttpStatus;

public class Advanced {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ClientProtocolException, IOException, JSONException {
		
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");

		Actions actions=new Actions(driver);
		WebElement mainmenu=driver.findElement(By.className("menutitle"));
		actions.moveToElement(mainmenu);
		WebElement submenu=driver.findElement(By.xpath("html/body/div/div[2]/div[2]/span[5]"));
		actions.moveToElement(submenu);
		actions.click().build().perform();


		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
		String dbUrl = "jdbc:mysql://10.0.1.86/tatoc";					
		//Database Username		
		String username = "tatocuser";
		//Database Password		
		String password = "tatoc01";
		//Load mysql jdbc driver		
		Class.forName("com.mysql.jdbc.Driver");
		//Create Connection to DB
		Connection con =  (Connection) DriverManager.getConnection(dbUrl, username, password);
		String symbol=driver.findElement(By.id("symboldisplay")).getText();
		System.out.println(symbol);
		//Create Statement Object		
		PreparedStatement pstmt= con.prepareStatement("select id from identity where symbol=?;");				
		pstmt.setString(1,symbol);
		// Execute the SQL Query. Store results in ResultSet		
		ResultSet rs= pstmt.executeQuery();
		int id=0;
		// While Loop to iterate through data for name		
		while(rs.next()){
			id = rs.getInt("id");
			System.out.println("id: "+ id);
		}
		pstmt= con.prepareStatement("select name, passkey from credentials where id=?;");
		pstmt.setInt(1, id);

		rs= pstmt.executeQuery();
		String name="";
		String passkey="";
		while(rs.next()) {
			name=rs.getString("name");
			System.out.println(name);
			passkey=rs.getString("passkey");
			System.out.println(passkey);
		}
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("passkey")).sendKeys(passkey);
		driver.findElement(By.id("submit")).click();
		con.close();





		//WebElement video = driver.findElement(By.className("video"));

		JavascriptExecutor js = (JavascriptExecutor)driver;
		System.out.println("jajaja");
		Thread.sleep(1000);
		js.executeScript("document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].playMovie();");

		double i = (double) js.executeScript("return document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].getTotalTime();");
		int j = (int) i;
		System.out.println(+i);

		System.out.println(+j);
		j=j*1000+1000;
		Thread.sleep(j);
		driver.findElement(By.linkText("Proceed")).click();


		/// HTTP rest 

		String ss= driver.findElement(By.id("session_id")).getText();

		String sessId = ss.substring(12);
		System.out.println(sessId);
		String uri = "http://10.0.1.86/tatoc/advanced/rest/service/token/"+sessId ;
		System.out.println(uri);
		//  driver.get(uri);
		URL url = new URL(uri);
		//	      HttpClient client = new DefaultHttpClient();
		//	       HttpGet get = new HttpGet(uri.toString());
		//
		//	       HttpResponse r = (HttpResponse) client.execute(get);
		//	    //  driver.get(uri);
		//	       HttpEntity e = ((org.apache.http.HttpResponse) r).getEntity();
		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		System.out.println(str);
		org.json.JSONObject jas = new org.json.JSONObject(str);
		String tkvalue= jas.getString("token");
		System.out.println("token is" +tkvalue);
		long exp = jas.getLong("expires");
		System.out.println("expires value" +exp); 
		URL url1 = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();


		conn1.setRequestMethod("POST");

		conn1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "id="+sessId+"&signature="+tkvalue+"&allow_access=1";

		conn1.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn1.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		conn1.disconnect();
		driver.findElement(By.cssSelector(".page a")).click();

		//driver.findElement((By.linkText("Download File"))).click();
		Thread.sleep(5000);
		BufferedReader br;
		String rf ="" ;
		br= new BufferedReader(new FileReader("/home/abdullatasleem/Downloads/file_handle_test.dat"));
		while(br.readLine()!=null){
			rf+=rf;
		}
		System.out.println(rf);
		br.close();
	}
}
