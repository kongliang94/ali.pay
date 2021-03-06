package com.corefire.action;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.corefire.util.MD5;
import com.corefire.zytest.TestUtils;

public class TestWxpayServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private final static String key = "ba42359a1229e949d5c68923c15b23aa";

	private final static String encryptId = "000330000000006";

	private final static Integer apiVersion = 1;
	
	private static final String serverUrl = "https://pay.juhezhifujia.com/oss-transaction/gateway/";
	  @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doPost(req, resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	JSONObject content = new JSONObject();
			JSONObject obj = new JSONObject();
			String method = "jsprecreate";    //测试发现jsprecreateUrl也可以
			obj.put("mid", "000330000000006");
			obj.put("encryptId", encryptId);
			obj.put("apiVersion", apiVersion);
			obj.put("method", method);
			obj.put("txnDate", Calendar.getInstance().getTimeInMillis());

			//obj.put("mid", "000330000000006");
			obj.put("srcAmt", 0.01);
			obj.put("walletType", 2);
			obj.put("bizOrderNumber", Calendar.getInstance().getTimeInMillis()+"");
			//obj.put("userId", "2088421561554081"); 
			obj.put("userId", "ox0RR1KE05shOPTDPOSN8TllBJkg");
			obj.put("subAppid", "wxee6ca381ab7006d5");       //微信必传，切微信必须将openid传给userId
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
			JSONObject resulta = JSONObject.parseObject(resultStr);
			JSONObject data=(JSONObject) resulta.get("data");
			JSONObject sdkParam=JSONObject.parseObject((String)data.get("sdkParam"));
			sdkParam.remove("isRaw");
			String sdkParamstr=sdkParam.toJSONString();
			System.out.println(sdkParamstr);
			req.setAttribute("sdkParam", sdkParamstr);
			req.setAttribute("appId", sdkParamstr);
			req.setAttribute("timeStamp", sdkParamstr);
			req.setAttribute("nonceStr", sdkParamstr);
			req.setAttribute("package", sdkParamstr);		
			req.setAttribute("sdkParam", sdkParamstr);
			req.getRequestDispatcher("index-wxpay-result.jsp").forward(req, resp);
	    }

}
