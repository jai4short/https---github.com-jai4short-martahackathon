package com.lastmilenoproblem.www.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.CookieManager;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

public class sendHttpRequest implements Serializable {
	
	public StringBuffer sendGet(String url) throws IOException{

		CookieStore httpCookieStore = new BasicCookieStore();
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		HttpGet request = new HttpGet(url);

		// add request header
		HttpResponse response = client.execute(request);
		
		printHeaders(response.getAllHeaders());
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		System.out.println();

		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
		
		List<Cookie> cookies = httpCookieStore.getCookies();
		
		System.out.println("Number of cookies is: " + cookies.size() + "\n");
		
		for (Cookie cookie : cookies){
			System.out.println("cookie: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("IsPersistent: " + cookie.isPersistent());
			System.out.println("Expiry Date: " + cookie.getExpiryDate());
			System.out.println("Comment: " + cookie.getComment());
			System.out.println();
		}

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return result;
	}
	
	public StringBuffer sendGet(String url, Map<String, String> headers) throws IOException{

		CookieStore httpCookieStore = new BasicCookieStore();
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		HttpGet request = new HttpGet(url);
		
		Set<String> headerKeys = headers.keySet();
		for(String key : headerKeys){
			request.addHeader(key, headers.get(key));
		}

		HttpResponse response = client.execute(request);
		
		printHeaders(response.getAllHeaders());
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		System.out.println();

		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
		
		List<Cookie> cookies = httpCookieStore.getCookies();
		
		System.out.println("Number of cookies is: " + cookies.size() + "\n");
		
		for (Cookie cookie : cookies){
			System.out.println("cookie: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("IsPersistent: " + cookie.isPersistent());
			System.out.println("Expiry Date: " + cookie.getExpiryDate());
			System.out.println("Comment: " + cookie.getComment());
			System.out.println();
		}

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return result;
	}
	
	public String sendPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException{
		
		CookieStore httpCookieStore = new BasicCookieStore();
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		HttpPost post = new HttpPost(url);
		
		post.setHeader("User-Agent", "NewUserAgent/1.0");
		
		post.setEntity(new UrlEncodedFormEntity(params));
		
		HttpResponse response = client.execute(post);
		
		printHeaders(response.getAllHeaders());
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		System.out.println();
		
		List<Cookie> cookies = httpCookieStore.getCookies();
		
		System.out.println("Number of cookies is: " + cookies.size() + "\n");
		
		for (Cookie cookie : cookies){
			System.out.println("cookie: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("IsPersistent: " + cookie.isPersistent());
			System.out.println("Expiry Date: " + cookie.getExpiryDate());
			System.out.println("Comment: " + cookie.getComment());
			System.out.println();
		}
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null){
			result.append(line);
		}
		
		return result.toString();
		
	}
	
	public String sendPost(String url, String payload, String contentType) throws ClientProtocolException, IOException{
		
		CookieStore httpCookieStore = new BasicCookieStore();
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		HttpPost post = new HttpPost(url);
		
		post.setHeader("User-Agent", "NewUserAgent/1.0");
		StringEntity input = new StringEntity(payload);
		input.setContentType(contentType);
		post.setEntity(input);
		
		HttpResponse response = client.execute(post);
		
		printHeaders(response.getAllHeaders());
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		System.out.println();
		
		List<Cookie> cookies = httpCookieStore.getCookies();
		
		System.out.println("Number of cookies is: " + cookies.size() + "\n");
		
		for (Cookie cookie : cookies){
			System.out.println("cookie: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("IsPersistent: " + cookie.isPersistent());
			System.out.println("Expiry Date: " + cookie.getExpiryDate());
			System.out.println("Comment: " + cookie.getComment());
			System.out.println();
		}
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null){
			result.append(line);
		}
		
		return result.toString();
		
	}
	
	public void sendPost(String url, List<NameValuePair> params1, List<NameValuePair> params2, long wait) throws ClientProtocolException, IOException, ScriptException, InterruptedException{
		//create cookie manager object
		CookieStore httpCookieStore = new BasicCookieStore();
		
		
		
		//create httpclient utils
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).setRedirectStrategy(new LaxRedirectStrategy()).build();
		//HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();

		HttpPost post = new HttpPost(url);
		HttpPost post2 = new HttpPost(url);
		
		//set headers
		post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");
		post2.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");
		
		
		post.setEntity(new UrlEncodedFormEntity(params1));
		
		HttpResponse response = client.execute(post);
		
		printResponseData(response, httpCookieStore);
		
		System.out.println("waiting " + wait/1000 + "seconds before next post");
		
		Thread.sleep(wait);
		
		post2.setEntity(new UrlEncodedFormEntity(params2));
		
		HttpResponse response2 = client.execute(post2);
		
		printResponseData(response2, httpCookieStore);
		
	}
	
	public static void printHeaders(Header[] headers){
		for (Header header : headers){
			System.out.println("Key : " + header.getName() 
            + " ,Value : " + header.getValue());
		}
	}
	
	public static void printResponseData(HttpResponse response, CookieStore httpCookieStore) throws UnsupportedOperationException, IOException{
		printHeaders(response.getAllHeaders());
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		
		
		List<Cookie> cookies = httpCookieStore.getCookies();
		
		System.out.println("Number of cookies is: " + cookies.size() + "\n");

		for (Cookie cookie : cookies){
			System.out.println("cookie: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("IsPersistent: " + cookie.isPersistent());
			System.out.println("Expiry Date: " + cookie.getExpiryDate());
			System.out.println("Comment: " + cookie.getComment());
			System.out.println();
		}
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null){
			result.append(line);
		}
	}

}
