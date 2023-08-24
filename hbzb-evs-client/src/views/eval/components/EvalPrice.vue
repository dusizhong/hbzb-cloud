<template>
    <div>

        <div class="container">
            <el-table :header-cell-style="{background:'#eef1f6',color:'#333'}" border :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="bidPrice" label="投标报价（元）" width="180" align="center"></el-table-column>
                <el-table-column prop="bidPrice" label="评审价格（元）" align="center" width="160">
                    <template slot-scope="scope">
                        <el-input :value="scope.bidPrice" size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column :prop="'col'+(index+1)" :label="col.item" v-for="(col, index) in tableCol" width="200" align="center">
                    <template slot="header" slot-scope="scope">
                        {{col.item}}<span v-if="col.unit">（{{col.unit}}）</span>
                    </template>
                </el-table-column>
                <el-table-column label="价格得分" width="120" align="center">
                    <template slot-scope="scope"><b>9.00</b></template>
                </el-table-column>
            </el-table>

            <div style="margin-top:30px" align="center">
                <el-button type="primary" style="width:170px" @click="showDialog">签章确认</el-button>
            </div>

        </div>

        <!-- 对话框 -->
        <el-dialog title="开标记录表" center width="80%" :visible.sync="visible">
            <!-- <Tinymce :init="tinymceConfig" v-model="report">公告内容</Tinymce> -->
            <el-input type="textarea" rows="20"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="visible=false">取消</el-button>
                <el-button type="primary" @click="onConfirm">确定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>

    import Tinymce from '../../../../public/tinymce/tinymce.min.js';

    export default {
        name: 'EvalPrice',
        components: { 'Tinymce': Tinymce },
        props:{
            tenderSection: {},
            report: '',
            tinymceConfig: {
                height: 400,
                // language_url: '/tinymce/zh_CN.js',
                language: 'zh_CN',
            }
        },
        data() {
            return {
                tableCol: [],
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
                this.$http.get('/tas/tender/sheet/list?sectionUid=' + this.tenderSection.sectionUid).then((data) => {
                    this.tableCol = data;
                    this.loading = false;
                    // fetch data
                    this.loading = true;
                    this.$http.get('/tas/bid/record/list?sectionUid=' + this.tenderSection.sectionUid).then((data) => {
                        this.tableData = data;
                        this.loading = false;
                    }).catch(() => this.loading = false);
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
