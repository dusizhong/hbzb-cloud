<template>
    <div>

        <div class="container" v-loading="loading">
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                    <el-table-column prop="avatar" label="头像" align="center" width="60">
                        <template slot-scope="scope">
                            <img style="width:36px; height:36px; margin-right:10px" src="../../../assets/avatar.png">
                        </template>
                </el-table-column>
                <el-table-column prop="name" label="专家姓名" align="center" width="120"></el-table-column>
                    <el-table-column prop="profession" label="专业" align="center" width="200"></el-table-column>
                    <el-table-column prop="work" label="工作单位" align="left"></el-table-column>
                    <el-table-column prop="vote" label="得票数" align="center" width="160"></el-table-column>
                    <el-table-column prop="leader" label="评标组长" align="center" width="160">
                        <template slot-scope="scope">
                            <span v-if="scope.row.leader =='是'" style="color:#409EFF">是</span>
                            <span v-else style="color:#303133">否</span>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="!voted" label="操作" align="center" width="120">
                        <template slot-scope="scope">
                            <span v-if="voted" style="color:#303133">已推荐</span>
                            <el-button v-else type="primary" plain size="mini" @click="voteLeader(scope.$index)">推荐</el-button>
                        </template>
                    </el-table-column>
            </el-table>
            <div style="margin-top:30px" align="center">
                <el-button v-if="allVoted" type="primary" @click="confirmDialog" disabled>已推荐评标组长</el-button>
                <el-button v-else type="primary" @click="confirmDialog=true">确定组长</el-button>
            </div>
        </div>

        <!-- 确定对话框 -->
        <el-dialog title="确定组长" width="30%" :visible.sync="confirmDialog">
            <div>
                <div style="margin-bottom:20px;"><span style="font-size:18px; font-weight:bold"> 嘟嘟 </span>您获得了最高票<span style="font-size:22px; font-weight:bold"> 4 </span>票，确定当选评标组长吗？</div>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="revote()">重新推荐</el-button>
                <el-button type="primary" @click="confirmLeader()">确定当选</el-button>
            </div>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'EvalExpert',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                currentUser: {},
                tableData: [],
                voted: false,
                allVoted: false,
                confirmDialog: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            this.tenderSection = JSON.parse(sessionStorage.getItem("tenderSection"));
        },
        mounted() {
            this.fetchData();
        },
        watch: {
            tenderSection(oldValue, newValue) {
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/eval/member/list?sectionUid=' + this.tenderSection.sectionUid).then((data) => {
                    this.tableData = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            // 推荐评标组长
            voteLeader(index) {
                let member = this.tableData[index];
                member.vote++;
                this.tableData[index] = member;
                this.voted = true;
                this.$message.success('推荐成功');
            },
            revote() {
                this.voted = false;
                this.tableData = [];
                this.fetchData();
                this.confirmDialog = false;
            },
            // 选定评标组长
            confirmLeader(row) {
                let member = this.tableData[0];
                member.leader = '是';
                this.tableData[0] = member;
                this.confirmDialog = false;
                // this.loading = true;
                // this.$http.post('/tas/eval/member/update?leader=是&sectionUid=' + row.sectionUid + '&userUid=' + row.userUid).then((data) => {
                //     this.loading = false;
                //     this.fetchData();
                // }).catch(() => this.loading = false);
            },
        }
    }
</script>