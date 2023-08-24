<template>
    <div>
        <div class="container">
            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
            </el-row>
            <!-- 列表 -->
            <el-table ref="multipleTable" :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" @selection-change="selectionChange" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="serialNo" label="标段编号" align="center" width="200"></el-table-column>
                <el-table-column prop="name" label="标段名称" align="left"></el-table-column>
                <el-table-column prop="tradeType" label="采购方式" align="center" width="120"></el-table-column>
                <el-table-column type="selection" align="center" width="55"></el-table-column>
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
                selectedData: [],
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

            selectionChange(val) {
                // 检查所选标段是否同一项目
                if(val.length > 0) {
                    let projectUid = val[0].projectUid;
                    for(let v of val) {
                        if(v.projectUid != projectUid) {
                            this.$message.warning('所选标段不是同一项目!');
                            break;
                        }
                    }
                    this.$emit('select', val);
                }
            }
        }
    }
</script>
