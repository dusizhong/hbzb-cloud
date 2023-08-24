<template>
    <div v-loading="loading">

        <div class="container">
            <el-row :gutter="20" style="margin-bottom:20px; line-height:36px">
                <el-col :span="8">
                    <el-button type="primary" plain @click="handleNext">下一步 <i class="el-icon-arrow-right"></i></el-button>
                </el-col>
                <el-col :span="16" style="font-size:16px">
                    <div style="margin-left:60px; font-weight:bold"> 共<b style="font-size:24px; color:#E6A23C"> {{tableData.length}} </b>家投标人，其中<b style="font-size:24px; color:#E6A23C"> {{counter}} </b>家已递交投标文件</div>
                </el-col>
            </el-row>
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="guarantee" label="投标保证金" align="center" width="120">
                    <template slot-scope="scope">
                        <span v-if="scope.row.guarantee =='是'" style="color:#67C23A">已缴纳</span>
                        <span v-else style="color:#F56C6C">未缴纳</span>
                    </template>
                </el-table-column>
                <el-table-column prop="bidTime" label="投标文件递交时间" align="center" width="160"></el-table-column>
                <el-table-column prop="signinTime" label="是否签到" align="center" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.signinTime" class="el-icon-success" style="color:#67C23A"></i>
                        <i v-else class="el-icon-error" style="color:#F56C6C"></i>
                    </template>
                </el-table-column>
            </el-table>
        </div>

    </div>
</template>


<script>
    export default {
        name: 'BidOpen',
        props:{
            section: {},
            openRecord: null
        },
        data() {
            return {
                tableData: [],
                counter: 0,
                loading: false
            }
        },
        created() {
            if(this.openRecord) {
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/bid/record/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableData = data;
                    this.counter = 0;
                    for(let d of data) {
                        if(d.bidTime) this.counter++;
                    }
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            handleNext() {
                this.$emit('handleStep', 1);
            }
        }
    }
</script>
