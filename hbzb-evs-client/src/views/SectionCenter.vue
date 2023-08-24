<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h2 style="margin-bottom:10px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">项目大厅</h2>

            <!-- 电子时钟 -->
            <el-card shadow="hover" style="text-align:center;">
                <div style="font-size:16px; font-weight:bold; margin-bottom:10px;">{{clock}}</div>
                <div style="font-size:12px">国家授时中心</div>
            </el-card>

            <!-- 通知 -->
            <el-card shadow="hover" style="margin:16px auto; text-align:center;">
                <div>通知：请评标专家 <b>洪七公</b> 进入项目！ 09:25:00</div>
            </el-card>

            <!-- 搜索栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="12" style="text-align:right">
                    <el-radio-group v-model="params.status" @change="radioChange" size="medium">
                        <el-radio-button label="今日项目"></el-radio-button>
                        <el-radio-button label="往日项目"></el-radio-button>
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
        name: 'SectionCenter',
        data() {
            return {
                clock: '2020年',
                params: { "keyword": "", "status": "今日项目", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                loading: false,
            }
        },
        created() {
            this.startClock();
            this.fetchData();
        },
        methods: {
            startClock() {
                setInterval(() => {
                    let now = new Date();
                    let week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                    this.clock = now.getFullYear() + '年' + (now.getMonth()+1) + '月' + now.getDate() + '日 '+now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + ' ' + week[now.getDay()];
                }, 1000);
            },
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/section/list?status=' + '&keyword=' + this.params.keyword + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
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
                this.params = { "keyword": "", "status": "今日项目", "page": 0, "size": 5, "total": 0 };
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
                // this.$store.commit('setSection', row);
                this.$router.push('/OpenCenter');
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
