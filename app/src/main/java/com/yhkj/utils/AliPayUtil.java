package com.yhkj.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.yhkj.base.AliPayConfig;
import com.yhkj.vo.rep.order.dto.PayOrderDto;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class AliPayUtil {

    public static PayOrderDto aliPay(String subject, String unitePayOrderNumber, String total_amount,String orderType) {
        PayOrderDto dto = new PayOrderDto();
        try {


            Map<String, String> orderMap = new LinkedHashMap<String, String>();            //订单实体

            orderMap.put("out_trade_no", unitePayOrderNumber);
            // 订单名称，必填
            orderMap.put("subject", subject);
            // 付款金额，必填
            orderMap.put("total_amount", total_amount);
            // 商品描述，可空
            orderMap.put("body", orderType);
            // 超时时间 可空
            orderMap.put("timeout_express", "30m");
            // 销售产品码 必填
            orderMap.put("product_code", "QUICK_WAP_PAY");


            //实例化客户端
            AlipayClient client = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.private_key, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setPassbackParams(URLEncoder.encode(orderMap.get("body")));
            ;  //描述信息  添加附加数据
            model.setBody(orderMap.get("body"));                        //商品信息（订单类型）
            model.setSubject(orderMap.get("subject"));                  //商品名称
            model.setOutTradeNo(orderMap.get("out_trade_no"));          //商户订单号(自动生成)
            model.setTimeoutExpress(orderMap.get("timeout_express"));     //交易超时时间
            model.setTotalAmount(orderMap.get("total_amount"));         //支付金额
            model.setProductCode(orderMap.get("product_code"));         //销售产品码
            model.setSellerId(AliPayConfig.SELLER_ID);                        //商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.notify_url);          //回调地址

            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            String orderStr = response.getBody();

            dto.setResult(orderStr);
            dto.setStatus(0);
            dto.setMsg("订单生成成功");

        } catch (Exception e) {
            dto.setStatus(1);
            dto.setMsg("订单生成失败");
        }
        return dto;
    }



}
