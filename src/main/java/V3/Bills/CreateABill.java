package V3.Bills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class CreateABill {

    public static void main(String[] args) {

        HttpClient httpclient = HttpClientBuilder.create().build();

        Encoder encoder = Base64.getEncoder();
        String encoding = encoder.encodeToString(("4e49de80-1670-4606-84f8-2f1d33a38670:").getBytes());

        HttpPost httppost = new HttpPost("https://billplz-staging.herokuapp.com/api/v3/bills");
        httppost.setHeader("Authorization", "Basic " + encoding);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(getData()));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

            String line = null;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }

    }

    public static List<NameValuePair> getData() {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("collection_id", "ohjqe1rp"));
        urlParameters.add(new BasicNameValuePair("description", "Test"));
        urlParameters.add(new BasicNameValuePair("email", "test@gmail.com"));
        urlParameters.add(new BasicNameValuePair("name", "Wan Zulkarnain"));
        urlParameters.add(new BasicNameValuePair("amount", "500"));
        urlParameters.add(new BasicNameValuePair("callback_url", "http://google.com"));
        return urlParameters;
    }

}
