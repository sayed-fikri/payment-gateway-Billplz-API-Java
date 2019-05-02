package com.billplz.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class BillplzConnect {

    public static final String PRODUCTIONURL = "https://billplz-staging.herokuapp.com/api/";
    public static final String STAGINGURL = "https://www.billplz.com/api/";

    public static final String CREATEBILL = "v3/bills";
    public static final String GETFPXBANKS = "v3/fpx_banks";

    public HttpClient httpclient;
    public Base64.Encoder encoder;
    public String encoding;
    public HttpPost httppost;
    public HttpGet httpget;
    public HttpResponse httpresponse;
    public String httpurl;

    public BillplzConnect(String APIKey) {
        httpclient = HttpClientBuilder.create().build();
        encoder = Base64.getEncoder();
        encoding = encoder.encodeToString((APIKey + ":").getBytes());
    }

    public BillplzConnect() {
        httpclient = HttpClientBuilder.create().build();
        encoder = Base64.getEncoder();
    }

    public BillplzConnect setAPIKey(String APIKey) {
        encoding = encoder.encodeToString((APIKey + ":").getBytes());
        return this;
    }

    public BillplzConnect setMode(String mode) {
        if (mode.equalsIgnoreCase("staging")) {
            httpurl = PRODUCTIONURL;
        } else {
            httpurl = STAGINGURL;
        }
        return this;
    }

    /**
     * createbill, getbill, etc
     *
     * @param action
     * @return
     */
    public BillplzConnect setAction(String action) {
        if (action.equalsIgnoreCase("createbill")) {
            httppost = new HttpPost(httpurl + CREATEBILL);
        } else if (action.equalsIgnoreCase("getfpxbanks")) {
            httpget = new HttpGet(httpurl + GETFPXBANKS);
        }
        /*
        Add more case here
         */
        setHeader();
        return this;
    }

    public BillplzConnect setHeader() {
        Object httpaction = getHttpAction();
        if (httpaction instanceof HttpPost) {
            httppost.setHeader("Authorization", "Basic " + encoding);
        } else if (httpaction instanceof HttpGet) {
            httpget.setHeader("Authorization", "Basic " + encoding);
        }
        return this;
    }

    public BillplzConnect setPostData(List<NameValuePair> urlParameters) {
        try {
            httppost.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void send(Object httpaction) throws IOException {
        System.out.print("Executing Request: ");
        if (httpaction instanceof HttpPost) {
            System.out.println(httppost.getRequestLine());
            httpresponse = httpclient.execute(httppost);
        } else if (httpaction instanceof HttpGet) {
            System.out.println(httpget.getRequestLine());
            httpresponse = httpclient.execute(httpget);
        }

    }

    public StatusLine getHttpStatus() {
        return httpresponse.getStatusLine();
    }

    public String getHttpOutput() throws IOException {

        HttpEntity entity = httpresponse.getEntity();

        BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

        String line = null;
        String output = "";
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        return output;
    }

    public Object getHttpAction() {
        if (httppost instanceof HttpPost) {
            return httppost;
        } else if (httpget instanceof HttpGet) {
            return httpget;
        }
        return httppost;
    }

}
