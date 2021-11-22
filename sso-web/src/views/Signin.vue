<template>
  <div style="padding-top: 5%">
    <el-form :model="signinData" :rules="signinRules" ref="signinForm" class="formBox">
      <h3>登陆</h3>
      <el-form-item prop="username">
        <el-input v-model="signinData.username" placeholder="账号/邮箱/手机号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="signinData.password" placeholder="密码" type="password"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="signinData.captcha" placeholder="图形验证码"
                      @keyup.enter="signin()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <img :src="captchaImg" @click="refreshCaptcha()"/>
        </el-col>
      </el-row>
      <el-form-item style="text-align: left">
        <el-checkbox v-model="signinData.rememberMe">免登陆</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="signin()">登录</el-button>
      </el-form-item>
      <el-space>
        <el-link type="primary" :href="signupHtml">注册</el-link>
        <el-link type="primary" :href="passwordHtml" target="_blank">重置密码</el-link>
      </el-space>
    </el-form>
  </div>
</template>

<script>
import url from "../js/url";

export default {
  name: "Signin",
  data() {
    return {
      signupHtml: url.html.signupHtml,
      passwordHtml: url.html.passwordHtml,
      signinData: {         // 登陆数据
        username: null,       // 用户名
        password: null,       // 密码
        captcha: null,        // 验证码
        rememberMe: false,  // 是否记住我
        redirectUri: null,    // 登陆成功后重定向的uri
      },
      captchaImg: null,    // 验证码图片路径
      signinRules: {        // 表单校验
        username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
        password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.refreshCaptcha();
    let redirectUri = this.$route.query[url.redirectUri];
    let query = this.$route.fullPath.replace(this.$route.path, '');
    if (query) {
      this.signinData.redirectUri = redirectUri;
      this.signupHtml = url.html.signupHtml + query;
      this.passwordHtml = url.html.passwordHtml + query;
    }
  },
  methods: {
    /**
     * 刷新验证码图片
     */
    refreshCaptcha() {
      this.$axios.get(url.urls.imgCaptcha)
          .then(res => {
            if (res.status) {
              this.captchaImg = 'data:image/jpeg;base64,' + res.data.img;
            }
          })
    },
    /**
     * 登陆
     */
    signin() {
      // 校验
      this.$refs['signinForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 登陆请求
        this.$axios.post(url.urls.signin, this.signinData)
            .then(res => {
              if (res.status) {
                window.location.href = res.data.redirectUri;
              } else {
                this.refreshCaptcha();
              }
            })
      });
    }
  }
}
</script>

<style scoped>
.formBox {
  border-radius: 10px;
  margin: 0 auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
  text-align: center;
}
</style>