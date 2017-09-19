<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>支付宝支付测试页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/index.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/alipay2wap.js"></script>
		<script type="text/javascript">
		
		function pay(){
			//window.location.href="https://qr.alipay.com/bax02412k3g8jy8z11lx4040";
			//alipay_wap($("#perpay_id").val(),'http://www.alipay.com');
			alipay_wap('https://qr.alipay.com/bax05797wqbnd41d8mpy20bb','http://www.baidu.com');
			//alipay_wap("071B1B1F1C5540401E1D410E03061F0E16410C0002400D0E175F59585F5C5C1F1F0D1A06071818030D5C5B5F560D",'');
			}
	</script>
</head>
<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
<input id="perpay_id" type="hidden" value="${prepay_id}"/>
<input type="hidden" name="out_trade_no"  value="${out_trade_no}"/>


	<div
		style="margin: 0 auto; width: 300px; height: 50px; line-height: 50px; text-align: center;">订单信息</div>
	<div
		style="margin: 0 auto; width: 300px; height: 50px; line-height: 50px; text-align: center;">
		商品名称：<input id="body" value="${body}"/>
		
	</div>
	<div
		style="margin: 0 auto; width: 300px; height: 50px; line-height: 50px; text-align: center;">
		订单金额：<input name="total_fee"  value="${total_fee}"/>
		
	</div>
	<center>
		<input style="width: 100px; height: 40px;" type="button" name="alipay"
			value="支付宝付款" onclick="pay()">
	</center>
</body>
</body>
</html>