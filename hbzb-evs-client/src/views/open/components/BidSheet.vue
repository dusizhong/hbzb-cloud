<template>
    <div>

        <div class="container">
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-button v-if="!started" type="primary" icon="el-icon-caret-right" @click="startSing" :disabled="started">开启唱标</el-button>
                    <el-button v-if="started" :disabled="started">已开启唱标</el-button>
                    <el-button v-if="!sealed" type="primary" plain @click="showDialog">签章确认</el-button>
                    <el-button v-if="sealed" :disabled="sealed">已签章确认</el-button>
                    <el-button>导出唱标记录</el-button>
                </el-col>
                <el-col :span="12" style="text-align:right; font-weight:bold">
                    <span style="margin-left:30px">签章统计<b style="font-size:20px; color:#E6A23C"> {{counter}}/{{tableData.length}} </b></span>
                </el-col>
            </el-row>
            <el-table ref="singleTable" highlight-current-row :header-cell-style="{background:'#d3dce6',color:'#333'}" border :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="bidPrice" label="投标报价（元）" width="180" align="center"></el-table-column>
                <el-table-column :prop="'col'+(index+1)" :label="col.item" v-for="(col, index) in tableCol" width="200" align="center">
                    <template slot="header" slot-scope="scope">
                        {{col.item}}<span v-if="col.unit">（{{col.unit}}）</span>
                    </template>
                </el-table-column>
                <el-table-column label="签章确认" align="center" width="90">
                    <template slot-scope="scope">未签章</template>
                </el-table-column>
                <el-table-column label="异常情况" align="center" width="90">
                    <template slot-scope="scope"><i class="el-icon-edit memo" @click="showMemoDialog"></i></template>
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

        <!-- 异常情况 -->
        <el-dialog title="提示" :visible.sync="memoDialog" width="500px" center>
            <span><i class="el-icon-warning" style="margin-bottom:20px; color:#E6A23C"></i> 请填写异常情况记录</span>
            <el-input v-model="memo" type="textarea" :rows="2" placeholder="请填写"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="memoDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmMemo">确定保存</el-button>
            </span>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'BidSheet',
        props:{
            section: {}
        },
        data() {
            return {
                tableCol: [],
                tableData: [],
                decrypted: false,
                started: false,
                sealed: false,
                counter: 0,
                reload: null,
                currentRow: null,
                visible: false,
                memoDialog: false,
                memo: '',
                loading: false
            }
        },
        created() {
            this.init();
        },
        methods: {
            init() {
                this.loading = true;
                this.$http.get('/evs/open/record/detail?sectionUid=' + this.section.uid).then((data) => {
                    this.loading = false;
                    if(data) {
                        if(data.endDecryptTime) {
                            this.decrypted = true;
                            if(data.declareTime) {
                                this.started = true;
                                this.fetchData();
                            }
                            if(data.sealTime) {
                                this.sealed = true;
                            } else {
                                this.reload = setInterval(() => {
                                    console.log('reload tableData')
                                    this.fetchData();
                                }, 5000);
                            }
                        }
                    }
                }).catch(() => this.loading = false);
            },
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/tender/sheet/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableCol = data;
                    this.loading = false;
                    this.loading = true;
                    this.$http.get('/evs/bid/record/list?status=NORMAL&sectionUid=' + this.section.uid).then((data) => {
                        this.tableData = data;
                        this.counter = 0;
                        for(let d of data) {
                            if(d.decryptTime) this.counter++;
                        }
                        this.loading = false;
                    }).catch(() => this.loading = false);
                }).catch(() => this.loading = false);
            },
            startSing() {
                if(this.decrypted) {
                this.$confirm('确定开启唱标吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.loading = true;
                    let params = { "sectionUid": this.section.uid, "step": 2, "operate": "DECLARE" };
                    this.$http.post('/evs/open/record/update', params).then((data) => {
                        this.loading = false;
                        this.started = true;
                        this.$socket.send({"step": 2, "op": "DECLARE"});
                        this.$message.success('操作成功！');
                        // bid sing
                        for(let i=0; i<this.tableData.length; i++) {
                            setTimeout(() => {
                                this.$refs.singleTable.setCurrentRow(this.tableData[i]);
                            }, i*2000);
                        }
                    }).catch(() => this.loading = false);
                })
                } else this.$message.warning('解密尚未结束，不能操作！');
            },
            showDialog() {
                if(this.started) this.visible = true;
                else this.$message.warning('请先开启唱标！');
            },
            onConfirm() {
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "op": "seal" };
                this.$http.post('/evs/open/record/update', params).then((data) => {
                    this.loading = false;
                    this.sealed = true;
                    this.visible = false;
                    clearInterval(this.reload);
                    this.$socket.send({"step": 2, "op": "seal"});
                    this.$message.success('签章成功！');
                }).catch(() => this.loading = false);
            },
            showMemoDialog() {
                this.memoDialog = true;
            },
            confirmMemo() {
                this.memoDialog = false;
                this.$message.success('保存成功！');
            }
        }
    }
</script>


<style scoped>
.memo:hover {
    cursor: pointer;
}
</style>
