<template>
    <div>

        <div class="container">
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">投标信息</h3>

            <!-- 标段信息 -->
            <el-card shadow="hover" style="margin:20px 0; font-size:18px; line-height:36px">
                <div>标段编号：<b>{{section.serialNo}}</b>（{{section.tradeType}}）</div>
                <div>标段名称：<b>{{section.name}}</b></div>
            </el-card>

            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
            </el-row>

            <!-- 投标记录 -->
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="contactName" label="联系人" align="center" width="100"></el-table-column>
                <el-table-column prop="contactPhone" label="联系电话" align="center" width="160"></el-table-column>
                <el-table-column prop="bidTime" label="投标文件上传时间" align="center" width="160"></el-table-column>
                <el-table-column prop="fee" label="已交工本费" align="center" width="160"></el-table-column>
                <el-table-column prop="guarantee" label="已交保证金" align="center" width="160"></el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params.size" :total="params.total" @current-change="pageChange"></el-pagination>
            </div>
        </div>


    </div>
</template>

<script>
    export default {
        name: 'BidRecord',
        data() {
            return {
                section: {},
                params: { "keyword": "", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                loading: false,
                idx: -1
            }
        },
        created() {
            this.section = JSON.parse(sessionStorage.getItem('section'));
            this.fetchData();
        },
        watch: {
            $route(to, from) {
                this.section = JSON.parse(sessionStorage.getItem('section'));
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/bid/record/list?sectionUid=' + this.section.uid + '&keyword=' + this.params.keyword + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
                    this.tableData = data;
                    // this.tableData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            search() {
                this.params.page = 0;
                this.fetchData();
            },
            reset() {
                this.params = { "keyword": "", "page": 0, "size": 5, "total": 0 };
                this.fetchData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            }
        }
    }
</script>
