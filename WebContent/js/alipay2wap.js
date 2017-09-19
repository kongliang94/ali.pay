 if (typeof AlipayWallet !== 'object') {  
            AlipayWallet = {};  
        }  
  
        (function () {  
            var ua = navigator.userAgent.toLowerCase(),  
                locked = false,  
                domLoaded = document.readyState==='complete',  
                delayToRun;  
  
            function customClickEvent() {  
                var clickEvt;  
                if (window.CustomEvent) {  
                    clickEvt = new window.CustomEvent('click', {  
                        canBubble: true,  
                        cancelable: true  
                    });  
                } else {  
                    clickEvt = document.createEvent('Event');  
                    clickEvt.initEvent('click', true, true);  
                }  
  
                return clickEvt;  
            }  
  
            function getAndroidVersion() {  
                var match = ua.match(/android\s([0-9\.]*)/);  
                return match ? match[1] : false;  
            }  
  
            var noIntentTest = /aliapp|360 aphone|weibo|windvane|ucbrowser|baidubrowser|safari/.test(ua);  
            var hasIntentTest = /chrome|samsung/.test(ua);  
            var isAndroid = /android|adr/.test(ua) && !(/windows phone/.test(ua));  
            var canIntent = !noIntentTest && hasIntentTest && isAndroid;  
            var openInIfr = /weibo|m353/.test(ua);  
            var inWeibo = ua.indexOf('weibo')>-1;
            //需要优化ios拉起
            //var isIOS = !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            //var isIPhone=ua.indexOf('iPhone') > -1
            //var isIPhone=ua.indexOf('safari') > -1;
            
            
            if (ua.indexOf('m353')>-1 && !noIntentTest) {  
                canIntent = false;  
            }  
  
            // 是否在 webview  
            var inWebview = '';  
            if (inWebview) {  
                canIntent = false;  
            }  
  
  
            /**  
             * 打开钱包  
             * @param   {string}    params  唤起钱包的参数设置('alipays://platformapi/startapp?'后面的值)  
             * @param   {boolean}   jumpUrl 唤起钱包后，android下要跳转到的URL；  
             *                      若传"default"，则为https://d.alipay.com/i/index.htm?nojump=1#once  
             */  
            AlipayWallet.open = function (params, jumpUrl) {  
                if (!domLoaded && (ua.indexOf('360 aphone')>-1 || canIntent)) {  
                    var arg = arguments;  
                    delayToRun = function () {  
                        AlipayWallet.open.apply(null, arg);  
                        delayToRun = null;  
                    };  
                    return;  
                }  
  
                // 唤起锁定，避免重复唤起  
                if (locked) {  
                    return;  
                }  
                locked = true;  
  
                var o;  
                // 参数容错  
                if (typeof params==='object') {  
                    o = params;  
                } else {  
                    o = {  
                        params: params,  
                        jumpUrl: jumpUrl  
                    };  
                }  
  
                // 参数容错  
                if (typeof o.params !== 'string') {  
                    o.params = '';  
                }  
                if (typeof o.openAppStore !== 'boolean') {  
                    o.openAppStore = true;  
                }  
  
                o.params = o.params || 'appId=20000001';  
                o.params = o.params + '';  
                o.params = o.params + '&_t=' + (new Date()-0);  
  
                if (o.params.indexOf('startapp?')>-1) {  
                    o.params = o.params.split('startapp?')[1];  
                } else if (o.params.indexOf('startApp?')>-1) {  
                    o.params = o.params.split('startApp?')[1];  
                }  
  
                // 是否为RC环境  
                var isRc = '';  
  
                // 是否唤起re包  
                var isRe = '';  
                if (typeof o.isRe==='undefined') {  
                    o.isRe = !!isRe;  
                }  
  
                // 通过alipays协议唤起钱包  
                var schemePrefix;  
                if (ua.indexOf('mac os')>-1 && ua.indexOf('mobile')>-1) {  
                    // IOS RC包前缀为 alipaysrc  
                    if (isRc) {  
                        if (o.isRe) {
                        	//alert(1);
                            schemePrefix = 'alipayrerc';  
                        } else {  
                        	//alert(2);
                            schemePrefix = 'alipaysrc';  
                        }  
                    }  
                }  
                if (!schemePrefix && o.isRe) {  
                    schemePrefix = 'alipayre';  
                }
                
                schemePrefix = schemePrefix || 'alipays';  
                
                // 由于历史原因，对 alipayqr 前缀做特殊处理  
                if (location.href.indexOf('scheme=alipayqr') > -1) {  
                    schemePrefix = 'alipayqr';  
                    isRc = false;  
                }  
  
                if (!canIntent) {  
                    var alipaysUrl = schemePrefix + '://platformapi/startapp?' + o.params;  
                    //alert(1);
                    //alert(alipaysUrl);
                    if ( ua.indexOf('qq/') > -1 || ( ua.indexOf('safari') > -1 && ua.indexOf('os 10_') > -1 ) ) {  
                        var openSchemeLink = document.getElementById('openSchemeLink');  
                        if (!openSchemeLink) {  
                            openSchemeLink = document.createElement('a');  
                            openSchemeLink.id = 'openSchemeLink';  
                            openSchemeLink.style.display = 'none';  
                            document.body.appendChild(openSchemeLink);  
                        }  
                        openSchemeLink.href = alipaysUrl;  
                        // 执行click  
                        openSchemeLink.dispatchEvent(customClickEvent());  
                    } else {  
                        var ifr = document.createElement('iframe');  
                        ifr.src = alipaysUrl;  
                        ifr.style.display = 'none';  
                        document.body.appendChild(ifr);  
                    }  
                } else {  
                    // android 下 chrome 浏览器通过 intent 协议唤起钱包  
                    var packageKey = 'AlipayGphone';  
                    if (isRc) {  
                        packageKey = 'AlipayGphoneRC';  
                    }  
                    var intentUrl = 'intent://platformapi/startapp?'+o.params+'#Intent;scheme='+ schemePrefix +';package=com.eg.android.'+ packageKey +';end';  
  
                    var openIntentLink = document.getElementById('openIntentLink');  
                    if (!openIntentLink) {  
                        openIntentLink = document.createElement('a');  
                        openIntentLink.id = 'openIntentLink';  
                        openIntentLink.style.display = 'none';  
                        document.body.appendChild(openIntentLink);  
                    }  
                    openIntentLink.href = intentUrl;  
                    // 执行click  
                    openIntentLink.dispatchEvent(customClickEvent());  
                }  
  
                // 延迟移除用来唤起钱包的IFRAME并跳转到下载页  
                setTimeout(function () {  
                    if (typeof o.jumpUrl !== 'string') {  
                        o.jumpUrl = '';  
                    }  
  
                    // URL白名单  
                    var urlPattern = /^http(s)?:\/\/([a-z0-9_\-]+\.)*(alipay|taobao|alibaba|alibaba-inc|tmall|koubei|alipayqr|alipayauth|aliminipayauth|aw|alipayre|alipaysso|alipayshare|alipaytlshare|alipaysopensdk|alipayauthlogin|alicertpay|alipayauthtb\.(com|net|cn|com\.cn)(:\d+)?([/;?].*)?$/;  
                    // 默认跳转地址  
                    if (o.jumpUrl==='default') {  
                    	//o.jumpUrl = 'https://www.baidu.com';
                        o.jumpUrl = 'https://ds.alipay.com/?nojump=true';  
                    }  
  
                    if (o.jumpUrl && typeof o.jumpUrl==='string' && urlPattern.test(o.jumpUrl)) {  
                        location.href = o.jumpUrl;  
                    }  
                }, 1000)  
  
              
                // 唤起加锁，避免短时间内被重复唤起  
                setTimeout(function () {  
                    locked = false;  
                }, 2500)  
            }  
  
            if (!domLoaded) {  
                document.addEventListener('DOMContentLoaded', function () {  
                    domLoaded = true;  
                    if (typeof delayToRun === 'function') {  
                        delayToRun();  
                    }  
                }, false);  
            }  
        })();  

 
		function alipay_wap(qrdecode,jumpUrl){
			//appId=10000011&	
			//alert(qrdecode);AlipayWallet.open({params:'saId=10000007&clientVersion=3.7.0.0718&url='+encodeURI(qrdecode)+'?_s=web-other',jumpUrl:jumpUrl});
			//alert(encodeURI(qrdecode));
			
			AlipayWallet.open({params:'appId=10000011&url='+qrdecode,jumpUrl:jumpUrl});
			window.location.href=jumpUrl;
		}