<template>
  <div>
    <el-card class="box-card" style="width: 700px;margin: 0 auto">
      <div slot="header" class="clearfix">
        <span>添加项目</span>
      </div>
      <el-form ref="ruleForm" :model="formData" :rules="rules" label-position="top">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="formData.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="项目描述" prop="projectDescription">
          <el-input v-model="formData.projectDescription" placeholder="请输入项目详情"></el-input>
        </el-form-item>
        <el-form-item label="项目附件" prop="attachment">
          <el-button class="el-icon-upload" size="small" type="info">点击上传项目附件</el-button>
        </el-form-item>
        <!-- 分割线 -->
        <el-divider content-position="center"/>
        <!-- 提交与重置按钮 -->
        <el-form-item>
          <el-button type="primary" @click="submit('ruleForm')">提 交</el-button>
          <el-button @click="reset('ruleForm')">重 置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- 用于确认提交的dialog -->
    <el-dialog :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false"
               :visible.sync="dialogVisible"
               title="请确认信息"
               width="50%">
      <el-descriptions :column="1" border title="项目信息">
        <el-descriptions-item>
          <template slot="label">
            项目名称
          </template>
          {{ formData.projectName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            项目描述
          </template>
          {{ formData.projectDescription }}
        </el-descriptions-item>
      </el-descriptions>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        projectName: '',
        projectDescription: '',
        attachment: ''
      },
      dialogVisible: false,
      rules: {
        projectName: [
          {required: true, message: '请输入项目名称', trigger: 'blur'},
          {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
        ],
        projectDescription: [
          {required: true, message: '请输入项目描述', trigger: 'blur'},
          {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dialogVisible = true
        } else {
          return false
        }
      })
    },
    reset(formName) {
      this.$refs[formName].resetFields()
    },
    handleSubmit() {
      this.$http.post('/project/save', this.formData)
          .then(() => {
            this.$message.success('项目添加成功')
            this.dialogVisible = false
            this.reset('ruleForm')
          })
    },
  },

}
</script>

<style scoped>

</style>