package com.yhkj.appController;

import com.yhkj.Interceptor.MemberAccess;
import com.yhkj.base.BaseController;
import com.yhkj.constant.StaticConstant;
import com.yhkj.enums.ChangePageTypeEnum;
import com.yhkj.enums.MessageTypeEnum;
import com.yhkj.model.User;
import com.yhkj.service.UserService;
import com.yhkj.utils.DateUtils;
import com.yhkj.utils.MD5Utils;
import com.yhkj.utils.RedisUtils;
import com.yhkj.utils.UploadPicUtil;
import com.yhkj.vo.rep.BaseRepVo;
import com.yhkj.vo.rep.PicUrlRepVo;
import com.yhkj.vo.rep.user.UserRepVo;
import com.yhkj.vo.rep.user.dto.UserDto;
import com.yhkj.vo.req.user.*;
import io.swagger.annotations.Api;
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

    @Autowired
    SuggestService suggestService;


    @ApiOperation(value = "登录 密码登录 --返回code（0：买家；1：卖家申请中；2：卖家申请成功；3：卖家申请失败，重新申请）")
    @RequestMapping(value = "loginByPassword", method = RequestMethod.POST)
    public UserRepVo loginByPassword(@RequestBody LoginPReqVo reqVo, HttpServletResponse response) {
        UserRepVo repVo = loginPReqVoCheck(reqVo);
        if (repVo.isSuccess()) {
            User user = userService.loginByPhone(reqVo.getPhone());
            if (user != null) {
                if (user.getPassword().equals(MD5Utils.md5(reqVo.getPassword()))) {
                    user = userService.loginByPhone(reqVo.getPhone());
                    String token = setToken(response, user.getUserId(), user.getUserStatus());
                    repVo.setToken(token);
                    if (user.getUserShop() == null) {
                        repVo.setCode(0);
                    } else {
                        if (user.getUserShop().getApplyStatus() != null) {
                            repVo.setCode(user.getUserShop().getApplyStatus());
                        } else {
                            repVo.setCode(0);
                        }
                    }
                } else {
                    repVo.setSuccess(false);
                    repVo.setMessage(StaticConstant.LOGIN_PASSWORD_ERROR);
                }
                repVo.setData(tranformDto(user));
            } else {
                repVo.setSuccess(false);
                repVo.setMessage(StaticConstant.LOGIN_USER_NULL);
            }
        }
        return repVo;
    }

    @ApiOperation(value = "登录 验证码登录 --返回code（0：买家；1：卖家申请中；2：卖家申请成功；3：卖家申请失败，重新申请")
    @RequestMapping(value = "loginByCode", method = RequestMethod.POST)
    public UserRepVo loginByCode(@RequestBody LoginCReqVo reqVo, HttpServletResponse response) throws Exception {
        UserRepVo repVo = loginCReqVoCheck(reqVo);
        if (repVo.isSuccess()) {
            User user = userService.loginByPhone(reqVo.getPhone());
            boolean success = messageService.isExpiredOrCorrect(reqVo.getPhone(), reqVo.getCode(), user == null ? MessageTypeEnum.REGISTER.getType() : MessageTypeEnum.LOGIN.getType());
            if (success) {
                boolean isExist = true;  //判断是否已存在账号
                if (user == null) {
                    //创建账户
                    User temp = new User();
                    temp.setPhone(reqVo.getPhone());
                    temp.setPassword(MD5Utils.md5("123456"));//设置初始密码（默认：123456）
                    temp.setUserStatus(0);
                    temp.setUserLevel(1);
                    temp.setCreateTime(DateUtils.getToday());
                    userService.registerUser(temp);
                    isExist = false;
                }
                user = userService.loginByPhone(reqVo.getPhone());
                String token = setToken(response, user.getUserId(), user.getUserStatus());
                repVo.setToken(token);
                if (user.getUserShop() == null) {
                    repVo.setCode(0);
                } else {
                    if (user.getUserShop().getApplyStatus() != null) {
                        repVo.setCode(user.getUserShop().getApplyStatus());
                    } else {
                        repVo.setCode(0);
                    }
                }
                if (!isExist) {
                    repVo.setMessage(StaticConstant.REGISTER_CODE);
                    repVo.setType(ChangePageTypeEnum.register.getType());
                } else {
                    repVo.setMessage(StaticConstant.LOGIN_SUCCESS);
                    repVo.setType(ChangePageTypeEnum.login.getType());
                }
                repVo.setData(tranformDto(user));
            } else {
                repVo.setSuccess(false);
                repVo.setMessage(StaticConstant.CODE_ERROR);
                repVo.setType(ChangePageTypeEnum.normal.getType());
            }
        }
        return repVo;
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public BaseRepVo resetPassword(@RequestBody LoginCAndPReqVo cAndPReqVo) throws Exception {
        BaseRepVo repVo = loginCAndPReqVoCheck(cAndPReqVo);
        if (repVo.isSuccess()) {
            boolean success = messageService.isExpiredOrCorrect(cAndPReqVo.getPhone(), cAndPReqVo.getCode(), 2);
            if (success) {
                User user = userService.getByPhone(cAndPReqVo.getPhone());
                user.setPassword(MD5Utils.md5(cAndPReqVo.getPassword()));
                userService.updateUser(user);
                repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
            } else {
                repVo.setSuccess(false);
                repVo.setMessage(StaticConstant.CODE_ERROR);
            }
        }
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "验证码注册，设置账户密码")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public BaseRepVo updatePassword(@RequestBody LoginPReqVo reqVo, HttpServletRequest request) {
        BaseRepVo repVo = loginPReqVoCheck(reqVo);
        if (repVo.isSuccess()) {
            User user = getUserInfo(request);
            user.setPassword(MD5Utils.md5(reqVo.getPassword()));
            userService.updateUser(user);
            repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
        }
        return repVo;
    }

    @MemberAccess
    @ApiOperation(value = "登录根据原密码更新密码")
    @RequestMapping(value = "updatePasswordByOldPassword", method = RequestMethod.POST)
    public BaseRepVo updatePasswordByOldPassword(@RequestBody UpdByOldPasswordReqVo reqVo, HttpServletRequest request) {
        BaseRepVo repVo = UpdByOldPasswordCheck(reqVo);
        if (repVo.isSuccess()) {
            User user = getUserInfo(request);
            if (MD5Utils.md5(reqVo.getOldPassword()).equals(user.getPassword())) {
                user.setPassword(MD5Utils.md5(reqVo.getPassword()));
                userService.updateUser(user);
                repVo.setMessage(StaticConstant.PASSWORD_UPDATE);
            } else {
                repVo.setSuccess(false);
                repVo.setMessage(StaticConstant.PASSWORD_INCORRECT);
            }
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
        user.setUsername(reqVo.getUsername());
//        user.setAddress(reqVo.getAddress());
        user.setAvatar(reqVo.getAvatarURL());
        user.setEmail(reqVo.getEmail());
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
    @ApiOperation(value = "提意见建议")
    @RequestMapping(value = "suggest", method = RequestMethod.POST)
    public BaseRepVo suggest(@RequestBody SuggestReqVo suggestReqVo, HttpServletRequest request) {
        BaseRepVo repVo = suggestReqVoCheck(suggestReqVo);
        if (repVo.isSuccess()) {
            User user = getUserInfo(request);
            Suggest suggest = new Suggest();
            suggest.setSuggestMessage(suggestReqVo.getMessage());
            suggest.setSuggestTime(DateUtils.getToday());
            suggest.setSuggestUserId(user.getUserId());
            suggestService.saveSuggest(suggest);
            repVo.setMessage(StaticConstant.SUCCESS);
        }
        return repVo;
    }


    @MemberAccess
    @ApiOperation("切换版本")
    @RequestMapping(value = "switch", method = RequestMethod.POST)
    public UserRepVo switchSellerVersion(HttpServletRequest request) {
        User user = getUserInfo(request);
        UserRepVo userRepVo = new UserRepVo();
        if (user.getUserShop() == null) {
            userRepVo.setCode(0);
        } else {
            if (user.getUserShop().getApplyStatus() != null) {
                userRepVo.setCode(user.getUserShop().getApplyStatus());
            } else {
                userRepVo.setCode(0);
            }
        }


        userRepVo.setData(tranformDto(user));

        return userRepVo;
    }


    private BaseRepVo suggestReqVoCheck(SuggestReqVo suggestReqVo) {
        BaseRepVo repVo = new BaseRepVo();
        if (StringUtils.isBlank(suggestReqVo.getMessage()) || "".equals(suggestReqVo.getMessage())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR1);
            return repVo;
        }
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


    private UserRepVo loginPReqVoCheck(LoginPReqVo reqVo) {
        UserRepVo repVo = new UserRepVo();
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
        return repVo;
    }

    private UserRepVo loginCReqVoCheck(LoginCReqVo reqVo) {
        UserRepVo repVo = new UserRepVo();
        if (StringUtils.isBlank(reqVo.getCode()) || "".equals(reqVo.getCode())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR4);
            return repVo;
        }
        if (StringUtils.isBlank(reqVo.getPhone()) || "".equals(reqVo.getPhone())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR2);
            return repVo;
        }
        return repVo;
    }

    private UserRepVo loginCAndPReqVoCheck(LoginCAndPReqVo reqVo) {
        UserRepVo repVo = new UserRepVo();
        if (StringUtils.isBlank(reqVo.getCode()) || "".equals(reqVo.getCode())) {
            repVo.setSuccess(false);
            repVo.setMessage(StaticConstant.USER_ERROR4);
            return repVo;
        }
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
        return repVo;
    }

    private UserRepVo UpdByOldPasswordCheck(UpdByOldPasswordReqVo reqVo) {
        UserRepVo repVo = new UserRepVo();
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
        return repVo;
    }

    private UserDto tranformDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setPhone(user.getPhone());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setAvatarUrl(user.getAvatar());
        dto.setUserLevel(user.getUserLevel());

        if (user.getUserShop() != null) {
            if (user.getUserShop().getShopId() != null) {
                dto.setShopId(user.getUserShop().getShopId());
                dto.setCompanyType(user.getUserShop().getCompanyType());
                dto.setCompanyName(user.getUserShop().getCompanyName());
                dto.setCompanyAddress(user.getUserShop().getCompanyAddress());
                dto.setBusinessScope(user.getUserShop().getBusinessScope());
                dto.setMainProducts(user.getUserShop().getMainProducts());
                dto.setLinkPerson(user.getUserShop().getLinkPerson());
                dto.setLinkPhone(user.getUserShop().getLinkPhone());
                dto.setShopName(user.getUserShop().getShopName());
                dto.setShopIntroduce(user.getUserShop().getShopIntroduce());
                dto.setShopAvatarUrl(user.getUserShop().getShopAvatarUrl());
                dto.setIdCardUrl(user.getUserShop().getIdCardUrl());
                dto.setBusinessLicenseUrl(user.getUserShop().getBusinessLicenseUrl());
                dto.setApplyStatus(user.getUserShop().getApplyStatus());
            }
        }
        return dto;
    }


}
