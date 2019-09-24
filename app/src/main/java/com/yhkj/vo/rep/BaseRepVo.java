package com.yhkj.vo.rep;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: chen
 * @Date: 2019/3/4
 * @Description:
 */
@Setter
@Getter
@ToString
@ApiModel
public class BaseRepVo {
    @ApiModelProperty(value = "结果成功/失败",name = "success")
    private boolean success;
    @ApiModelProperty(value = "返回结果提示信息（根据实际情况返回）",name = "message")
    private String message;
    @ApiModelProperty(value = "返回token值（根据实际情况返回）",name = "token")
    private String token;
    @ApiModelProperty(value = "暂时没用到",name = "type")
    private int type;
    @ApiModelProperty(value = "暂时只在登录的时候用到，用来判断是否为卖家(0成功)",name = "code")
    private int code;


    public BaseRepVo() {
        this.success = true;
    }
}
