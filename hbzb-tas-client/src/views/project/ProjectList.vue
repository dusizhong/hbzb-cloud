<template>
    <div>

        <div class="container">
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">项目管理</h3>

            <!-- 搜索栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-plus" @click="goEditor()">新增项目</el-button>
                </el-col>
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="8" style="text-align:right">
                    <el-radio-group v-model="params.status" size="medium" @change="radioGroupChange">
                        <el-radio-button plain label="">全部</el-radio-button>
                        <el-radio-button label="EDIT">编辑中</el-radio-button>
                        <el-radio-button label="SUBMIT">待审核</el-radio-button>
                        <el-radio-button label="APPROVAL">审核通过</el-radio-button>
                        <el-radio-button label="REJECT">审核不通过</el-radio-button>
                    </el-radio-group>
                </el-col>
            </el-row>

            <!-- 项目列表 -->
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="serialNo" label="项目编号" align="center" width="200">
                    <template slot-scope="scope">
                        <el-button @click="showDetail(scope.row)" type="text" size="small">{{ scope.row.serialNo }}</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="项目名称" align="left"></el-table-column>
                <el-table-column prop="tendereeName" label="采购单位" align="center" width="160"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="80">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.status=='EDIT'" style="color:#409EFF">编辑中</strong>
                        <strong v-if="scope.row.status=='SUBMIT'" style="color:#E6A23C">待审核</strong>
                        <strong v-if="scope.row.status=='APPROVAL'" style="color:#67C23A">审核通过</strong>
                        <strong v-if="scope.row.status=='REJECT'" style="color:#F56C6C">审核不通过</strong>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button type="primary" plain size="mini" @click="goEditor(scope.row)">编辑</el-button>
                        <el-button type="danger" size="mini" @click="showDelDialog(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params.size" :total="params.total" @current-change="pageChange"></el-pagination>
            </div>
        </div>

        <!-- 删除对话框 -->
        <el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
           <span><i class="el-icon-warning" style="color:#E6A23C"></i> 确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取 消</el-button>
                <el-button type="primary" @click="onDel">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'Project',
        data() {
            return {
                params: { "keyword": "", "status": "", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                loading: false,
                delDialog: false,
                delUid: -1
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/project/list?keyword=' + this.params.keyword + '&status=' + this.params.status + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
                    this.tableData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            search() {
                this.params.page = 0;
                this.fetchData();
            },
            reset() {
                this.params = { "keyword": "", "status": "", "page": 0, "size": 5, "total": 0 };
                this.fetchData();
            },
            radioGroupChange(val) {
                this.params.status = val;
                this.params.page = 0;
                this.fetchData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            },
            showDetail(index, row) {
                this.$router.push('/ProjectDetail');
            },
            goEditor(row) {
                sessionStorage.setItem("projectUid", null);
                if(row) sessionStorage.setItem("projectUid", row.uid);
                this.$router.push('/ProjectEditor');
            },
            showDelDialog(row) {
                this.delUid = row.uid;
                this.delDialog = true;
            },
            onDel() {
                this.loading = true;
                this.$http.get('/tas/project/del?projectUid=' + this.delUid).then((data) => {
                    this.loading = false;
                    this.delDialog = false;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
