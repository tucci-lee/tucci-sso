<template>
  <div style="padding-top: 5%">
    <el-form v-if="verifyShow" :model="verifyData" :rules="verifyRules"
             ref="verifyForm"
             class="formBox">
      <h3>安全验证</h3>
      <el-form-item prop="phone" v-if="verifyData.type === 1">
        <el-input v-model="verifyData.phone" placeholder="手机"></el-input>
      </el-form-item>
      <el-form-item prop="email" v-if="verifyData.type === 2">
        <el-input v-model="verifyData.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="verifyData.captcha" placeholder="验证码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="sendCaptcha()" :disabled="captcha.disabled" style="width:80%;">
            {{ captcha.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="verify()">下一步</el-button>
      </el-form-item>
      <el-space>
        <el-link type="primary" v-if="verifyData.type === 2" @click="phoneVerify()">手机验证码重置</el-link>
        <el-link type="primary" v-if="verifyData.type === 1" @click="emailVerify()">邮箱验证码重置</el-link>
      </el-space>
    </el-form>

    <el-form v-if="editPasswordShow" :model="editPasswordData" :rules="editPasswordRules" ref="editPasswordForm"
             class="formBox">
      <h3>重置密码</h3>
      <el-form-item prop="password">
        <el-input v-model="editPasswordData.password" placeholder="新密码[长度6-30位]" type="password"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input v-model="editPasswordData.confirmPassword" placeholder="确认密码" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="editPassword()">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import url from "@/js/url";

export default {
  name: "Password",
  data() {
    /**
     * 校验器：校验两次密码是否相同
     */
    let checkPassword = (rule, value, callback) => {
      if (value !== this.editPasswordData.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    };
    return {
      signinHtml: url.html.signinHtml,
      verifyShow: true,     // 安全验证显示
      editPasswordShow: false, // 修改密码显示
      verifyData: {  // 验证数据
        type: 1,      // 验证类型，1-手机 2-邮箱
        email: null,    // 邮箱
        phone: null,    // 手机
        captcha: null,  // 验证码
      },
      editPasswordData: {  // 重置密码数据
        token: null,    // 校验的token
        password: null, // 密码
        confirmPassword: null, // 确认密码
      },
      captcha: {
        text: '发送验证码',    // 验证码按钮文字
        disabled: false,     // 按钮是否禁用
      },
      verifyRules: {  // 校验邮箱表单校验
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {type: 'email', message: '邮箱格式错误', trigger: 'blur'}],
        phone: [
          {required: true, message: '手机不能为空', trigger: 'blur'},
          {pattern: '1[0-9]{10}', message: '手机格式错误', trigger: 'blur'}],
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
      },
      editPasswordRules: {
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {pattern: '.{6,30}', message: '密码格式错误', trigger: 'blur'}
        ],
        confirmPassword: [{validator: checkPassword, trigger: 'blur'}],
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
     * 邮箱验证
     */
    emailVerify() {
      this.verifyData.type = 2;
      this.verifyData.phone = null;
    },
    /**
     * 手机验证
     */
    phoneVerify() {
      this.verifyData.type = 1;
      this.verifyData.email = null;
    },
    /**
     * 发送修改密码验证码
     */
    sendCaptcha() {
      if (this.verifyData.type === 2) {
        this.$refs['verifyForm'].validateField('email', msg => {
          if (msg) {
            return;
          }
          let data = {'email': this.verifyData.email, 'type': 2};
          this.$axios.post(url.urls.emailCaptcha, data)
              .then(res => {
                if (!res.status) {
                  return;
                }
                this.$message.success(res.msg);
                this.setInterval();
              })
        });
      }else {
        this.$refs['verifyForm'].validateField('phone', msg => {
          if (msg) {
            return;
          }
          let data = {'phone': this.verifyData.phone, 'type': 2};
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
     * 修改密码：验证码验证，获取修改密码后续的token
     */
    verify() {
      this.$refs['verifyForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.post(url.urls.editPasswordVerify, this.verifyData)
            .then(res => {
              if (!res.status) {
                return;
              }
              this.editPasswordData.token = res.data.token;
              this.verifyShow = false;
              this.editPasswordShow = true;
            })
      });
    },
    /**
     * 重置密码
     */
    editPassword() {
      this.$refs['editPasswordForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(url.urls.editPassword, this.editPasswordData)
            .then(res => {
              if (!res.status) {
                return;
              }
              window.location.href = this.signinHtml;
            })
      });
    }
  },
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