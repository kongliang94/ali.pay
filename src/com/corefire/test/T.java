package com.corefire.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.corefire.config.CorefireConfig;
import com.corefire.util.CorefireHttpPost;
import com.corefire.util.MD5;
import com.corefire.util.SignUtils;
import com.corefire.util.XmlUtils;

public class T {

	public static void h5pay() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("method", "mbupay.alipay.wap");
		map.put("version", "2.0.1");
		map.put("method", "mbupay.alipay.wap");
		map.put("mch_id", CorefireConfig.mch_id);
		map.put("appid", CorefireConfig.appid);
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("body", "测试商品");
		map.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
		map.put("total_fee", "1");
		map.put("notify_url", CorefireConfig.notify_url);
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
	public static void findPay(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_id", CorefireConfig.mch_id);
	    map.put("appid", CorefireConfig.appid);
	    map.put("method", "mbupay.alipay.query");
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("out_trade_no","f2bd81733920457a91d09db932968fac");
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
	
	public static void wxpay() {

		 /*xml>
   <mch_id>100010</mch_id>
   <total_fee>10</total_fee>
   <out_trade_no>5812281</out_trade_no>
   <body>BODY unif 支付测试</body>
   <attach>ATTACH unif 订单额外描述</attach>
   <detail>DETAIL unif 刷卡支付测试</detail>
   <spbill_create_ip>127.0.0.1</spbill_create_ip>
   <notify_url>http://127.0.0.1:3001/pay_notify/wechat_notify</notify_url>
   <nonce_str>e40534ec4a8d493b9759f105c2df8c3c</nonce_str>
   <trade_type>NATIVE</trade_type>
   <sign>F68C75279E86B7EB8C0722D4AA314549</sign>
</xml>*/
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
		map.put("trade_type","");
		map.put("openid","");
		map.put("notify_url", CorefireConfig.notify_url);
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
           resultMap.put("", "");
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
	public static void main(String[] args) {
		
		//h5pay();
		findPay();
		//System.out.println(new Date().getTime());
	}
}
