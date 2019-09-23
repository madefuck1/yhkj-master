package com.yhkj.vo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: chen
 * @Date: 2019/3/19
 * @Description:
 */
@Getter
@Setter
public class ProductExcel {

    @Excel(name = "firstAssort")
    private String firstAssort;
    @Excel(name = "secondAssort")
    private String secondAssort;
    @Excel(name = "thirdAssort")
    private String thirdAssort;

    @Excel(name = "isSpot")
    private String isSpot ; // 是否现货
    @Excel(name = "price")
    private String price ; // 价格
    @Excel(name = "url")
    private String url ; // 图片
    @Excel(name = "productName")
    private String productName; // 产品名
    @Excel(name = "quantityToPrice")
    private String quantityToPrice; // 起订量

    @Excel(name = "stockUnit")
    private String stockUnit ; // 单位
    @Excel(name = "stock")
    private String stock ; // 库存
    @Excel(name = "productColor")
    private String productColor ; // 颜色
    @Excel(name = "shopName")
    private String shopName;  // 店铺名

    @Excel(name = "key1Value")
    private String key1Value;

    @Excel(name = "key2Value")
    private String key2Value;

    @Excel(name = "key3Value")
    private String key3Value;

    @Excel(name = "key4Value")
    private String key4Value;

    @Excel(name = "key5Value")
    private String key5Value;

    @Excel(name = "key6Value")
    private String key6Value;

    @Excel(name = "key7Value")
    private String key7Value;

    @Excel(name = "key8Value")
    private String key8Value;

    @Excel(name = "key9Value")
    private String key9Value;

    @Excel(name = "key10Value")
    private String key10Value;

    @Excel(name = "key11Value")
    private String key11Value;

    @Excel(name = "key12Value")
    private String key12Value;

    @Excel(name = "key13Value")
    private String key13Value;

    @Excel(name = "key14Value")
    private String key14Value;

    @Excel(name = "key15Value")
    private String key15Value;

    @Excel(name = "key16Value")
    private String key16Value;

    @Excel(name = "key17Value")
    private String key17Value;

    @Excel(name = "key18Value")
    private String key18Value;

    @Excel(name = "key19Value")
    private String key19Value;

    @Excel(name = "key20Value")
    private String key20Value;

    @Excel(name = "key21Value")
    private String key21Value;

    @Excel(name = "key22Value")
    private String key22Value;

    @Excel(name = "key23Value")
    private String key23Value;

    @Excel(name = "key24Value")
    private String key24Value;

    @Excel(name = "key25Value")
    private String key25Value;
}
