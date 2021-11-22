package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.core.constant.CacheConst;
import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.core.support.CacheOperator;
import cn.tucci.sso.server.model.body.EmailCaptchaBody;
import cn.tucci.sso.server.model.body.PhoneCaptchaBody;
import cn.tucci.sso.server.model.dto.ImgCaptchaDTO;
import cn.tucci.sso.server.service.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    private final DefaultKaptcha defaultKaptcha;
    private final CacheOperator cacheOperator;
    private final CaptchaService captchaService;

    public CaptchaController(DefaultKaptcha defaultKaptcha,
                             CacheOperator cacheOperator,
                             CaptchaService captchaService) {
        this.defaultKaptcha = defaultKaptcha;
        this.cacheOperator = cacheOperator;
        this.captchaService = captchaService;
    }

    /**
     * 生成验证码
     * <p>
     * 生成cid为缓存key，生成的验证码为缓存value存储
     * 登陆需要携带cid，根据cid获取验证码并校验验证码是否正确
     *
     * @return ImgCaptchaDTO
     */
    @GetMapping("img")
    public Result<?> img(HttpSession session) {
        // 获取验证码
        String captcha = defaultKaptcha.createText();
        // 生成图片验证码返回
        BufferedImage image = defaultKaptcha.createImage(captcha);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpeg", os);
            // 存储验证码
            String cacheKey = CacheConst.getCaptchaImgSigninKey(session.getId());
            cacheOperator.set(cacheKey, captcha, CacheConst.CAPTCHA_EXPIRY_TIME);

            // 生成返回信息，byte[]返回会自动base64编码
            ImgCaptchaDTO result = new ImgCaptchaDTO()
                    .setImg(os.toByteArray());
            return Result.ok(result);
        } catch (IOException e) {
            return Result.ok();
        }
    }

    /**
     * 发送邮箱验证码
     *
     * @param body 邮箱信息
     * @return ok
     */
    @PostMapping("email")
    public Result<?> email(@Validated @RequestBody EmailCaptchaBody body) {
        captchaService.sendEmailCaptcha(body.getType(), body.getEmail());
        return Result.ok();
    }

    /**
     * 发送手机验证码
     *
     * @param body 手机信息
     * @return ok
     */
    @PostMapping("phone")
    public Result<?> phone(@Validated @RequestBody PhoneCaptchaBody body) {
        captchaService.sendPhoneCaptcha(body.getType(), body.getPhone());
        return Result.ok();
    }
}
