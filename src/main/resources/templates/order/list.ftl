<html>
<#include "../common/header.ftl">

<#--卖家后台 使用bootstrap-->
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <#--fluid流动布局-->
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <#--<th>支付方式</th>-->
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <#--属性-->
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <#--方法-->
                            <td>${orderDTO.getOrderStatusEnum().message}</td>
                            <td>${orderDTO.getPayStatusEnum().message}</td>
                            <td>${orderDTO.createTime}</td>
                            <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                            <td>
                                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>
                    <#--1..5:1至5, 1..<5: 1至4 -->
                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<#--webSocket: 弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <#--关闭弹窗-->
                <button onclick="javascript:document.getElementById('audio').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <#--刷新当前页面-->
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>

<#--webSocket: 播放音乐-->
<audio id="audio" loop="loop">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<#--webSocket: js-->
<script>
    // h5原生直接支持webSocket
    var websocket = null;
    // 游览器是否支持
    if('WebSocket' in window) {
        /** 与后台建立连接 */
        websocket = new WebSocket('ws://sell.natapp4.cc/sell/webSocket');
    }else {
        console.log('该浏览器不支持websocket!')
        alert('该浏览器不支持websocket!');
    }

    // webSocket open事件
    websocket.onopen = function (event) {
        console.log('建立连接');
    }
    // webSocket close事件
    websocket.onclose = function (event) {
        console.log('连接关闭');
    }
    // webSocket 消息事件
    websocket.onmessage = function (event) {
        console.log('收到消息:' + event.data)
        // 弹窗提醒
        $('#myModal').modal('show');
        // 播放音乐
        $('#audio').play();
        //document.getElementById('audio').play();
    }
    // webSocket 异常事件
    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }
    // webSocket 关闭窗口前关闭webSocket事件
    window.onbeforeunload = function () {
        websocket.close();
    }
</script>
</body>
</html>