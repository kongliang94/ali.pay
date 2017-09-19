package com.corefire.zytest;

import java.util.Calendar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.corefire.util.MD5;

public class QueryTest {

	private final static String key = "4d557e735e81dabd33dbb757002b8585";

	private final static String encryptId = "000020000000004";

	private final static Integer apiVersion = 1;
	
	private static final String serverUrl = "https://www.znyoo.com/oss-transaction/gateway/";
	
	
	public static void query(){
		/*method
		mid
		walletType
		srcAmt*/
		JSONObject content = new JSONObject();
		JSONObject obj = new JSONObject();
		String method = "query";
		obj.put("encryptId", encryptId);
		obj.put("apiVersion", apiVersion);
		obj.put("txnDate", Calendar.getInstance().getTimeInMillis());
		obj.put("method", method);
		obj.put("mid", "000020000000004");
		obj.put("bizOrderNumber","1505736763074");
		obj.put("isLoop", 1);
		obj.put("looptime", 5);
		content.put("content", JSONObject.toJSONString(obj,SerializerFeature.WriteMapNullValue));
		content.put("key", key);
		String signStr = JSON.toJSONString(content,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		String sign = MD5.sign2(signStr, "utf-8");
		content.remove("key");
		content.put("sign", sign);
		String result = TestUtils.sendHttpsPost(serverUrl+method, JSON.toJSONString(content));
		JSONObject resultObj = JSONObject.parseObject(result);
		JSONObject resultSignObj = new JSONObject();
		resultSignObj.put("result", resultObj.getString("result"));
		resultSignObj.put("key", key);
		signStr = JSON.toJSONString(resultSignObj,SerializerFeature.WriteMapNullValue);
		System.out.println("signStr=="+signStr);
		sign = MD5.sign2(signStr, "utf-8");
		System.out.println(sign.equals(resultObj.getString("sign")));
		String resultStr = resultObj.getString("result");
	}
	
	public static void main(String[] args) {
		query();
	}
}
