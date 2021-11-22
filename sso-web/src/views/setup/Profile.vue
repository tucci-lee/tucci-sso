<template>
  <el-card>
    <el-row>
      <el-col :span="5" style="text-align: center">
        <img class="avatarDiv" :src="profileData.avatar"/>
        <div>
          <el-link type="primary" @click="editAvatarShow = true">修改</el-link>
        </div>
      </el-col>
      <el-col :span="10">
        <el-descriptions title="基本信息" :column="2">
          <el-descriptions-item label="账号ID:">{{ profileData.uid }}</el-descriptions-item>
          <el-descriptions-item label="创建时间:">{{ profileData.createTime }}</el-descriptions-item>
          <el-descriptions-item label="账号:">{{ profileData.username }}</el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
  </el-card>

  <el-dialog title="修改头像" width="30%" v-model="editAvatarShow">
    <el-upload
        :auto-upload="false"
        accept="image/*"
        ref="avatar"
        list-type="picture">
      <el-button size="small" type="primary">选取照片</el-button>
    </el-upload>
    <template #footer>
      <div>
        <el-button @click="closeEditAvatar()">取消</el-button>
        <el-button type="primary" @click="editAvatar()">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <el-card style="margin-top: 16px; text-align: left">
    <el-row>
      <el-col :span="6">
        <el-form ref="editProfileFrom" :model="profileData" :rules="editProfileRules" label-width="auto">
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="profileData.nickname"></el-input>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="profileData.birthday" type="date" :clearable="false" value-format="YYYY-MM-DD"
                            placeholder="选择日期"/>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="profileData.gender" :label="1">男</el-radio>
            <el-radio v-model="profileData.gender" :label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="地区" prop="region">
            <el-cascader
                v-model="profileData.region"
                :props="regionProps"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="个人介绍">
            <el-input type="textarea" v-model="profileData.introduction"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="editProfile()">保存</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import url from "@/js/url";
import {editAvatar} from "@/js/mitt";
import axios from 'axios';

export default {
  name: "Profile",
  data() {
    return {
      profileData: {
        uid: null,
        avatar: null,
        username: null,
        nickname: null,
        birthday: null,
        province: null,
        city: null,
        gender: null,
        introduction: null,
        createTime: null,
        region: null, // 地区联动帮定值
      },
      editAvatarShow: false,  // 修改头像的选择框是否显示
      regionProps: {   // 地区联动卡配置
        lazy: true,
        lazyLoad(node, resolve) {
          let parentId = node.level === 0 ? 0 : node.value;
          axios.get(url.urls.getRegion + "?parentId=" + parentId)
              .then(res => {
                let nodes = Array.from(res.data).map(item => ({
                  value: item.regionId,
                  label: item.name,
                  leaf: node.level === 1,
                }));
                resolve(nodes)
              })
        }
      },
      editProfileRules: {
        nickname: [
          {required: true, message: '昵称不能为空', trigger: 'blur'},
          {max: 20, message: '昵称最多20个字符', trigger: 'blur'},
        ],
        gender: [
          {required: true, message: '性别不能为空', trigger: 'blur'},
        ],
        region: [
          {required: true, message: '地区不能为空', trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    this.getProfile();
  },
  methods: {
    /**
     * 获取用户资料
     */
    getProfile() {
      this.$axios.get(url.urls.getProfile)
          .then(res => {
            if (!res.status) {
              return;
            }
            this.profileData = res.data;
            let createTime = res.data.createTime;
            this.profileData.createTime = this.$moment(parseInt(createTime)).format("YYYY-MM-DD HH:mm:ss");
            this.profileData.region = [res.data.province, res.data.city];
            // 广播头像
            editAvatar(res.data.avatar);
          });
    },
    /**
     * 修改头像
     * 不使用elementui的上传(主要是太麻烦)，使用axios的上传文件
     */
    editAvatar() {
      let avatar = this.$refs.avatar.uploadFiles[0].raw;
      let data = new FormData();
      data.append("file", avatar);

      this.$axios.put(url.urls.editAvatar, data)
          .then(res => {
            if (!res.status) {
              return;
            }
            this.$message.success(res.msg);
            // 关闭上传组件
            this.closeEditAvatar();
            // 更新头像信息
            this.profileData.avatar = res.data.avatar;
            // 广播头像信息
            editAvatar(res.data.avatar);
          })
    },
    /**
     * 关闭上传头像的dialog
     */
    closeEditAvatar() {
      this.editAvatarShow = false;
      // 清空文件列表
      this.$refs.avatar.uploadFiles = [];
    },
    /**
     * 修改用户信息
     */
    editProfile() {
      this.$refs['editProfileFrom'].validate(valid => {
        if (!valid) {
          return false;
        }
        this.profileData.province = this.profileData.region[0];
        this.profileData.city = this.profileData.region[1];
        this.$axios.put(url.urls.editProfile, this.profileData)
            .then(res => {
              if (res.status) {
                this.$message.success(res.msg);
              }
            });
      })
    },
  }
}
</script>

<style scoped>
.avatarDiv {
  border-radius: 50%;
  width: 130px;
  height: 130px;
}
</style>