<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
<title>微信支付</title>
<meta name="description" content="微信支付" />
<meta name="keywords" content="微信支付" />
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript"></script>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<%
String aaa = (String)request.getAttribute("sdkParam");
%>
	<script type="text/javascript">
		//调用微信JS api 支付
	
		function pay() {
			var s=${sdkParam};
			WeixinJSBridge.invoke('getBrandWCPayRequest',s, function(res) {
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					alert("success");
				} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
				if (res.err_msg == "get_brand_wcpay_request:fail") {
					alert(1);
				}
			});
		}
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			onBridgeReady();
		}
	</script>
</head>
<body>
<div align="center" bgcolor="#666666">
         <div>
         
         </div>
         <div align="center">
		<button style="width:600px; height:100px; background-color:#FE6714; border:0px #FE6714 solid; cursor: pointer;  color:white;  font-size:50px;" type="button" onclick="pay()" >微信支付</button>
		</div>


</div>
</body>
</html>
