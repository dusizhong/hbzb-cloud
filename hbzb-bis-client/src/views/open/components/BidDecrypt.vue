<template>
    <div>

        <div class="container">
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="8">
                    <el-tooltip effect="dark" content="请点此解密" placement="top" :value="tooltip" :manual="true">
                        <el-button type="primary" @click="handleDecrypt" :disabled="decrypted" icon="el-icon-view">立即解密</el-button>
                    </el-tooltip>
                    <el-button type="primary" plain @click="handleNext">下一步</el-button>
                </el-col>
                <el-col :span="8" style="text-align:center; font-weight:bold">
                    <span v-if="status=='waiting'">解密倒计时<b style="font-size:24px; color:#E6A23C"> 未开始 </b></span>
                    <span v-if="status=='running'">解密倒计时<b style="font-size:24px; color:#E6A23C"> {{timer}} </b>秒</span>
                    <span v-if="status=='end'">解密倒计时<b style="font-size:24px; color:#E6A23C"> 已结束 </b></span>
                </el-col>
                <el-col :span="8" style="text-align:right">
                    
                </el-col>
            </el-row>
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column label="密码解密" align="center" width="120">
                    <template slot-scope="scope"><i class="el-icon-setting"></i></template>
                </el-table-column>
                <el-table-column prop="decryptTime" label="解密完成时间" align="center" width="160"></el-table-column>
                <el-table-column label="状态" align="center" width="90">
                    <template slot-scope="scope">
                        <span v-if="scope.row.decryptTime" style="color:#67C23A">已解密</span>
                        <span v-else style="color:#F56C6C">待解密</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="文件解密" center :show-close="false" :close-on-click-modal="false" :visible.sync="decryptDialog">
            <div style="margin-bottom:30px;">请插入CA锁，点击开始解密按钮。</div>
            <el-progress :text-inside="true" :stroke-width="24" :percentage="pt" status="success"></el-progress>
            <div style="margin-top:30px; text-align:center"><el-button type="primary" :loading="btnLoading" @click="confirmDecrypt">开始解密</el-button></div>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'BidDecrypt',
        props:{
            section: {},
            openRecord: null
        },
        data() {
            return {
                tableData: [],
                timer: 0,
                myTimer: null,
                tooltip: false,
                myTooltip: null,
                status: 'waiting',
                decrypted: false,
                decryptDialog: false,
                loading: false,
                btnLoading: false,
                pt: 0
            }
        },
        watch: {
            section(newValue, oldValue) {
                console.log('BidDecrypt watch section')
            },
            openRecord(newValue, oldValue) {
                console.log('BidDecrypt watch openRecord: ' + this.openRecord)
                if(this.openRecord.finishDecryptTime) {
                    clearInterval(this.myTimer);
                    this.status = 'end';
                }
            }
        },
        created() {
            this.fetchData();
            if(this.openRecord) {
                if(this.openRecord.startDecryptTime) {
                    let today = new Date();
                    let endDecryptTime = new Date(this.openRecord.endDecryptTime.replace("-", "/"));
                    if(today < endDecryptTime) {
                        this.status = 'running';
                        this.timer = Math.round((endDecryptTime.getTime() - today.getTime()) / 1000);
                        this.startTimer();
                        // show tooltip
                        if(!this.decrypted) {
                            this.myTooltip = setInterval(() => {
                                this.tooltip = true;
                                setTimeout(() => { this.tooltip = false }, 2000);
                            }, 3000);
                        }
                    } else this.status = 'end';
                }
            } else this.$message.warning('异常！开标记录不存在');
        },
        beforeDestroy () {
            clearInterval(this.myTooltip);
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/bid/record/detail?sectionUid=' + this.section.uid).then((data) => {
                    this.tableData.push(data);
                    if(data.decryptTime) {
                        this.decrypted = true;
                    }
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            startTimer() {
                this.myTimer = setInterval(() => { 
                    if(this.timer >0) {
                        this.timer--;
                    } else {
                        this.status = 'end';
                        clearInterval(this.myTooltip);
                    }
                }, 1000);
            },
            handleDecrypt() {
                if(this.status == 'waiting') {
                    this.$message.warning('解密未开始，请稍后再试！');
                } else if(this.status == 'running') {
                    this.decryptDialog = true;
                } else if(this.status == 'end') {
                    this.$message.warning('解密已结束！');
                }
            },
            confirmDecrypt() {
                this.btnLoading = true;
                let progress = setInterval(() => {
                    this.pt++;
                    if (this.pt > 99) {
                        let params = { "sectionUid": this.section.uid };
                        this.$http.post('/evs/bid/file/decrypt', params).then((data) => {
                            this.loading = false;
                            // this.$socket.send({"step": 1, "op": "BidDecrypt"});
                            this.decryptDialog = false;
                            this.decrypted = true;
                            this.tableData[0].decryptTime = data.decryptTime;
                            this.$message.success('解密成功！');
                        }).catch(() => this.loading = false);
                        this.btnLoading = false;
                        clearInterval(progress);
                        clearInterval(this.myTooltip);
                    }
                }, 50);
            },
            handleNext() {
                if(this.decrypted) {
                    this.$emit('handleStep', 2);
                } else {
                    this.$message.warning('文件未解密，不能继续！');
                }
                
            }
        }
    }
</script>
