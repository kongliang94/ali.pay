package com.corefire.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimerTask;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.alibaba.fastjson.JSONObject;
import com.corefire.config.CorefireConfig;
import com.corefire.util.CorefireHttpPost;
import com.corefire.util.MD5;
import com.corefire.util.SignUtils;
import com.corefire.util.XmlUtils;

public class TimerTaskTest  extends TimerTask{

	public void findPFpay() {

		/*<xml>
	    <mch_id>100010</mch_id>
	    <out_trade_no>85698222</out_trade_no>
	    <nonce_str>89c021667fb447e78f16263d89ed6d69</nonce_str>
	    <sign>812E5B685286973336C45DC76465125E</sign>
	</xml>*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_id", "100009221297");
		map.put("nonce_str", String.valueOf(new Date().getTime()));	
		map.put("out_trade_no", "4d0f58437f4c4578aaae28b43e679446");
		Map<String, String> params = SignUtils.paraFilter(map);
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		String sign = MD5.sign(preStr, "&key=6EE2DC456C26410DF1177BDDFE971450", "utf-8")
				.toUpperCase();
		map.put("sign", sign);
		SortedMap<String,String> sortedmap = XmlUtils.getParameterOMap(map);
		String reqUrl = "http://api.mch.spd.uline.cc/alipay/orders/query";
		System.out.println("reqParams:" + XmlUtils.toXml(map));
		System.out.println("请求reqUrl：" + reqUrl);
		String res = null;
		try {
			res = CorefireHttpPost.connect(reqUrl, sortedmap);
			Map<String,String> resultMap = XmlUtils.xml2map(res, "xml");
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultMap);
			System.out.println("输出的结果是："+jsonObject);
           System.out.println("请求返回数据：" + res);  
          
		}catch (Exception e) {
			
		}
	}
	
	public static void findLZPay(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_id", CorefireConfig.mch_id);
	    map.put("appid", CorefireConfig.appid);
	    map.put("method", "mbupay.alipay.query");
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("out_trade_no","9db932968fac");
		map.put("notify_url", CorefireConfig.notify_url);
		map.put("version", "2.0.1");
		Map<String, String> params = SignUtils.paraFilter(map);
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		String sign = MD5.sign(preStr, "&key=" + CorefireConfig.key, "utf-8")
				.toUpperCase();
		map.put("sign", sign);
		SortedMap<String,String> sortedmap = XmlUtils.getParameterOMap(map);
		String reqUrl = CorefireConfig.common_url;
		//String reqUrl = "http://juhezhifujia.net/JSAPI/notify.jsp";
		System.out.println("reqParams:" + XmlUtils.toXml(map));
		System.out.println("请求reqUrl：" + reqUrl);
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		String res = null;
		try {
			res = CorefireHttpPost.connect(reqUrl, sortedmap);
			Map<String,String> resultMap = XmlUtils.xml2map(res, "xml");
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultMap);
			System.out.println("输出的结果是："+jsonObject);
            System.out.println("请求返回数据：" + res);  
            resultMap.put("", "");
            if(SignUtils.checkParam(resultMap, CorefireConfig.key))
            {
            	System.out.println(1111111);
            }
		}catch (Exception e) {
			
		}
	}
	@Override
	public void run() {
		Date date = new Date(this.scheduledExecutionTime());  
        System.out.println("本次执行该线程的时间为：" + date); 
        findPFpay();
        //findLZPay();
	}

}
