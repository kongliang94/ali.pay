package com.corefire.action;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.corefire.util.MD5;


public class JsprecreateUrlTest {
	
	private final static String key = "4d557e735e81dabd33dbb757002b8585";

	private final static String encryptId = "000020000000004";

	private final static Integer apiVersion = 1;
	
	private static final String serverUrl = "https://www.znyoo.com/oss-transaction/gateway/";
	
	public static void main(String[] args) throws Exception {
		JSONObject content = new JSONObject();
		JSONObject obj = new JSONObject();
		String method = "jsprecreateUrl";
		obj.put("encryptId", encryptId);
		obj.put("apiVersion", apiVersion);
		obj.put("method", method);
		obj.put("txnDate", Calendar.getInstance().getTimeInMillis());

		obj.put("mid", "000020000000004");
		obj.put("srcAmt", 10);
		obj.put("walletType", 1);
		obj.put("bizOrderNumber", Calendar.getInstance().getTimeInMillis()+"");
		obj.put("inwechat", "f");
		obj.put("userId", "15350707891");
		content.put("content", JSONObject.toJSONString(obj,SerializerFeature.WriteMapNullValue));
		content.put("key", key);
		String signStr = JSON.toJSONString(content,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		String sign = MD5.sign2(signStr, "utf-8");
		content.remove("key");
		content.put("sign", sign);
		String result = sendHttpsPost(serverUrl+method, JSON.toJSONString(content));
		JSONObject resultObj = JSONObject.parseObject(result);
		JSONObject resultSignObj = new JSONObject();
		resultSignObj.put("result", resultObj.getString("result"));
		resultSignObj.put("key", key);
		signStr = JSON.toJSONString(resultSignObj,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		sign = MD5.sign2(signStr, "utf-8");
		System.out.println(sign.equals(resultObj.getString("sign")));
		String resultStr = resultObj.getString("result");
		//System.out.println(resultStr);
	}
	
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
