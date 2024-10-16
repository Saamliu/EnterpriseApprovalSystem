<template>
  <div class="bg">
    <div class="login-box">
      <div class="login-title">项目审批系统</div>
      <el-form ref="form" :model="user" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input placeholder="请输入账户名" v-model="user.username">
            <template slot="prepend">
              <div class="el-icon-user-solid"></div>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" v-model="user.password" @keyup.enter.native="submitForm('form')"  show-password>
            <template slot="prepend">
              <div class="el-icon-lock"></div>
            </template>
          </el-input>
        </el-form-item>
        <div style="display: flex; justify-content: space-between">
          <el-button type="primary" @click="submitForm('form')" >登录</el-button>
          <el-button @click="resetForm('form')">重置</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      // 绑定当前登录的用户
      user: {},
      // 绑定校验规则
      rules: {
        account: [
          { required:true, message:'请输入账户名', trigger:'blur'}
        ],
        password: [
          { required:true, message:'请输入密码', trigger:'blur'}
        ]
      }
    }
  },
  methods: {
    // 提交表单, 进行登录
    submitForm(formName) {
      // 先根据表单名找到表单, 进行校验, 得到校验结果valid
      this.$refs[formName].validate((valid) => {
        // 如果校验结果为true, 说明校验通过, 执行http-post登录请求
        if (valid) {
          this.$http.post('/user/login', this.user)
              .then((data) => {
                if (data) {
                  localStorage.setItem("user",JSON.stringify(data))
                  // 跳转到主页
                  this.$router.replace('/')
                  this.$message.success(`欢迎回来，${data.realname}`)
                }
              })
        }
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.bg {
  height: 100vh;
  background-color: whitesmoke;
}
.login-box {
  width: 550px;
  padding: 50px;
  background-color: #545c64;
  border-radius: 10px;
  box-shadow: 0 0 10px gray;
  margin: 0 auto;
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}
.login-form {
  width: 74%;
  margin: auto;
}
.login-title {
  padding-bottom: 30px;
  text-align: center;
  font-weight: 600;
  font-size: 20px;
  color: whitesmoke;
  cursor: pointer;
}
.login-identity {
  display: flex;
  justify-content: center;
  background-color: whitesmoke;
  padding: 10px;
  border-radius: 4px;
}
</style>