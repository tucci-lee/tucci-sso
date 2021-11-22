<template>
  <el-card style="text-align: left">
    <div>
      <b class="title">登陆密码</b>
      <br/>
      <el-space wrap>
        <span>登陆密码长度必须在6位以上</span>
        <el-link type="primary" :href="passwordHtml" target="_blank">重置</el-link>
      </el-space>
    </div>
    <el-divider></el-divider>
    <div>
      <b class="title">绑定手机</b>
      <br/>
      <el-space wrap>
        <span>您已绑定 [<el-link type="primary" @click="editPhoneShow = true">{{ accountData.phone }}</el-link>]</span>
        <el-link type="primary" @click="editPhoneShow = true">修改</el-link>
      </el-space>
    </div>
    <el-divider></el-divider>
    <div>
      <b class="title">绑定邮箱</b>
      <br/>
      <el-space wrap>
        <span>您已绑定 [<el-link type="primary" @click="editEmailShow = true">{{ accountData.email }}</el-link>]</span>
        <el-link type="primary" @click="editEmailShow = true">修改</el-link>
      </el-space>
    </div>
  </el-card>
  <EditEmail :show="editEmailShow" :email="accountData.email" :phone="accountData.phone"
             @close="editEmailShow = false"></EditEmail>
  <EditPhone :show="editPhoneShow" :email="accountData.email" :phone="accountData.phone"
             @close="editPhoneShow = false"></EditPhone>
</template>

<script>
import url from "@/js/url";
import {listenerAccount} from "@/js/mitt";

import EditEmail from "@/components/EditEmail";
import EditPhone from "@/components/EditPhone";

export default {
  name: "Account",
  components: {
    EditEmail, EditPhone
  },
  data() {
    return {
      editEmailShow: false, // 修改邮箱显示
      editPhoneShow: false, // 修改手机显示
      passwordHtml: url.html.passwordHtml,
      accountData: {  // 显示账号信息
        phone: null,
        email: null,
      },
    }
  },
  created() {
    this.getAccount();
    this.registerListener();
  },
  methods: {
    /**
     * 获取账号信息
     */
    getAccount() {
      this.$axios.get(url.urls.getAccount)
          .then(res => {
            if (!res.status) {
              return;
            }
            this.accountData = res.data;
          })
    },
    /**
     * 监听：
     * 监听修改邮箱/手机号重新加载账号信息
     */
    registerListener() {
      listenerAccount(() => {
        this.getAccount();
      });
    }
  }
}
</script>

<style scoped>
div b {
  font-size: 15px;
}

div a, span {
  color: rgb(153, 153, 153);
  font-size: 12px;
}
</style>