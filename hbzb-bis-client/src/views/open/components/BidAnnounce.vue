<template>
    <div>

        <div class="container">
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-button v-if="!sealed" type="primary" @click="showDialog">签章确认</el-button>
                    <el-button v-else :disabled="sealed">已签章确认</el-button>
                    <el-button type="primary" plain @click="handleNext">下一步</el-button>
                </el-col>
                <el-col :span="12" style="text-align:right; line-height:36px">
                    <div style="font-size:16px;color:#E6A23C">请签章确认</div>
                </el-col>
            </el-row>
            <el-table :header-cell-style="{background:'#eef1f6',color:'#333'}" border :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="bidPrice" label="投标报价（元）" width="180" align="center"></el-table-column>
                <el-table-column :prop="'col'+(index+1)" :label="col.item" v-for="(col, index) in tableCol" width="200" align="center">
                    <template slot="header" slot-scope="scope">
                        {{col.item}}<span v-if="col.unit">（{{col.unit}}）</span>
                    </template>
                </el-table-column>
            </el-table>
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
        name: 'BidAnnounce',
        props:{
            section: {},
            openRecord: null
        },
        data() {
            return {
                tableCol: [],
                tableData: [],
                sealed: false,
                visible: false,
                loading: false,
            }
        },
        created() {
            if(this.openRecord) {
                if(this.openRecord.startAnnounceTime) {
                    this.fetchData();
                }
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/tender/sheet/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableCol = data;
                    this.loading = false;
                    // fetch data
                    this.loading = true;
                    this.$http.get('/evs/bid/record/list?status=NORMAL&sectionUid=' + this.section.uid).then((data) => {
                        this.tableData = data;
                        let currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
                        for(let d of data) {
                            if(d.creatorUid == currentUser.uid) {
                                if(d.sealTime) this.sealed = true;
                            }
                        }
                        this.loading = false;
                    }).catch(() => this.loading = false);
                }).catch(() => this.loading = false);
            },
            showDialog() {
                this.visible = true;
            },
            onConfirm() {
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "operate": "SEAL" };
                this.$http.post('/evs/bid/record/update', params).then((data) => {
                    this.loading = false;
                    this.sealed = true;
                    this.visible = false;
                    this.$message.success('Woo...签章成功！');
                }).catch(() => this.loading = false);
            },
            handleNext() {
                if(this.sealed) {
                    this.$emit('handleStep', 3);
                } else {
                    this.$message.warning('请签章确认唱标结果！');
                }
                
            }
        }
    }
</script>
