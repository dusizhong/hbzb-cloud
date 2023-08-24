<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h2 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">项目大厅</h2>

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
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="6" v-for="(data, index) in tableData">
                    <el-card shadow="hover" style="margin-bottom:20px; height:400px; font-size:15px">
                        <div><b>标段编号：</b>{{data.serialNo}}<span style="float:right">{{data.tradeType}}</span></div>
                        <div style="margin-top:20px; line-height:26px">{{data.name}}</div>
                        <div style="margin-top:20px; padding:10px; background:#F2F6FC"><b>招标文件金额：</b>¥500.00元</div>
                        <div style="margin-top:20px; padding:10px; background:#F2F6FC"><b>投标保证金：</b>¥200,000.00元</div>
                        <div style="margin-top:20px; padding:10px; background:#F2F6FC"><b>开标时间：</b>{{data.bidOpenTime}}</div>
                        <div style="margin-top:30px" align="center">
                            <el-button type="primary" plain>项目公告</el-button>
                            <el-button type="primary">收藏项目</el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>

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
                params: { "keyword": "", "status": "工程", "page": 0, "size": 8, "total": 0 },
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
                    console.log(data)
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
                this.params = { "keyword": "", "status": "今日项目", "page": 0, "size": 8, "total": 0 };
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
