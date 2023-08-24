<template>
    <div>
        <!-- 签到、开标纪律 -->
        <transition name="el-zoom-in-center">
        <div v-show="!bidOpened" class="container">
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-button type="primary" icon="el-icon-news" @click="showOpenDialog">公布投标人</el-button>
                </el-col>
                <el-col :span="12" style="text-align:right; font-weight:bold">
                    <span style="margin-left:30px">签到统计<b style="font-size:20px; color:#E6A23C"> {{signinNum}}/{{tableData.length}} </b></span>
                </el-col>
            </el-row>
            <el-carousel height="300px">
                <el-carousel-item v-for="item in 4" :key="item">
                    <h1 style="line-height:300px; text-align:center; background-color:#d3dce6">公开 公平 公正{{ item }}</h1>
                </el-carousel-item>
            </el-carousel>
        </div>
        </transition>

        <!-- 公布投标人 -->
        <transition name="el-zoom-in-top">
        <div v-show="bidOpened" class="container">
            <el-row :gutter="20" style="margin-bottom:20px; line-height:36px">
                <el-col :span="24" style="font-size:16px; text-align:center">
                    <div style="font-weight:bold"> 共<b style="font-size:24px; color:#E6A23C"> {{tableData.length}} </b>家投标人，其中<b style="font-size:24px; color:#E6A23C"> {{bidNum}} </b>家已递交投标文件</div>
                </el-col>
            </el-row>
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="contactName" label="联系人" align="center" width="100"></el-table-column>
                <el-table-column prop="contactPhone" label="联系电话" align="center" width="120"></el-table-column>
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
                <el-table-column label="退回" align="center" width="80">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status!='REJECT'" size="mini" icon="el-icon-edit" circle @click="showRejectDialog(scope.$index)"></el-button>
                        <span v-else style="color:#F56C6C">已退回</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        </transition>

        <!-- 公布投标人对话框 -->
        <el-dialog title="提示" :visible.sync="openDialog" width="330px" center>
           <span><i class="el-icon-warning" style="margin-bottom:20px; color:#E6A23C"></i> 确定要公布投标人吗？公布后不能撤回！</span>
           <div style="margin-bottom:10px">监督人<el-input v-model="supervisor" placeholder="请填写"></el-input></div>
           <div>公证人<el-input v-model="notary" placeholder="请填写"></el-input></div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="openDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmOpen">确定</el-button>
            </span>
        </el-dialog>

        <!-- 退回对话框 -->
        <el-dialog title="提示" :visible.sync="rejectDialog" width="500px" center>
            <span><i class="el-icon-warning" style="margin-bottom:20px; color:#E6A23C"></i> 确定要退回此投标人吗？</span>
            <el-input v-model="rejectReason" type="textarea" :rows="2" placeholder="请填写退回原因"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="rejectDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmReject">确定退回</el-button>
            </span>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'BidOpen',
        props:{
            section: {},
            openRecord: {}
        },
        data() {
            return {
                tableData: [],
                bidOpened: false,
                bidNum: 0,
                signinNum: 0,
                supervisor: '',
                notary: '',
                openDialog: false,
                rejectDialog: false,
                rejectReason: '',
                loading: false,
                idx: -1
            }
        },
        created() {
            console.log('bid Open created')
            if(JSON.stringify(this.openRecord) != '{}') {
                this.bidOpened = true;
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/evs/bid/record/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableData = data;
                    this.bidNum = 0;
                    this.signinNum = 0;
                    for(let d of data) {
                        if(d.bidTime) this.bidNum++;
                        if(d.signinTime) this.signinNum++;
                    }
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            showOpenDialog() {
                let today = new Date();
                let bidOpenTime = new Date(this.section.bidOpenTime.replace("-", "/"));
                if(today < bidOpenTime) {
                    this.$message.warning('开标时间未到，不能公布投标人！'); 
                } else {
                    if(this.tableData.length < 3) this.$message.warning('投标人少于3家，不符合开标规定！');
                    this.openDialog = true;
                }
            },
            confirmOpen() {
                this.openDialog = false;
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "supervisor": this.supervisor, "notary": this.notary };
                this.$http.post('/evs/open/record/create', params).then((data) => {
                    this.loading = false;
                    this.bidOpened = true;
                    this.$socket.send({"step": 0, "op": "BID_OPEN"});
                    this.fetchData();
                    this.$message.success('公布成功！');
                }).catch(() => this.loading = false);
            },
            showRejectDialog(index) {
                this.idx = index;
                this.rejectDialog = true;
            },
            confirmReject() {
                this.rejectDialog = false;
                this.loading = true;
                let params = { "sectionUid": this.section.uid, "bidderUid": this.tableData[this.idx].bidderUid, "status": "REJECT", "memo": this.rejectReason };
                this.$http.post('/evs/bid/record/update', params).then((data) => {
                    this.loading = false;
                    this.$message.success('操作成功！');
                }).catch(() => this.loading = false);
                this.tableData[this.idx].status = 'REJECT';
            }
        }
    }
</script>
