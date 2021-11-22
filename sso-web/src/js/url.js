const host = 'http://localhost:10000';    // 请求地址,测试使用
const redirectUri = 'redirect_uri'; // 重定向参数

const html = {
    signinHtml: '/signin.html', // 登陆页面
    signupHtml: '/signup.html', // 注册页面
    profileHtml: '/profile.html',  // 用户资料页面
    accountHtml: '/account.html', // 账号信息页面
    passwordHtml: '/password.html',  // 修改密码页面
};

/** 系统请求的url */
const urls = {
    imgCaptcha: '/captcha/img', // 获取图片验证码
    signin: '/signin',  // 登录
    signup: '/signup',  // 注册
    signout: 'signout', // 登出
    emailCaptcha: '/captcha/email', // 发送邮箱验证码
    phoneCaptcha: '/captcha/phone',   // 发送手机验证码
    getProfile: '/profile', // 获取用户信息
    editAvatar: '/profile/avatar/edit',   // 修改头像
    editProfile: '/profile/edit',   // 修改用户信息
    getRegion: '/open/region',  // 获取地区信息
    getAccount: '/account', // 获取账号信息
    editPasswordVerify: '/account/password/edit/verify',  // 重置密码验证
    editPassword: '/account/password/edit', // 重置密码
    accountEmailCaptcha: '/account/captcha/email', // 发送邮箱验证码（不传入邮箱，后台根据当前用户获取）
    accountPhoneCaptcha: '/account/captcha/phone', // 发送手机验证码（不传入手机，后台根据当前用户获取）
    editEmailVerify: '/account/email/edit/verify',  // 修改邮箱验证
    editEmail: '/account/email/edit',   // 修改邮箱
    editPhoneVerify: '/account/phone/edit/verify',  // 修改手机验证
    editPhone: '/account/phone/edit',   // 修改手机
};

export default {
    host, redirectUri, urls, html
};