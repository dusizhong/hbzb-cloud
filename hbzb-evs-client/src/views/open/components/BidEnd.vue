<template>
    <div>

        <div class="container" v-loading="loading">
            <el-card shadow="hover" style="text-align:center;">
                <el-button v-show="showEndBtn" type="primary" style="width:170px" @click="showEndDialog">结束开标</el-button>
                <div v-show="showEndInfo">
                    <div style="font-size:24px; font-weight:bold; color:#E6A23C">开标已结束</div>
                    <div style="margin-top:20px; font-size:16px"><b>结束时间：</b>{{endTime}}</div>
                    <div style="margin-top:10px; margin-bottom:20px; font-size:16px"><b>操作人：</b>{{oper}}</div>
                    <el-tooltip effect="dark" content="重置开标" placement="top">
                        <el-button size="mini" icon="el-icon-arrow-up" circle @click="handleReOpen"></el-button>
                    </el-tooltip>
                </div>
            </el-card>
        </div>

        <!-- 开标结束 -->
        <el-dialog title="提示" :visible.sync="endDialog" width="500px" center>
            <div style="margin-bottom:20px; font-size:16px; font-weight:bold">确定要结束本次开标吗?</div>
              <el-radio-group v-model="radio" style="margin-bottom:20px">
                <el-radio label="FINISH">正常结束</el-radio>
                <el-radio label="ABORT">终止开标</el-radio>
            </el-radio-group>
            <el-input v-model="memo" type="textarea" :rows="2" placeholder="备注"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="endDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmEnd">确定</el-button>
            </span>
        </el-dialog>

        <!-- 重新开标 -->
        <el-dialog title="提示" :visible.sync="reOpenDialog" width="500px" center>
            <div style="margin-bottom:20px; font-size:16px; font-weight:bold; color:#F56C6C">确定要重置开标吗？重置后开标流程将重新开启！（投标人已完成解密的，无须再次解密）</div>
            <p style="margin-bottom:10px; font-weight:bold">重置原因：</p>
            <el-input v-model="reopenMemo" type="textarea" :rows="3" placeholder="请填写"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="reOpenDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmReOpen">确定重置</el-button>
            </span>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'BidEnd',
        props:{
            section: {}
        },
        data() {
            return {
                showEndBtn: true,
                showEndInfo: false,
                oper: '',
                endTime: '',
                endDialog: false,
                radio: 'FINISH',
                memo: '',
                reOpenDialog: false,
                reopenMemo: '',
                loading: false,
                visible: false
            }
        },
        created() {
            this.fetchOpenRecord();
        },
        methods: {
            fetchOpenRecord() {
                this.loading = true;
                this.$http.get('/evs/open/record/detail?sectionUid=' + this.section.uid).then((data) => {
                    this.loading = false;
                    if(data) {
                        if(data.endTime) {
                            this.oper = data.creatorName;
                            this.endTime = data.endTime;
                            this.showEndBtn = false;
                            this.showEndInfo = true;
                        }
                    }
                }).catch(() => this.loading = false);
            },
            showEndDialog() {
                this.endDialog = true;
            },
            confirmEnd() {
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "op": this.radio, "memo": this.memo };
                this.$http.post('/evs/open/record/update', params).then((data) => {
                    this.loading = false;
                    this.endDialog = false;
                    this.oper = data.creatorName;
                    this.endTime = data.endTime;
                    this.showEndBtn = false;
                    this.showEndInfo = true;
                    this.$socket.send({"step": 3, "op": this.radio});
                    this.$message.success('操作成功！');
                }).catch(() => this.loading = false);
            },
            handleReOpen() {
                this.reOpenDialog = true;
            },
            confirmReOpen() {
                if(!this.reopenMemo) {
                    this.$message.warning('请填写重置开标原因！');
                    return;
                }
                this.reOpenDialog = false;
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "op": "REOPEN", "reopenMemo": this.reopenMemo };
                this.$http.post('/evs/open/record/update', params).then((data) => {
                    this.loading = false;
                    this.$socket.send({"step": 0, "op": "REOPEN"});
                    this.$message.warning('重置成功，请重新开标！');
                    setTimeout(() => { window.location.reload() }, 2000);
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
