<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId": "${payResponse.appId}",         // 公众号名称，由商户传入
                    "timeStamp": "${payResponse.timeStamp}", // 时间戳，自1970年以来的秒数
                    "nonceStr": "${payResponse.nonceStr}",   // 随机串
                    "package": "${payResponse.packAge}",
                    "signType" :"MD5",                       // 微信签名方式
                    "paySign": "${payResponse.paySign}"      // 微信签名
                },
                // 跳转
                function(res){
                    /**
                     * res.err_msg返回值
                     * 支付成功:            get_brand_wcpay_request:ok
                     * 支付过程中用户取消:   get_brand_wcpay_request:cancel
                     * 支付失败:            get_brand_wcpay_request:fail
                     * 注: JS API的返回结果get_brand_wcpay_request:ok仅在用户成功完成支付时返回。
                     *    由于前端交互复杂，get_brand_wcpay_request:cancel和get_brand_wcpay_request:fail可以统一处理为:
                     *    用户遇到错误或主动放弃，不必细化区分。
                     *    此处，不做任何返回值的细化区分，统一跳转。
                     */
                    //if(res.err_msg == "get_brand_wcpay_request:ok") {}
                    location.href = "${returnUrl}";
                }
        );
    }

    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>