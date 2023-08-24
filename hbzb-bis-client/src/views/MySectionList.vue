<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h2 style="margin-bottom:10px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">我的项目</h2>

            <!-- 搜索栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-dropdown split-button type="primary" size="medium" style="margin-right:20px">公告中
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>公告中</el-dropdown-item>
                            <el-dropdown-item>已截止</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="12" style="text-align:right">
                    <el-radio-group v-model="params.status" @change="radioChange" size="medium">
                        <el-radio-button label="工程"></el-radio-button>
                        <el-radio-button label="采购"></el-radio-button>
                    </el-radio-group>
                </el-col>
            </el-row>

            <!-- 项目列表 -->
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="serialNo" label="标段编号" align="center" width="200"></el-table-column>
                <el-table-column prop="name" label="标段名称" align="left"></el-table-column>
                <el-table-column prop="bidOpenTime" label="开标时间" align="center" width="160"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="180">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.status=='等待开标'" style="color:#409EFF">{{scope.row.status}}</strong>
                        <strong v-if="scope.row.status=='正在开标'" style="color:#F56C6C">{{scope.row.status}}</strong>
                        <strong v-if="scope.row.status=='评标准备'" style="color:#E6A23C">{{scope.row.status}}</strong>
                        <strong v-if="scope.row.status=='等待评标'" style="color:#67C23A">{{scope.row.status}}</strong>
                        <strong v-if="scope.row.status=='正在评标'" style="color:#F56C6C">{{scope.row.status}}</strong>
                        <strong v-if="scope.row.status=='已结束'" style="color:#333">{{scope.row.status}}</strong>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status=='等待开标'" type="primary" size="mini" @click="goOpenCenter(scope.row)">进入项目</el-button>
                        <el-button v-if="scope.row.status=='评标准备'" type="primary" size="mini" @click="goEvalPrepare(scope.row)">进入项目</el-button>
                        <el-button v-if="scope.row.status=='等待评标'" type="primary" size="mini" @click="goEvalCenter(scope.row)">进入项目</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params.size" :total="params.total" @current-change="pageChange"></el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'MySectionList',
        data() {
            return {
                params: { "keyword": "", "status": "工程", "page": 0, "size": 10, "total": 0 },
                tableData: [],
                loading: false,
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/bis/section/list?status=' + '&keyword=' + this.params.keyword + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
                    this.tableData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
                // this.loading = true;
                // this.$http.getJson('/mock/bidSections.json').then((data) => {
                //     this.bidSections = data.bidSections;
                //     this.loading = false;
                // }).catch(() => this.loading = false);
            },
            search() {
                this.params.page = 0;
                this.fetchData();
            },
            reset() {
                this.params = { "keyword": "", "status": "今日项目", "page": 0, "size": 10, "total": 0 };
                this.fetchData();
            },
            radioChange(val) {
                this.params.status = val;
                this.params.page = 0;
                this.fetchData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            },

            //进入开标中心
            goOpenCenter(row) {
                sessionStorage.setItem("section", JSON.stringify(row));
                this.$router.push('/openCenter');
            },

            // 进入评标准备
            goEvalPrepare(row) {
                sessionStorage.setItem("tenderSection", JSON.stringify(row));
                this.$router.push('/EvalPrepare');
            },

            // 进入评标中心
            goEvalCenter(row) {
                // 检查用户是否有权进入
                this.loading = true;
                this.$http.get('/tas/eval/member/detail?sectionUid=' + row.sectionUid).then((data) => {
                    sessionStorage.setItem("tenderSection", JSON.stringify(row));
                    this.loading = false;
                    this.$router.push('/EvalCenter');
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
