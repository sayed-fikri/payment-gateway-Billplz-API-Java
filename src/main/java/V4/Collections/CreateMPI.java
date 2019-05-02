package V4.Collections;

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

public class CreateMPI {

    public static void main(String[] args) {

        HttpClient httpclient = HttpClientBuilder.create().build();

        Encoder encoder = Base64.getEncoder();
        String encoding = encoder.encodeToString(("4e49de80-1670-4606-84f8-2f1d33a38670:").getBytes());

        HttpPost httppost = new HttpPost("https://billplz-staging.herokuapp.com/api/v4/mass_payment_instructions");
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
        urlParameters.add(new BasicNameValuePair("mass_payment_instruction_collection_id", "hhtab90a"));
        urlParameters.add(new BasicNameValuePair("bank_code", "MBBEMYKL"));
        urlParameters.add(new BasicNameValuePair("bank_account_number", "123422"));
        urlParameters.add(new BasicNameValuePair("identity_number", "TEST0010"));
        urlParameters.add(new BasicNameValuePair("name", "Testing123"));
        urlParameters.add(new BasicNameValuePair("description", "Testing123"));
        urlParameters.add(new BasicNameValuePair("total", "2000"));
        return urlParameters;
    }
}
