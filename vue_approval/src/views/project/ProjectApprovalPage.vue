<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>项目审批</span>
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
            审批状态
          </template>
          <el-tag v-if="project.status == 0" type="info">未审批</el-tag>
          <el-tag v-if="project.status == 1" type="success">已通过</el-tag>
          <el-tag v-if="project.status == 2" type="danger">已驳回</el-tag>
          <el-button v-if="project.status == 2 && user.roleId == 3" plain size="mini" style="margin-left: 10px"
                     @click="gotoAppealPage">申诉
          </el-button>
        </el-descriptions-item>
        <el-descriptions-item v-if="project.status != 0">
          <template slot="label">
            审批人id
          </template>
          {{ project.approverId }}
        </el-descriptions-item>
        <el-descriptions-item v-if="project.status != 0">
          <template slot="label">
            审批人姓名
          </template>
          {{ project.approverName }}
        </el-descriptions-item>
        <el-descriptions-item v-if="project.status != 0">
          <template slot="label">
            审批时间
          </template>
          {{ project.approveTime }}
        </el-descriptions-item>
        <el-descriptions-item v-if="project.status == 2">
          <template slot="label">
            驳回原因
          </template>
          {{ project.rejectReason }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <div v-if="project.status == 0 && user.roleId != 3" style="margin-top: 20px; text-align: center">
      <el-button plain type="success" @click="handleApproval(1)">通过</el-button>
      <el-button plain type="danger" @click="handleApproval(2)">驳回</el-button>
    </div>
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
    handleApproval(status) {
      switch (status) {
        case 1:
          // 通过审批，弹出一个确认框，在确认后发起post请求到后端更新项目状态
          this.$confirm('是否确认通过审批?', '确认提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            // 确认通过审批
            this.updateProject({
              id: this.project.id,
              approverId: this.user.id,
              approverName: this.user.realname,
              status: status,
            });
          }).catch()
          break;
        case 2:
          // 驳回审批，弹出一个输入框，让用户输入驳回原因后发起post请求到后端更新项目状态
          this.$prompt('请输入驳回原因', '确认提示', {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(({value}) => {
            // then的回调参数是用户输入的驳回原因
            this.updateProject({
              id: this.project.id,
              approverId: this.user.id,
              approverName: this.user.realname,
              status: status,
              // 驳回比通过多更新一个字段：驳回原因
              rejectReason: value,
            });
          }).catch()
          break;
      }
    },
    // 被处理审批的case1和case2调用
    updateProject(project) {
      this.$http.post('/project/approval', project)
          .then(() => {
            this.$message.success('项目审批成功')
            // 刷新当前项目详情
            this.getProject()
          })
    },
    gotoAppealPage() {
      // 跳转到申诉页面，需要接受当前项目id作为参数
      this.$router.push({path: '/appealPage', query: {projectId: this.project.id}})
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