package Selenium.Test;

import com.billplz.api.BillplzConnect;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Case4 {

    public static void main(String[] args) throws IOException, ParseException {

        BillplzConnect bc = new BillplzConnect("fc2f8712-5c4f-438e-9f0f-f3092865e2eb");
        JSONParser jsonParser = new JSONParser();

        /**
         * Create A Bill
         */
        bc.httppost = null;

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("collection_id", "5p8dl8ka"));
        urlParameters.add(new BasicNameValuePair("description", "Speerichment"));
        urlParameters.add(new BasicNameValuePair("email", "fik.arif@gmail.com"));
        urlParameters.add(new BasicNameValuePair("name", "Fikri"));
        urlParameters.add(new BasicNameValuePair("amount", "15000"));
        urlParameters.add(new BasicNameValuePair("mobile_number", "123456"));
        urlParameters.add(new BasicNameValuePair("callback_url", "http://google.com"));
        urlParameters.add(new BasicNameValuePair("reference_1_label", "Speericment"));
        urlParameters.add(new BasicNameValuePair("reference_1", "TES0022"));
        urlParameters.add(new BasicNameValuePair("reference_2_label", "Child Name"));
        urlParameters.add(new BasicNameValuePair("reference_2", "Fikri Junior"));

        bc.setMode("staging").setAction("createbill").setPostData(urlParameters).send(bc.getHttpAction());

        String jsondata = bc.getHttpOutput();
        System.out.println(bc.getHttpStatus());
        System.out.println(jsondata);

        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsondata);
        String bill_id = (String) jsonObject.get("id");
        String bill_url = (String) jsonObject.get("url");
        System.out.println("Bill ID: " + bill_id);
        System.out.println("Bill URL: " + bill_url);

        /**
         * Selenium Web Driver
         */
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = bill_url + "?auto_submit=true";
        driver.get(baseUrl);

    }
}
