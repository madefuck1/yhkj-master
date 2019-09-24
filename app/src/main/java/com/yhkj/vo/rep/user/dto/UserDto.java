package com.yhkj.vo.rep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/28 13:30
 */
@Setter
@Getter
@ApiModel
public class UserDto {

    @ApiModelProperty(value = "用户id",name = "userId")
    private Long userId;

    @ApiModelProperty(value = "用户手机号码",name = "phone")
    private String phone;

    @ApiModelProperty(value = "姓名/昵称",name = "username")
    private String username;

    @ApiModelProperty(value = "邮箱",name = "email")
    private String email;

    @ApiModelProperty(value = "头像路径",name = "avatarUrl")
    private String avatarUrl;

    @ApiModelProperty(value = "用户等级（1：买家；2：卖家；99：管理员；）",name = "userLevel")
    private Integer userLevel;

    @ApiModelProperty(value = "店铺id",name = "shopId")
    private Long shopId;

    @ApiModelProperty(value = "单位性质（1：企业；2：个体工商户）",name = "companyType")
    private Integer companyType;

    @ApiModelProperty(value = "单位名称",name = "companyName")
    private String companyName;

    @ApiModelProperty(value = "单位地址",name = "companyAddress")
    private String companyAddress;

    @ApiModelProperty(value = "经营范围",name = "businessScope")
    private String businessScope;

    @ApiModelProperty(value = "主营产品",name = "mainProducts")
    private String mainProducts;

    @ApiModelProperty(value = "联系人",name = "linkPerson")
    private String linkPerson;

    @ApiModelProperty(value = "联系电话",name = "linkPhone")
    private String linkPhone;

    @ApiModelProperty(value = "店铺名称",name = "shopName")
    private String shopName;

    @ApiModelProperty(value = "店铺介绍",name = "shopIntroduce")
    private String shopIntroduce;

    @ApiModelProperty(value = "店铺头像url",name = "shopAvatarUrl")
    private String shopAvatarUrl;

    @ApiModelProperty(value = "身份证照片url",name = "idCardUrl")
    private String idCardUrl;

    @ApiModelProperty(value = "营业执照url",name = "businessLicenseUrl")
    private String businessLicenseUrl;

    @ApiModelProperty(value = "入驻状态：（1：申请中；2：申请成功；3：申请失败；99：店铺关闭",name = "applyStatus")
    private Integer applyStatus;
}
