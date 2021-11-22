<template>
  <el-container style="height: 100%">
    <el-header class="header">
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside width="250px">
        <Menu></Menu>
      </el-aside>
      <el-main style="background-color: #fafafa; height: 100%">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Menu from '@/components/Menu';

export default {
  name: "Index",
  components: {
    Header, Menu
  },
}
</script>

<style scoped>
.header {
  box-shadow: 0 2px 4px 0 #888888;
  z-index: 999;
}
</style>