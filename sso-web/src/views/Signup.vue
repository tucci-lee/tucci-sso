<template>
  <div style="padding-top: 5%">
    <el-form :model="signupData" :rules="signupRules" ref="signupForm" class="formBox">
      <h3>注册</h3>
      <el-form-item prop="username">
        <el-input v-model="signupData.username" placeholder="账号[开头必须字母且5-20位]"></el-input>
      </el-form-item>
      <el-form-item prop="phone" v-if="signupData.type === 1">
        <el-input v-model="signupData.phone" placeholder="手机"></el-input>
      </el-form-item>
      <el-form-item prop="email" v-if="signupData.type === 2">
        <el-input v-model="signupData.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="signupData.captcha" placeholder="验证码" @keyup.enter="signup()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="sendCaptcha()" :disabled="captcha.disabled" style="width:80%;">
            {{ captcha.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item prop="password">
        <el-input v-model="signupData.password" placeholder="密码[长度6-30位]" type="password"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input v-model="signupData.confirmPassword" placeholder="确认密码" type="password"
                  @keyup.enter="signup()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="signup()">注册</el-button>
      </el-form-item>
      <el-space>
        <el-link type="primary" v-if="signupData.type===1" @click="emailSignup()">邮箱注册</el-link>
        <el-link type="primary" v-if="signupData.type===2" @click="phoneSignup()">手机注册</el-link>
        <el-link type="primary" :href="signinHtml">登录</el-link>
      </el-space>
    </el-form>
  </div>
</template>

<script>
import url from "@/js/url";

export default {
  name: "signup",
  data() {
    /**
     * 校验器：校验两次密码是否相同
     */
    let checkPassword = (rule, value, callback) => {
      if (value !== this.signupData.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    };
    return {
      signinHtml: url.html.signinHtml,
      redirectUri: null,      // 重定向的uri
      signupData: {         // 登陆数据
        type: 1,    // 注册类型 1-手机号 2-邮箱
        username: null,        // 账号
        password: null,       // 密码
        confirmPassword: null,// 确认密码
        email: null,          // 邮箱
        phone: null,           // 手机
        captcha: null,        // 验证码
      },
      captcha: {
        text: '发送验证码',    // 验证码按钮文字
        disabled: false,     // 按钮是否禁用
      },
      signupRules: {        // 表单校验
        username: [
          {required: true, message: '账号不能为空', trigger: 'blur'},
          {pattern: '[a-zA-Z][\\w]{4,20}', message: '账号格式错误', trigger: 'blur'}],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {pattern: '.{6,30}', message: '密码格式错误', trigger: 'blur'}
        ],
        confirmPassword: [{validator: checkPassword, trigger: 'blur'}],
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {type: 'email', message: '邮箱格式错误', trigger: 'blur'}],
        phone: [
          {required: true, message: '手机不能为空', trigger: 'blur'},
          {pattern: '1[0-9]{10}', message: '手机格式错误', trigger: 'blur'}],
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
      }
    }
  },
  created() {
    let query = this.$route.fullPath.replace(this.$route.path, '');
    if (query) {
      this.signinHtml = url.html.signinHtml + query;
    }
  },
  methods: {
    /**
     * 切换为邮箱注册
     */
    emailSignup() {
      this.signupData.type = 2;
      this.signupData.phone = null;
    },
    /**
     * 切换为手机注册
     */
    phoneSignup() {
      this.signupData.type = 1;
      this.signupData.email = null;
    },
    /**
     * 发送验证码，根据当前选择邮箱和手机注册发送对应的验证码
     */
    sendCaptcha() {
      if (this.signupData.type === 2) {
        this.$refs['signupForm'].validateField('email', msg => {
          if (msg) {
            return;
          }
          let data = {'email': this.signupData.email, 'type': 1};
          this.$axios.post(url.urls.emailCaptcha, data)
              .then(res => {
                if (!res.status) {
                  return;
                }
                this.$message.success(res.msg);
                this.setInterval();
              })
        });
      } else {
        this.$refs['signupForm'].validateField('phone', msg => {
          if (msg) {
            return;
          }
          let data = {'phone': this.signupData.phone, 'type': 1};
          this.$axios.post(url.urls.phoneCaptcha, data)
              .then(res => {
                if (!res.status) {
                  return;
                }
                this.$message.success(res.msg);
                this.setInterval();
              })
        });
      }
    },
    setInterval() {
      this.captcha.disabled = true;
      let num = 60;
      let interval = setInterval(() => {
        if (num === 0) {
          this.captcha.text = "重新发送";
          this.captcha.disabled = false;
          clearInterval(interval);
          return;
        }
        this.captcha.text = num;
        num--;
      }, 1000);
    },
    /**
     * 登陆
     */
    signup() {
      // 校验
      this.$refs['signupForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 登陆请求
        this.$axios.post(url.urls.signup, this.signupData)
            .then(res => {
              if (res.status) {
                window.location.href = this.signinHtml;
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