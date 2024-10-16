<template>
  <div>
    <el-card>
      <div slot="header" class="clearfix">
        <span>申诉列表</span>
      </div>
      <el-form ref="searchForm" :inline="true">
        <el-form-item label="项目名称">
          <el-input v-model="queryParams.projectId" placeholder="请输入项目id"/>
        </el-form-item>
        <el-form-item label="添加日期">
          <el-date-picker
              v-model="queryParams.submitTime"
              placeholder="选择日期"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="search">查 询</el-button>
          <el-button @click="reset('searchForm')">重 置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe style="width: auto" @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection"/>
        <el-table-column align="center" label="序号" type="index"/>
        <el-table-column align="center" label="项目id" prop="projectId"/>
        <el-table-column align="center" label="申诉人id" prop="appealerId"/>
        <el-table-column align="center" label="申诉原因" prop="appealReason"/>
        <el-table-column align="center" label="提交日期" prop="submitTime"/>
        <el-table-column align="center" label="申诉驳回原因" prop="rejectReason"/>
        <el-table-column align="center" label="状态" width="120px">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === 0" type="info">待申诉</el-tag>
            <el-tag v-if="scope.row.status === 1" type="success">申诉已通过</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">申诉被驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="user.roleId !== 3" align="center" label="操作">
          <template v-slot="scope">
            <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="primary" @click="handlePass(scope.row)">
              通过
            </el-button>
            <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="danger" @click="handleReject(scope.row)">
              驳回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-row style="margin-top: 20px">
        <el-col :span="21">
          <el-pagination
              :current-page="current"
              :page-size="size"
              :page-sizes="[10, 20, 30, 40]"
              :total="total"
              background
              layout="total, sizes, prev, pager, next, jumper"
              style="text-align: center"
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
          />
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ProjectPage.vue",
  data() {
    return {
      // 定义页面所需全局变量
      tableData: [],
      // 查询参数列表
      queryParams: {},
      // 四个分页器的变量
      total: 0,
      pages: 0,
      current: 1,
      size: 10,
      ids: [],
      // 当前用户
      user: {},
      // 当前的申诉对象
      projectAppeal: {},
    }
  },
  methods: {
    // 页面方法
    handlePass(appeal) {
      this.$confirm('是否确认通过申诉?', '确认提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 确认通过申诉
        this.$http.post('/project-appeal/pass', {
          id: appeal.id,
          projectId: appeal.projectId
        })
            .then(() => {
              this.$message.success('申诉通过成功')
              // 刷新当前项目详情
              this.getTableData()
            })
      })
    },
    handleReject(appeal) {
      this.$prompt('请输入驳回原因', '确认提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(({value}) => {
        // then的回调参数是用户输入的驳回原因
        this.$http.post('/project-appeal/reject', {
          id: appeal.id,
          status: 2,
          rejectReason: value
        })
            .then(() => {
              this.$message.success('申诉驳回成功')
              this.getTableData()
            })
      })
    },
    // 后端拿数据
    getTableData() {
      // 获取表格数据
      this.queryParams.current = this.current;
      this.queryParams.size = this.size;
      this.user = JSON.parse(localStorage.getItem('user'))
      this.queryParams.appealerId = this.user.roleId;
      // 如果是get请求，数据需要放到config对象的字段
      // 如果是post请求，数据直接作为post的第二个参数
      this.$http.get("/project-appeal/page", {params: this.queryParams})
          .then((data) => {
            // 如果后端返回的数据不为空（防止后端返回的数据为null,抛出空指针异常）
            if (data != null) {
              // 拿后端返回的表格数据
              this.tableData = data.records;
              // 拿后端返回的总行数
              this.total = data.total;
              // 拿后端返回的总页数
              this.pages = data.pages;
            }
          })
    },
    search() {
      // 每次搜索都默认搜索第一页
      this.current = 1;
      this.size = 10;
      this.getTableData();
    },
    reset(formName) {
      this.$refs[formName].resetFields();
      this.queryParams.projectId = null;
      this.queryParams.submitTime = null;
      this.search();
    },
    handleCurrentChange(current) {
      this.current = current;
      this.getTableData();
    },
    handleSizeChange(size) {
      this.size = size;
      this.getTableData();
    },
    handleSelectionChange(val) {
      this.ids = [];
      // val是选中的对象数组，遍历val拿到选中的对象的id放到ids数组里面
      for (let i = 0; i < val.length; i++) {
        this.ids.push(val[i].id)
      }
      // console.log(this.ids);
    },

  },

  mounted() {
    // 页面加载完成后执行的代码
    this.getTableData();
  }
}
</script>

<style scoped>

</style>