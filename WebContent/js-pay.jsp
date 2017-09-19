<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JS支付测试</title>
<script type ="text/javascript" src ="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	var trade_no = '${trade_no}';
	
		//AlipayJSBridge.call("tradePay" ,{tradeNO: jQuery(trade_no).html()} , function (result){});
		AlipayJSBridge.call("tradePay",{
			tradeNO: trade_no
			}, function(result){
			});
</script>
</head>
<body>

<div style="background:#fff;">

</div>
</body>
</html>