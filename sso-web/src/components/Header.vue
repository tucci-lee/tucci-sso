<template>
  <div class="left-option">
    <h3 style="color: #ff6a00">个人中心</h3>
  </div>
  <el-space size="large" class="right-option">
    <el-dropdown>
      <span>中文</span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>中文</el-dropdown-item>
          <el-dropdown-item>英文</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dropdown>
      <el-avatar size="medium" style="cursor: pointer;"
                 :src="avatar"></el-avatar>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="signout()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-space>
</template>

<script>
import {listenerAvatar} from "@/js/mitt";
import url from "@/js/url";

export default {
  name: "Header",
  data() {
    return {
      avatar: null,
    }
  },
  created() {
    this.registerListener();
    this.getProfile();
  },
  methods: {
    /**
     * 获取用户信息，设置头像
     */
    getProfile() {
      this.$axios.get(url.urls.getProfile)
          .then(res => {
            if (!res.status) {
              return;
            }

            this.avatar = res.data.avatar;
          });
    },
    /**
     * 注册监听器，监听头像的改变
     */
    registerListener() {
      listenerAvatar((data) => {
        this.avatar = data;
      });
    },
    /**
     * 登出
     */
    signout() {
      this.$axios.post(url.urls.signout)
          .then(res => {
            if(res.status){
              window.location.href = url.html.signinHtml;
            }
          })
    }
  }
}
</script>

<style scoped>
.left-option {
  height: 100%;
  display: flex;
  align-items: center;
  float: left;
}

.right-option {
  height: 100%;
  float: right;
}
</style>