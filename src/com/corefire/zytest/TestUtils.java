package com.corefire.zytest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class TestUtils {

	  public static String sendHttpsPost(String url, String params){
			DataOutputStream out = null;
			BufferedReader in = null;
	        StringBuffer result = new StringBuffer();
	        URL u = null;
	        HttpsURLConnection con = null;
	      //尝试发送请�?
	        try{
	        	System.out.println(params);
	        	SSLContext sc = SSLContext.getInstance("SSL");
	        	sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
		                new java.security.SecureRandom());
	            u = new URL(url);
	            //打开和URL之间的连�?
	            con = (HttpsURLConnection)u.openConnection();
	            //设置通用的请求属�?
	            con.setSSLSocketFactory(sc.getSocketFactory());
		        con.setHostnameVerifier(new TrustAnyHostnameVerifier());
	            //con.setRequestMethod("POST");
	            con.setRequestProperty("Content-Type", "application/json"); //
	            con.setUseCaches(false);
	            //发送POST请求必须设置如下两行
	            con.setDoOutput(true);
	            con.setDoInput(true);

	            con.connect();
		        out = new DataOutputStream(con.getOutputStream());
		        out.write(params.getBytes("utf-8"));
		        // 刷新、关�?
		        out.flush();
		        out.close();
	            //读取返回内容
		        //InputStream is = con.getInputStream();
	            //定义BufferedReader输入流来读取URL的响�?
	            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	            String line;
	            while((line = in.readLine()) != null) {
	            	result.append(line).append(System.lineSeparator());
	            }
	            System.out.println(result);
	            return result.toString();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally {
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	                if(con != null) {
	                    con.disconnect();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result.toString();
		}
	    

		private static class TrustAnyTrustManager implements X509TrustManager {
			 
	        public void checkClientTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public void checkServerTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public X509Certificate[] getAcceptedIssuers() {
	            return new X509Certificate[] {};
	        }

	    }
	 
	    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
	        public boolean verify(String hostname, SSLSession session) {
	            return true;
	        }
	    }
}
