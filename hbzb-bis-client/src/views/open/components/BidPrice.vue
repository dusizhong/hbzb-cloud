<template>
    <div>

        <div class="container">
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="bidPrice" label="投标报价（元）" align="center" width="250"></el-table-column>
                        <el-table-column prop="period" label="工期" align="center" width="160"></el-table-column>
                        <el-table-column prop="period" label="质量要求" align="center" width="160"></el-table-column>
                        <el-table-column prop="period" label="备注" align="center" width="300"></el-table-column>
                    </el-table>

            <div style="margin-top:30px" align="center">
                <el-button type="primary" style="width:170px" @click="showDialog">签章确认</el-button>
            </div>

        </div>

        <!-- 对话框 -->
        <el-dialog title="开标记录表" center width="80%" :visible.sync="visible">
            <el-input type="textarea" rows="20"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="visible=false">取消</el-button>
                <el-button type="primary" @click="onConfirm">确定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'BidPrice',
        props:{
            section: {}
        },
        data() {
            return {
                tableData: [],
                visible: false,
                loading: false,
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/bid/record/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableData = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            showDialog() {
                this.visible = true;
            },
            onConfirm() {
                this.visible = false;
                this.$message.success('Woo...签章成功！');
            }
        }
    }
</script>
