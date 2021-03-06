package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.core.constant.VerifyConst;
import cn.tucci.sso.server.core.exception.ServiceException;
import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.task.AsyncTaskExecutor;
import cn.tucci.sso.server.core.utils.UrlUtil;
import cn.tucci.sso.server.core.utils.WebUtils;
import cn.tucci.sso.server.model.body.AppSigninBody;
import cn.tucci.sso.server.model.body.SigninBody;
import cn.tucci.sso.server.model.body.SignupBody;
import cn.tucci.sso.server.model.domain.LogSignin;
import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.model.dto.SigninDTO;
import cn.tucci.sso.server.service.CaptchaService;
import cn.tucci.sso.server.service.LogSigninService;
import cn.tucci.sso.server.service.UserAccountService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author tucci.lee
 */
@RestController
public class SignController {
    private final UserAccountService userAccountService;
    private final CaptchaService captchaService;
    private final LogSigninService logSigninService;
    private final SsoProperties ssoProperties;
    private final Authenticator authenticator;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public SignController(UserAccountService userAccountService,
                          CaptchaService captchaService,
                          LogSigninService logSigninService,
                          SsoProperties ssoProperties,
                          Authenticator authenticator,
                          AsyncTaskExecutor asyncTaskExecutor) {
        this.userAccountService = userAccountService;
        this.captchaService = captchaService;
        this.logSigninService = logSigninService;
        this.ssoProperties = ssoProperties;
        this.authenticator = authenticator;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * ????????????
     * 1. ???????????????
     * 2. ??????
     *
     * @param body ????????????
     * @return ok
     */
    @PostMapping("signup")
    public Result<?> signup(@Validated @RequestBody SignupBody body) {
        UserAccount account = new UserAccount();
        BeanUtils.copyProperties(body, account);
        account.setPassword(BCrypt.hashpw(body.getPassword(), BCrypt.gensalt()));

        if (body.getType().equals(VerifyConst.SignupType.EMAIL)) {
            captchaService.verifyEmailCaptcha(VerifyConst.CaptchaType.SIGNUP, body.getEmail(), body.getCaptcha());
            userAccountService.signupByEmail(account);
        } else {
            captchaService.verifyPhoneCaptcha(VerifyConst.CaptchaType.SIGNUP, body.getPhone(), body.getCaptcha());
            userAccountService.signupByPhone(account);
        }

        return Result.ok();
    }

    /**
     * ??????
     * 1. ???????????????
     * 2. shiro??????
     * 3. ??????????????????
     * 4. ??????????????????token??????cookie???????????????
     * 5. ?????????????????????url???token
     *
     * @param body ????????????
     * @return ok
     */
    @PostMapping("signin")
    public Result<?> signin(@Validated @RequestBody SigninBody body, HttpSession session) {
        AuthenticationToken signinToken = new UsernamePasswordToken(body.getUsername(), body.getPassword());
        ResultStatus resultStatus = ResultStatus.OK;
        boolean status = false;
        Subject subject = SecurityUtils.getSubject();
        try {
            captchaService.verifyImgCaptcha(session.getId(), body.getCaptcha());
            subject.login(signinToken);
            status = true;
        } catch (UnknownAccountException e) {
            resultStatus = ResultStatus.USERNAME_ERROR;
            throw new ServiceException(resultStatus);
        } catch (CredentialsException e) {
            resultStatus = ResultStatus.PASSWORD_ERROR;
            throw new ServiceException(resultStatus);
        } catch (LockedAccountException e) {
            resultStatus = ResultStatus.ACCOUNT_LOCK;
            throw new ServiceException(resultStatus);
        } catch (ServiceException e) {
            resultStatus = e.getStatus();
            throw e;
        } catch (Exception e) {
            resultStatus = ResultStatus.SERVER_ERROR;
            throw e;
        } finally {
            this.addSigninLog(body.getUsername(), status, resultStatus.msg());
        }

        Long uid = (Long) subject.getPrincipal();
        String token = authenticator.webSignin(uid, body.getRememberMe());
        String redirectUri = UrlUtil.getRedirectUri(body.getRedirectUri(), ssoProperties.getDefaultRedirectUri());

        SigninDTO result = new SigninDTO()
                .setRedirectUri(redirectUri);
        return Result.ok(result);
    }

    @PostMapping("app/signin")
    public Result<?> appSignin(@Validated @RequestBody AppSigninBody body) {
        AuthenticationToken signinToken = new UsernamePasswordToken(body.getUsername(), body.getPassword());
        ResultStatus resultStatus = ResultStatus.OK;
        boolean status = false;
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(signinToken);
            status = true;
        } catch (UnknownAccountException e) {
            resultStatus = ResultStatus.USERNAME_ERROR;
            throw new ServiceException(resultStatus);
        } catch (CredentialsException e) {
            resultStatus = ResultStatus.PASSWORD_ERROR;
            throw new ServiceException(resultStatus);
        } catch (LockedAccountException e) {
            resultStatus = ResultStatus.ACCOUNT_LOCK;
            throw new ServiceException(resultStatus);
        } catch (ServiceException e) {
            resultStatus = e.getStatus();
            throw e;
        } catch (Exception e) {
            resultStatus = ResultStatus.SERVER_ERROR;
            throw e;
        } finally {
            this.addSigninLog(body.getUsername(), status, resultStatus.msg());
        }

        Long uid = (Long) subject.getPrincipal();
        String token = authenticator.appSignin(uid);

        SigninDTO result = new SigninDTO()
                .setToken(token);
        return Result.ok(result);
    }

    /**
     * ??????????????????
     *
     * @param username ?????????
     * @param status   ??????
     * @param msg      ??????
     */
    private void addSigninLog(String username, boolean status, String msg) {
        String ip = WebUtils.getIp();
        // ???????????????????????????????????????????????????
        UserAgent userAgent = UserAgent.parseUserAgentString(WebUtils.getHeader(HttpHeaders.USER_AGENT));
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();
        LogSignin log = new LogSignin()
                .setUsername(username)
                .setOs(os)
                .setBrowser(browser)
                .setStatus(status)
                .setMsg(msg)
                .setIp(ip);
        asyncTaskExecutor.execute(() -> logSigninService.add(log));
    }

    /**
     * ??????
     *
     * @return ok
     */
    @PostMapping("signout")
    public Result<?> signout() {
        authenticator.signout();
        return Result.ok();
    }

    /**
     * ??????
     *
     * @return ok
     */
    @PostMapping("app/signout")
    public Result<?> appSignout() {
        authenticator.signout();
        return Result.ok();
    }

    /**
     * ??????token??????????????????????????????
     * ????????????????????????????????????{@link cn.tucci.sso.server.config.ShiroConfig}
     * ??????token??????????????????????????????????????????????????????????????????????????????
     *
     * @param token token
     * @return ????????????
     */
    @GetMapping("web/token")
    public Result<?> webTokenAuth(String token) {
        Long uid = authenticator.isWebSignin(token);
        if (uid != null) {
            return Result.ok();
        } else {
            throw new ServiceException(ResultStatus.UNAUTHENTICATED);
        }
    }

    /**
     * ???web/token???????????????app????????????
     *
     * @param token token
     * @return ????????????
     */
    @GetMapping("app/token")
    public Result<?> appTokenAuth(String token) {
        Long uid = authenticator.isAppSignin(token);
        if (uid != null) {
            return Result.ok();
        } else {
            throw new ServiceException(ResultStatus.UNAUTHENTICATED);
        }
    }
}
