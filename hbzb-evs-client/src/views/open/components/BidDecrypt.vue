<template>
    <div>

        <div class="container">
            <el-row :gutter="20" style="margin-bottom:20px; line-height:36px">
                <el-col :span="8">
                    <el-button type="primary" icon="el-icon-time" @click="handleStart" :disabled="started">开启解密</el-button>
                    <el-button v-if="decrypted" :disabled="true">招标人已解密</el-button>
                    <el-button v-else type="primary" plain @click="handleDecrypt">招标人解密</el-button>
                </el-col>
                <el-col :span="8" style="text-align:center; font-size:16px; font-weight:bold">
                    <span v-if="!decrypted">解密倒计时<b style="font-size:24px; color:#E6A23C"> {{timer}} </b>秒</span>
                    <span v-else>解密倒计时<b style="font-size:24px; color:#E6A23C"> 已结束</b></span>
                </el-col>
                <el-col :span="8" style="text-align:right; font-weight:bold">
                    <span style="margin-left:30px">解密统计<b style="font-size:20px; color:#E6A23C"> {{counter}}/{{tableData.length}} </b></span>
                </el-col>
            </el-row>
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left">
                    <template slot-scope="scope">{{scope.row.bidderName}} <el-tooltip effect="dark" content="密码解密" placement="top"><i class="el-icon-setting"></i></el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column prop="decryptTime" label="解密完成时间" align="center" width="160"></el-table-column>
                <el-table-column label="状态" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.decryptTime" style="color:#67C23A">已解密</span>
                        <span v-else>待解密</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="提示" :show-close="false" :close-on-click-modal="false" :visible.sync="startDialog" center>
            <div style="font-size:16px"><i class="el-icon-warning" style="color:#E6A23C"></i> 确定开启解密吗？开启后不能撤回！</div>
            <div style="margin-top:20px">设定解密时长：<el-input type="number" style="width:140px; margin-right:10px;" v-model="timer" placeholder="解密时长" prefix-icon="el-icon-time"></el-input>秒</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="startDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmStart">确定开启</el-button>
            </span>
        </el-dialog>

        <el-dialog title="招标人解密" center :show-close="false" :close-on-click-modal="false" :visible.sync="decryptDialog">
            <div style="margin-bottom:30px;">请插入CA锁，点击开始解密按钮。</div>
            <el-progress :text-inside="true" :stroke-width="24" :percentage="pt" status="success"></el-progress>
            <div style="margin-top:30px; text-align:center">
                <el-button @click="decryptDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmDecrypt" :loading="btnLoading">开始解密</el-button>
            </div>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'BidDecrypt',
        props:{
            section: {},
            openRecord: {}
        },
        data() {
            return {
                tableData: [],
                started: false,
                decrypted: false,
                timer: 0,
                myTimer: null,
                counter: 0,
                reloader: null,
                startDialog: false,
                decryptDialog: false,
                loading: false,
                btnLoading: false,
                pt: 0
            }
        },
        created() {
            if(JSON.stringify(this.openRecord) != '{}') {
                if(this.openRecord.startDecryptTime) {
                    this.started = true;
                    this.fetchData();
                    let today = new Date();
                    let endDecryptTime = new Date(this.openRecord.endDecryptTime.replace("-", "/"));
                    if(today < endDecryptTime) {
                        this.timer = Math.round((endDecryptTime.getTime() - today.getTime()) / 1000);
                        this.startTimer();
                        this.reloader = setInterval(() => {
                            console.log('reload tableData')
                            this.fetchData();
                        }, 5000);
                    } else this.timer = '已结束';
                }
                if(this.openRecord.finishDecryptTime) {
                    this.decrypted = true;
                    this.timer = '已结束';
                }
            }
        },
        beforeDestroy () {
            clearInterval(this.reloader);
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/bid/record/list?status=NORMAL&sectionUid=' + this.section.uid).then((data) => {
                    this.tableData = data;
                    this.counter = 0;
                    for(let d of data) {
                        if(d.decryptTime) this.counter++;
                    }
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            startTimer() {
                this.myTimer = setInterval(() => {
                    if(this.timer >0) this.timer--;
                    // else this.timer = '已结束';
                }, 1000);
            },
            handleStart() {
                if(this.openRecord.openTime) this.startDialog = true;
                else this.$message.warning('投标人尚未公布，不能开启解密!');
            },
            confirmStart() {
                if(this.timer < 1) {
                    this.$message.warning('请设置解密时长！');
                    return;
                }
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "op": "START_DECRYPT", "decryptPeriod": this.timer };
                this.$http.post('/evs/open/record/update', params).then((data) => {
                    this.loading = false;
                    this.started = true;
                    this.startDialog = false;
                    this.startTimer();
                    this.$socket.send({"step": 1, "op": "START_DECRYPT"});
                    this.fetchData();
                    this.$message.success('开启成功！');
                }).catch(() => this.loading = false);
            },
            handleDecrypt() {
                if(!this.started) {
                    this.$message.warning('解密未开启!');
                    return;
                }
                if(this.counter < this.tableData.length) {
                    this.$message.warning('投标人正在解密，请稍后再试!');
                    return;
                }
                this.decryptDialog = true;
            },
            confirmDecrypt() {
                this.btnLoading = true;
                let progress = setInterval(() => {
                    this.pt++;
                    if (this.pt > 99) {
                        clearInterval(progress);
                        let params = { "sectionUid": this.section.uid, "op": "FINISH_DECRYPT" };
                        this.$http.post('/evs/open/record/update', params).then((data) => {
                            this.decrypted = true;
                            this.$socket.send({"step": 1, "op": "FINISH_DECRYPT"});
                            clearInterval(this.myTimer);
                            clearInterval(this.reloader);
                            this.btnLoading = false;
                            this.decryptDialog = false;
                            this.$message.success('解密成功！');
                        }).catch(() => this.loading = false);
                    }
                }, 50); 
            }
        }
    }
</script>
