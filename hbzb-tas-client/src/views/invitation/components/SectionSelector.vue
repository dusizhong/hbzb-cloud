<template>
    <div>
        <div class="container">
            <!-- 搜索栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
            </el-row>
            <!-- 项目列表 -->
            <el-table ref="singleTable" :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" highlight-current-row @row-click="rowClick" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="serialNo" label="标段编号" align="center" width="200"></el-table-column>
                <el-table-column prop="name" label="标段名称" align="left"></el-table-column>
                <el-table-column prop="tradeType" label="采购方式" align="center" width="160"></el-table-column>
                <el-table-column label="选择" align="center" width="80">
                    <template slot-scope="scope">
                        <el-radio v-model="selectedRadio" :label="scope.row.id">{{''}}</el-radio>
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
        name: 'SectionSelector',
        data() {
            return {
                params: { "keyword": "", "status": "APPROVAL", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                selectedRadio: '',
                loading: false
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/section/list?keyword=' + this.params.keyword + '&status=' + this.params.status + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
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
                this.params = { "keyword": "", "status": "APPROVAL", "page": 0, "size": 5, "total": 0 };
                this.fetchData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            },
            rowClick(row) {
                this.selectedRadio = row.id;
                //use $emit set hook to pass params to parent
                this.$emit('select', row);
            }
        }
    }
</script>
