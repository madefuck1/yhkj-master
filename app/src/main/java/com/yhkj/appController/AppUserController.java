package com.yhkj.appController;

import com.yhkj.Interceptor.MemberAccess;
import com.yhkj.base.BaseController;
import com.yhkj.constant.StaticConstant;
import com.yhkj.enums.ChangePageTypeEnum;
import com.yhkj.enums.MessageTypeEnum;
import com.yhkj.model.User;
import com.yhkj.service.MessageService;
import com.yhkj.service.UserService;
import com.yhkj.utils.MD5Utils;
import com.yhkj.utils.RedisUtils;
import com.yhkj.utils.UploadPicUtil;
import com.yhkj.vo.rep.BaseRepVo;
import com.yhkj.vo.rep.PicUrlRepVo;
import com.yhkj.vo.rep.user.UserRepVo;
import com.yhkj.vo.rep.user.dto.UserDto;
import com.yhkj.vo.req.user.LoginPReqVo;
import com.yhkj.vo.req.user.UpdateUserDetailReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: chen
 * @Date: 2019/3/4
 * @Description:
 */
@RestController
@RequestMapping(value = "app/user")
@Api(value = "app用户", description = "用户管理")
public class AppUserController extends BaseController {

    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.loginName}")
    private String ftpUserName;
    @Value("${ftp.password}")
    private String ftpPassword;

    private static int ftpPort = 21;
    private static String url = "/uploadAvatar";
    private static String port = ":8088";

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @ApiOperation(value = "登录 手机号+密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataType = "LoginPReqVo-String"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "LoginPReqVo-String")
    })
    @RequestMapping(value = "loginByPassword", method = RequestMethod.POST)
    public UserRepVo loginByPassword(@RequestBody LoginPReqVo reqVo, HttpServletResponse response) {
        UserRepVo repVo=new UserRepVo();
        User user = userService.login_Phone(reqVo.getPhone(),reqVo.getPassword());
        if (user != null) {
            String token = setToken(response, user.getUserId());
            repVo.setToken(token);
            repVo.setSuccess(true);
        } else {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.LOGIN_PASSWORD_ERROR);
        }
        repVo.setData(tranformDto(user));
        return repVo;
    }
    @ApiOperation(value = "登录 手机号+验证码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataType = "LoginPReqVo-String"),
            @ApiImplicitParam(name = "verMessage", value = "手机验证码", required = true, dataType = "LoginPReqVo-String")
    })
    @RequestMapping(value = "loginByPhone", method = RequestMethod.POST)
    public UserRepVo loginByPhone(@RequestBody LoginPReqVo reqVo, HttpServletResponse response) throws Exception {
        UserRepVo repVo=new UserRepVo();
        User user = userService.getByPhone(reqVo.getPhone());
        if (user != null) {
            boolean success = messageService.isExpiredOrCorrect(reqVo.getPhone(), reqVo.getVerMessage(), MessageTypeEnum.LOGIN.getType());
            if(success) {
                String token = setToken(response, user.getUserId());
                repVo.setToken(token);
                repVo.setSuccess(true);
            }else{
                repVo.setSuccess(false);
                repVo.setMessage(StaticConstant.CODE_ERROR);
                repVo.setType(ChangePageTypeEnum.normal.getType());
            }
        } else {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.LOGIN_USER_NULL);
        }
        repVo.setData(tranformDto(user));
        return repVo;
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "verMessage", value = "手机验证码", required = true, dataType = "LoginPReqVo-String")
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public BaseRepVo resetPassword(@RequestBody LoginPReqVo ReqVo) throws Exception {
        UserRepVo repVo=new UserRepVo();
        boolean success = messageService.isExpiredOrCorrect(ReqVo.getPhone(), ReqVo.getVerMessage(), MessageTypeEnum.RESETPASSWORD.getType());
        if (success) {
            User user = userService.getByPhone(ReqVo.getPhone());
            user.setUserPassword(MD5Utils.md5(ReqVo.getPassword()));
            userService.updateUser(user);
            repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
        } else {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.CODE_ERROR);
        }
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "验证码注册，设置账户密码")
    @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "LoginPReqVo-String")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public BaseRepVo updatePassword(@RequestBody LoginPReqVo reqVo, HttpServletRequest request) {
        BaseRepVo repVo = new BaseRepVo();
        if (StringUtils.isBlank(reqVo.getPhone()) || "".equals(reqVo.getPhone())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR2);
            return repVo;
        }
        if (StringUtils.isBlank(reqVo.getPassword()) || "".equals(reqVo.getPassword())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR3);
            return repVo;
        }
        User user = getUserInfo(request);
        user.setUserPassword(MD5Utils.md5(reqVo.getPassword()));
        userService.updateUser(user);
        repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "登录根据原密码更新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "LoginPReqVo-String"),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "LoginPReqVo-String")
    })
    @RequestMapping(value = "updatePasswordByOldPassword", method = RequestMethod.POST)
    public BaseRepVo updatePasswordByOldPassword(@RequestBody LoginPReqVo reqVo, HttpServletRequest request) {
        BaseRepVo repVo = new BaseRepVo();
        if (StringUtils.isBlank(reqVo.getOldPassword()) || "".equals(reqVo.getOldPassword())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR5);
            return repVo;
        }
        if (StringUtils.isBlank(reqVo.getPassword()) || "".equals(reqVo.getPassword())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR6);
            return repVo;
        }
        User user = getUserInfo(request);
        if (MD5Utils.md5(reqVo.getOldPassword()).equals(user.getUserPassword())) {
            user.setUserPassword(MD5Utils.md5(reqVo.getPassword()));
            userService.updateUser(user);
            repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
        } else {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.PASSWORD_INCORRECT);
        }
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "个人信息")
    @RequestMapping(value = "userDetail", method = RequestMethod.POST)
    public UserRepVo userDetail(HttpServletRequest request) {
        UserRepVo repVo = new UserRepVo();
        repVo.setData(tranformDto(getUserInfo(request)));
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "更新个人信息")
    @RequestMapping(value = "updateUserDetail", method = RequestMethod.POST)
    public UserRepVo updateUserDetail(HttpServletRequest request, @RequestBody UpdateUserDetailReqVo reqVo) {
        UserRepVo repVo = new UserRepVo();
        User user = getUserInfo(request);
        user.setNickName(reqVo.getUsername());
//        user.setAddress(reqVo.getAddress());
        user.setUserAvatar(reqVo.getAvatarURL());
        user.setUserEmail(reqVo.getEmail());
        userService.updateUser(user);
        return repVo;
    }


    @MemberAccess
    @ApiOperation(value = "头像上传")
    @RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
    public PicUrlRepVo uploadAvatar(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        PicUrlRepVo repVo = UploadPicUtil.uploadPic(file, ftpHost, ftpUserName, ftpPassword, ftpPort, port, url);
        return repVo;
    }

    @MemberAccess
    @ApiOperation("注销登录")
    @RequestMapping(value = "loginOut", method = RequestMethod.POST)
    public BaseRepVo loginOut(HttpServletRequest request) {
        BaseRepVo repVo = new BaseRepVo();
        if (RedisUtils.delkeyObject(request.getHeader("Authorization")) == 1) {
            repVo.setSuccess(true);
            repVo.setMessage(StaticConstant.LOGINOUT_SUCCESS);
        } else {
            repVo.setSuccess(false);
        }
        return repVo;
    }


    //############工具类
    private UserDto tranformDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setPhone(user.getUserPhone());
        dto.setUsername(user.getNickName());
        dto.setEmail(user.getUserEmail());
        dto.setAvatarUrl(user.getUserAvatar());
        dto.setUserLevel(user.getUserLevel());

//        if (user.getUserShop() != null) {
//            if (user.getUserShop().getShopId() != null) {
//                dto.setShopId(user.getUserShop().getShopId());
//                dto.setCompanyType(user.getUserShop().getCompanyType());
//                dto.setCompanyName(user.getUserShop().getCompanyName());
//                dto.setCompanyAddress(user.getUserShop().getCompanyAddress());
//                dto.setBusinessScope(user.getUserShop().getBusinessScope());
//                dto.setMainProducts(user.getUserShop().getMainProducts());
//                dto.setLinkPerson(user.getUserShop().getLinkPerson());
//                dto.setLinkPhone(user.getUserShop().getLinkPhone());
//                dto.setShopName(user.getUserShop().getShopName());
//                dto.setShopIntroduce(user.getUserShop().getShopIntroduce());
//                dto.setShopAvatarUrl(user.getUserShop().getShopAvatarUrl());
//                dto.setIdCardUrl(user.getUserShop().getIdCardUrl());
//                dto.setBusinessLicenseUrl(user.getUserShop().getBusinessLicenseUrl());
//                dto.setApplyStatus(user.getUserShop().getApplyStatus());
//            }
//        }
        return dto;
    }

    ////备用，建议项
//    private BaseRepVo suggestReqVoCheck(SuggestReqVo suggestReqVo) {
//        BaseRepVo repVo = new BaseRepVo();
//        if (StringUtils.isBlank(suggestReqVo.getMessage()) || "".equals(suggestReqVo.getMessage())) {
//            repVo.setSuccess(false);
//            repVo.setMessage(StaticConstant.USER_ERROR1);
//            return repVo;
//        }
//        return repVo;
//    }
}
