package com.corefire.zytest;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.corefire.util.MD5;


public class JsprecreateUrlTest {
	
	/*private final static String key = "ba42359a1229e949d5c68923c15b23aa";

	private final static String encryptId = "000330000000006";

	private final static Integer apiVersion = 1;
	
	private static final String serverUrl = "https://pay.juhezhifujia.com/oss-transaction/gateway/";*/
	
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
		obj.put("srcAmt", 0.01);
		obj.put("walletType", 1);
		obj.put("mid", "000020000000004");
		
		obj.put("txnDate", Calendar.getInstance().getTimeInMillis());	
		obj.put("bizOrderNumber", Calendar.getInstance().getTimeInMillis()+"");
		System.out.println(obj.toJSONString());
		//obj.put("inwechat", "f");
		//obj.put("userId", "15350707891");
		content.put("content", JSONObject.toJSONString(obj,SerializerFeature.WriteMapNullValue));
		content.put("key", key);
		String signStr = JSON.toJSONString(content,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		String sign = MD5.sign2(signStr, "utf-8");
		content.remove("key");
		content.put("sign", sign);
		String result = TestUtils.sendHttpsPost(serverUrl+method, JSON.toJSONString(content));
		//System.out.println(result);
		JSONObject resultObj = JSONObject.parseObject(result);
		JSONObject resulta = JSONObject.parseObject((String) resultObj.get("result"));
		JSONObject url=(JSONObject) resulta.get("data");
		StringBuffer sb=new StringBuffer();
		sb.append("alipays://platformapi/startApp?appId=10000011&url=").append(url.get("url"));
		System.out.println(sb.toString());
		String s=MD5andKL.KL(sb.toString()+"e1gdr53");
		System.out.println(s);
		//System.out.println(MD5andKL.JM(s));
		
		//System.out.println(resultObj.get("data"));
		JSONObject resultSignObj = new JSONObject();
		resultSignObj.put("result", resultObj.getString("result"));
		resultSignObj.put("key", key);
		//System.out.println(resultObj.get("data"));
		signStr = JSON.toJSONString(resultSignObj,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		sign = MD5.sign2(signStr, "utf-8");
		System.out.println(sign.equals(resultObj.getString("sign")));
		String resultStr = resultObj.getString("result");
		//System.out.println(resultStr);
	}
	
	 
	    

}
