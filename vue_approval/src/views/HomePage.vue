<template>
  <el-container>
    <el-aside width="250px">
      <el-menu style="height: 100vh" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <div style="display: flex; flex-direction: column; align-items: center;padding: 20px 0px">
          <el-upload :action="uploadAvatarAction" :data="user" with-credentials
                     :accept="accept" :show-file-list="false" :on-success="onSuccess" >
            <el-avatar :src="avatarSrc" style="width: 100px;height: 100px"></el-avatar>
          </el-upload>
          <div style="font-size: 16px;font-weight: bold; color: #409EFF;margin-top: 15px">
            欢迎{{ user.roleName }} : {{ user.realname }}
          </div>
        </div>
        <div style="display: flex; flex-direction: column; align-items: center;padding: 20px 0px">
          <el-button type="primary" @click="logout">退出登录</el-button>
        </div>
        <el-submenu index="0">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span>项目管理</span>
          </template>
          <el-menu-item index="/project/add">添加项目</el-menu-item>
          <el-menu-item index="/project/approval">项目审批</el-menu-item>
          <el-menu-item index="/project/page">项目查询</el-menu-item>
          <el-menu-item index="/appealPage">申诉管理</el-menu-item>
        </el-submenu>
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span>申诉管理</span>
          </template>
          <el-menu-item index="/appealList">查看申诉</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>


<script>
export default {
  data() {
    return {
      user:{},
      accept: 'image/*',
    }
  },
  computed: {
    uploadAvatarAction() {
      return process.env.VUE_APP_API_BASE + 'upload/avatar'
    },
    avatarSrc() {
      return process.env.VUE_APP_FILE_AVATAR_BASE + this.user.avatar
    }
  },
  methods: {
    onSuccess(response) {
      this.user.avatar = response.data
      localStorage.setItem('user', JSON.stringify(this.user))
    },
    logout() {
      this.$confirm('是否确定要退出系统?', '确认提示', {
        confirmButtonText: '确认',
        cancelButtonText:'取消',
        type:'warning'
      }).then(() => {
        this.$http.post("/user/logout")
            .then(() => {
              localStorage.removeItem('user')
              this.$router.replace('/login')
            })
      })
    }
  },
  mounted() {
    try{
      let str = localStorage.getItem('user')
      this.user = JSON.parse(str)
    }catch (e) {
      this.$router.replace("/login")
    }
  }
}
</script>


<style scoped>

</style>

