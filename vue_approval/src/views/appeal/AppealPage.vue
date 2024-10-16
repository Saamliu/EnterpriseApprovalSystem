<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>项目申诉</span>
    </div>
    <div>
      <el-descriptions :column="2" border title="项目详情">
        <el-descriptions-item>
          <template slot="label">
            项目id
          </template>
          {{ project.id }}
        </el-descriptions-item>
        <el-descriptions-item label="附件">查看附件</el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            提交人id
          </template>
          {{ project.submitterId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            提交人姓名
          </template>
          {{ project.submitterName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            提交时间
          </template>
          {{ project.submitTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            审批人id
          </template>
          {{ project.approverId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            审批人姓名
          </template>
          {{ project.approverName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            审批时间
          </template>
          {{ project.approveTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            驳回原因
          </template>
          {{ project.rejectReason }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <el-form ref="form" :model="appeal" label-position="top" label-width="80px" style="margin-top: 30px">
      <el-form-item label="申诉原因">
        <el-input v-model="appeal.appealReason" :rows="5" type="textarea"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">提交申诉</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      // 当前正在审批的项目id
      id: null,
      // 当前审批的项目对象
      project: {},
      // 当前用户
      user: {},
      // 绑定申诉对象
      appeal: {},
    }
  },
  methods: {
    getProject() {
      this.$http.get('/project/detail', {params: {id: this.id}})
          .then((data) => {
            this.project = data
            console.log(this.project)
          })
    },
    save() {
      if (this.appeal.appealReason) {
        this.$confirm('是否确认提交申诉?', '确认提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          // 确认提交申诉
          this.$http.post('/project-appeal/appeal', {
            projectId: this.id,
            appealerId: this.user.id,
            appealReason: this.appeal.appealReason,
            submitTime: this.appeal.submitTime,
            approverId: this.appeal.approverId,
            approveTime: this.appeal.approveTime,
            rejectReason: this.appeal.rejectReason,
            status: 0,
          }).then(() => {
          })
        })
      }
    },
  },
  mounted() {
    this.id = this.$route.query.projectId;
    // 取出本地存储的用户信息字符串
    let str = localStorage.getItem('user');
    if (str) {
      // 将字符串转为对象
      this.user = JSON.parse(str);
    }
    console.log(this.id)
    this.getProject()
  }
}
</script>

<style scoped>

</style>