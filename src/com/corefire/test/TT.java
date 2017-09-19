package com.corefire.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.alibaba.fastjson.JSONObject;
import com.corefire.config.CorefireConfig;
import com.corefire.util.CorefireHttpPost;
import com.corefire.util.MD5;
import com.corefire.util.SignUtils;
import com.corefire.util.XmlUtils;

public class TT {

	public static void h5pay() {

		 /*<mch_id>100010</mch_id>
		    <body>test qrcode pay</body>
		    <spbill_create_ip>127.0.0.1</spbill_create_ip>
		    <notify_url>http://127.0.0.1:3000/pay_notify/wechat_notify</notify_url>
		    <out_trade_no>36807032</out_trade_no>
		    <total_fee>1</total_fee>
		    <nonce_str>613cc878bfa8432f85ea85f795ca4028</nonce_str>*/
		Map<String, String> map = new HashMap<String, String>();
		//map.put("method", "mbupay.alipay.wap");
		//map.put("version", "2.0.1");
		//map.put("method", "mbupay.alipay.wap");
		map.put("mch_id", "100009221297");
		//map.put("appid", CorefireConfig.appid);
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("body", "测试商品");
		map.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
		map.put("total_fee", "1");
		map.put("spbill_create_ip", "127.0.0.1");
		map.put("limit_pay", "pcredit,creditCard");
		map.put("notify_url", CorefireConfig.notify_url);
		map.put("timeout_express", "5m");
		
		Map<String, String> params = SignUtils.paraFilter(map);
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		String sign = MD5.sign(preStr, "&key=6EE2DC456C26410DF1177BDDFE971450", "utf-8")
				.toUpperCase();
		map.put("sign", sign);
		SortedMap<String,String> sortedmap = XmlUtils.getParameterOMap(map);
		String reqUrl = "http://api.mch.spd.uline.cc/alipay/orders/precreate";
		System.out.println("reqParams:" + XmlUtils.toXml(map));
		System.out.println("请求reqUrl：" + reqUrl);
		String res = null;
		try {
			res = CorefireHttpPost.connect(reqUrl, sortedmap);
			Map<String,String> resultMap = XmlUtils.xml2map(res, "xml");
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultMap);
			System.out.println("输出的结果是："+jsonObject);
            System.out.println("请求返回数据：" + res);  
            //resultMap.put("", "");
            if(SignUtils.checkParam(resultMap, CorefireConfig.key))
            {
            	System.out.println(1111111);
            }
		}catch (Exception e) {
			
		}
	
			/*HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(XmlUtils.toXml(map),
					"utf-8");
			httpPost.setEntity(entityParams);
			client = HttpClients.createDefault();
			response = client.execute(httpPost);
			if (response != null && response.getEntity() != null) {
				Map<String, String> resultMap1 = XmlUtils.toMap(
						EntityUtils.toByteArray(response.getEntity()), "utf-8");
				res = XmlUtils.toXml(resultMap1);
				System.out.println("请求结果：" + res);

			}
		} catch (Exception e) {
			
		}*/
		
		
	}
	public static void findpay() {

		/*<xml>
	    <mch_id>100010</mch_id>
	    <out_trade_no>85698222</out_trade_no>
	    <nonce_str>89c021667fb447e78f16263d89ed6d69</nonce_str>
	    <sign>812E5B685286973336C45DC76465125E</sign>
	</xml>*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_id", "100009221297");
		map.put("nonce_str", String.valueOf(new Date().getTime()));	
		map.put("out_trade_no", "f41371946b394bc3b7c5ef338c988062");
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
	/*1091303de6464e0a8b2ea4f5418dfd2c
	 * <xml>
    <mch_id>100010</mch_id>
    <out_trade_no>277031960</out_trade_no>
    <out_refund_no>2770319601</out_refund_no>
    <transaction_id></transaction_id>
    <refund_fee>1</refund_fee>
    <op_user_id>1404851802</op_user_id>
    <nonce_str>a2c89599844b4645a19df4ef4e6bf5c0</nonce_str>
    <sign>BA05A7453CAC7E363A3958BA745D895E</sign>
</xml>*/
	public static void refund() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_id", "100009221297");
		map.put("nonce_str", String.valueOf(new Date().getTime()));	
		map.put("out_trade_no", "2019515a903548bb96007ff387db7592");
		map.put("out_refund_no", String.valueOf(new Date().getTime()));
		map.put("op_user_id", "100009221297");
		map.put("refund_fee","1");
		Map<String, String> params = SignUtils.paraFilter(map);
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		String sign = MD5.sign(preStr, "&key=6EE2DC456C26410DF1177BDDFE971450", "utf-8")
				.toUpperCase();
		map.put("sign", sign);
		SortedMap<String,String> sortedmap = XmlUtils.getParameterOMap(map);
		String reqUrl = "http://api.mch.spd.uline.cc/alipay/refunds";
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
	
	public static void main(String[] args) {
		h5pay();
		
		//findpay();
		//refund();
	}
}
