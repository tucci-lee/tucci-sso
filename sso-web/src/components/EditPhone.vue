<template>
  <el-dialog width="30%" :close-on-click-modal="false" v-model="diglogShow" @open="open()" @close="close()">
    <el-form v-show="verifyShow" :model="verifyData" :rules="verifyRules" ref="verifyForm" class="formBox">
      <h3 v-if="verifyData.type === 1">手机验证</h3>
      <h3 v-if="verifyData.type === 2">邮箱验证</h3>
      <el-form-item prop="phone" v-if="verifyData.type === 1">
        <el-input v-model="currentPhone" disabled></el-input>
      </el-form-item>
      <el-form-item prop="email" v-if="verifyData.type === 2">
        <el-input v-model="currentEmail" disabled></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="verifyData.captcha" placeholder="验证码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="sendVerifyCaptcha()" :disabled="verifyCaptcha.disabled" style="width:80%;">
            {{ verifyCaptcha.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="verify()">下一步</el-button>
      </el-form-item>
      <el-space>
        <el-link type="primary" v-if="verifyData.type === 2 && currentPhone" @click="phoneVerify()">手机验证</el-link>
        <el-link type="primary" v-if="verifyData.type === 1 && currentEmail" @click="emailVerify()">邮箱验证</el-link>
      </el-space>
    </el-form>

    <el-form v-show="editShow" :model="editData" :rules="editRules" ref="editForm" class="formBox">
      <h3>修改手机</h3>
      <el-form-item prop="email">
        <el-input v-model="editData.phone" placeholder="手机"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="editData.captcha" placeholder="验证码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-button type="primary" @click="sendEditCaptcha()" :disabled="editCaptcha.disabled" style="width:80%;">
            {{ editCaptcha.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" style="width:100%;" @click="edit()">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import url from "@/js/url";
import {getAccount} from "@/js/mitt";

export default {
  name: "EditPhone",
  props: {
    show: {
      type: Boolean,
      default: false,
    },
    email: String,
    phone: String
  },
  data() {
    return {
      diglogShow: this.show, // 弹出框显示
      verifyShow: true,     // 验证显示
      editShow: false,      // 修改显示
      currentEmail: null, // 显示当前绑定的邮箱
      currentPhone: null, // 显示当前绑定的手机
      verifyData: {  // 验证数据
        type: 1,      // 验证类型，1-手机 2-邮箱
        captcha: null,  // 验证码
      },
      editData: {  // 修改数据
        token: null,    // 校验的token
        phone: null,    // 手机
        captcha: null,  // 验证码
      },
      verifyCaptcha: {
        text: '发送验证码',    // 验证码按钮文字
        disabled: false,     // 按钮是否禁用
      },
      editCaptcha: {
        text: '发送验证码',    // 验证码按钮文字
        disabled: false,     // 按钮是否禁用
      },
      verifyRules: {  // 校验验证表单
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
      },
      editRules: { // 校验修改表单
        phone: [
          {required: true, message: '手机不能为空', trigger: 'blur'},
          {pattern: '1[0-9]{10}', message: '手机格式错误', trigger: 'blur'}],
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
      }
    }
  },
  methods: {
    /**
     * 打开dialog时，根据是否有手机选择是否显示手机验证
     */
    open() {
      if (this.currentPhone) {
        this.verifyData.type = 1;
      } else {
        this.verifyData.type = 2;
      }
    },
    /**
     * 关闭dialog时重置数据
     */
    close() {
      this.diglogShow = false;
      this.verifyShow = true;
      this.editShow = false;
    },
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
     * 发送修改手机验证码
     */
    sendVerifyCaptcha() {
      if (this.verifyData.type === 2) {
        this.$refs['verifyForm'].validateField('email', msg => {
          if (msg) {
            return;
          }
          let data = {'type': 5};
          this.$axios.post(url.urls.accountEmailCaptcha, data)
              .then(res => {
                if (!res.status) {
                  return;
                }
                this.$message.success(res.msg);
                this.setEditInterval();
              })
        });
      } else {
        this.$refs['verifyForm'].validateField('phone', msg => {
          if (msg) {
            return;
          }
          let data = {'type': 5};
          this.$axios.post(url.urls.accountPhoneCaptcha, data)
              .then(res => {
                if (!res.status) {
                  return;
                }
                this.$message.success(res.msg);
                this.setEditInterval();
              })
        });
      }
    },
    setEditInterval() {
      this.verifyCaptcha.disabled = true;
      let num = 60;
      let interval = setInterval(() => {
        if (num === 0) {
          this.verifyCaptcha.text = "重新发送";
          this.verifyCaptcha.disabled = false;
          clearInterval(interval);
          return;
        }
        this.verifyCaptcha.text = num;
        num--;
      }, 1000);
    },
    /**
     * 修改手机：验证码验证，获取修改手机后续的token
     */
    verify() {
      this.$refs['verifyForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.post(url.urls.editPhoneVerify, this.verifyData)
            .then(res => {
              if (!res.status) {
                return;
              }
              this.editData.token = res.data.token;
              this.verifyShow = false;
              this.editShow = true;
            })
      });
    },
    /**
     * 发送修改手机验证码
     */
    sendEditCaptcha() {
      this.$refs['editForm'].validateField('email', msg => {
        if (msg) {
          return;
        }
        let data = {phone: this.editData.phone, type: 6};
        this.$axios.post(url.urls.phoneCaptcha, data)
            .then(res => {
              if (!res.status) {
                return;
              }
              this.$message.success(res.msg);
              this.editCaptcha.disabled = true;
              let num = 60;
              let interval = setInterval(() => {
                if (num === 0) {
                  this.editCaptcha.text = "重新发送";
                  this.editCaptcha.disabled = false;
                  clearInterval(interval);
                  return;
                }
                this.editCaptcha.text = num;
                num--;
              }, 1000);
            })
      });
    },
    /**
     * 修改手机
     */
    edit() {
      this.$refs['editForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.$axios.put(url.urls.editPhone, this.editData)
            .then(res => {
              if (!res.status) {
                return;
              }
              this.$message.success(res.msg);
              this.close();
              // 广播重新加载账号信息
              getAccount();
            })
      });
    }
  },
  watch: {
    // 监控数据修改
    show(val) {
      this.diglogShow = val;
    },
    email(val) {
      this.currentEmail = val;
    },
    phone(val) {
      this.currentPhone = val;
    }
  }
}
</script>

<style scoped>
.formBox {
  padding: 0 35px 15px 35px;
}
</style>