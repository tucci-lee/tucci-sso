<template>
  <el-menu router style="border-right:0">
    <el-menu-item :index="profileHtml">
      <span>基本信息</span>
    </el-menu-item>
    <el-menu-item :index="accountHtml">
      <span>安全设置</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import url from "@/js/url";

export default {
  name: "Menu",
  data() {
    return {
      profileHtml: url.html.profileHtml,
      accountHtml: url.html.accountHtml,
    }
  }
}
</script>

<style scoped>

</style>