<template>
  <div>
    <el-card>
      <div slot="header" class="clearfix">
        <span>项目列表</span>
      </div>
      <el-form ref="searchForm" :inline="true">
        <el-form-item label="项目名称">
          <el-input v-model="queryParams.projectName" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态">
            <el-option :value="0" label="待审批"/>
            <el-option :value="1" label="审批通过"/>
            <el-option :value="2" label="审批驳回"/>
          </el-select>
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
        <el-table-column align="center" label="项目id" prop="id"/>
        <el-table-column align="center" label="项目名称" prop="projectName"/>
        <el-table-column align="center" label="项目描述" prop="projectDescription"/>
        <el-table-column align="center" label="状态" width="120px">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === 0" type="info">待审批</el-tag>
            <el-tag v-if="scope.row.status === 1" type="success">审批通过</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">审批驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="提交日期" prop="submitTime"/>
        <el-table-column align="center" label="查看详情">
          <template v-slot="scope">
            <el-button size="mini" type="primary" @click="gotoApprovalPage(scope.row.id)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-row style="margin-top: 20px">
        <el-col :span="6">
          <div>
            <el-button :disabled="ids.length === 0" icon="el-icon-delete" size="mini" type="danger"
                       @click="handleBatchDel">批量删除
            </el-button>
            <el-button :disabled="ids.length === 0" icon="el-icon-download" size="mini" type="primary"
                       @click="handleBatchExportExcel">批量导出Excel
            </el-button>
          </div>
        </el-col>
        <el-col :span="12">
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
    }
  },
  methods: {
    // 页面方法
    // 后端拿数据
    getTableData() {
      // 获取表格数据
      this.queryParams.current = this.current;
      this.queryParams.size = this.size;
      // 如果是get请求，数据需要放到config对象的字段
      // 如果是post请求，数据直接作为post的第二个参数
      this.$http.get("/project/page", {params: this.queryParams})
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
      this.queryParams.submitTime = null;
      this.queryParams.projectName = null;
      this.queryParams.status = null;
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
    handleBatchExportExcel() {
      let str = localStorage.getItem('user')
      let user = JSON.parse(str);
      let ids = encodeURIComponent(JSON.stringify(this.ids));
      this.$confirm('是否确认导出选中的记录为excel?', '确认提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 定义请求地址url, 后面拼接请求参数ids=ids数组用,拼接后的字符串
        // 超链接不能传数组, 只能将ids数组拼接为字符串进行传输
        let url = process.env.VUE_APP_API_BASE + 'project/export-excel?ids=' + ids + '&token=' + user.token;
        // 创建一个隐形的超链接
        let link = document.createElement('a');
        // 设置超链接的请求地址
        link.href = url;
        // 设置超链接用新页面打开（可写可不写）
        link.target = '_blank';
        // 模拟用户点击链接
        link.click();
        this.$message.success('导出成功');
        this.getTableData();
      }).catch(() => {
        this.$message.info('取消导出操作')
        this.getTableData()
      })
    },
    handleBatchDel() {
      this.$confirm('是否确认删除选中的数据?', '确认提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.$http.post('/project/batchDel', {ids: JSON.stringify(this.ids)})
            .then((data) => {
              this.getTableData()
              // 提示删除成功
              this.$message.success('删除成功')
            })
      }).catch(() => {
        this.$message.info('取消删除操作')
        this.getTableData()
      })
    },
    gotoApprovalPage(projectId) {
      // 跳转到项目审批页面，需要接受当前项目id作为参数
      this.$router.push({ path: '/project/approval', query: { projectId }})
    }

  },
  mounted() {
    // 页面加载完成后执行的代码
    this.getTableData();
  }
}
</script>

<style scoped>

</style>