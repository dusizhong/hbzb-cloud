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
                                <el-table-column prop="name" label="评标专家" align="center" width="160"></el-table-column>
                                <el-table-column prop="catalog" label="专业类别" align="center" width="200"></el-table-column>
                                <el-table-column prop="workPlace" label="工作单位" align="left"></el-table-column>
                                <el-table-column prop="vote" label="得票数" align="center" width="160">
                                    <template slot-scope="scope">
                                        <span style="font-size:16px; margin-right:10px">{{scope.row.vote}}票</span>
                                        <el-button v-if="currentUser.recommended !='已推荐'" type="primary" plain size="mini">推荐</el-button>
                                        
                                    </template>
                                </el-table-column>
                                <el-table-column prop="recommended" label="推荐评标组长" align="center" width="160">
                                    <template slot-scope="scope">
                                        <span v-if="currentUser.recommended =='已推荐'" style="color:#303133">-</span>
                                        <el-button v-else type="primary" plain size="mini" @click="recommendLeader(scope.$index)">推荐</el-button>
                                        <!-- <el-switch v-model="value2" active-color="#13ce66" inactive-color="#ff4949"></el-switch> -->
                                    </template>
                                </el-table-column>
                                <el-table-column v-if="currentUser.role=='ADMIN'" prop="status" label="确认评委" align="center" width="160">
                                    <template slot-scope="scope">
                                        <span v-if="scope.row.status=='已确认'" style="color:#303133">已确认</span>
                                        <span v-else-if="scope.row.status=='已回避'" style="color:#F56C6C">已回避</span>
                                        <span v-else>
                                            <el-button type="primary" plain size="mini" @click="excludedDialog=true">确认</el-button>
                                            <el-button type="danger" plain size="mini" @click="excludedDialog=true">回避</el-button>
                                        </span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" align="center" width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" plain size="mini" @click="showEditor(scope.$index, scope.row)">指定为评标组长</el-button>
                                        <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">回避</el-button>
                                        <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
        </div>

        <!-- 确定回避人员对话框 -->
        <el-dialog title="确定回避人员" width="30%" :visible.sync="excludeDialog">
            <div>
                <div style="margin-bottom:20px;">您确定要将评标专家<span style="font-size:18px; font-weight:bold"> 欧阳锋 </span>设置为回避吗？被回避后将不能参与评标。</div>
                <el-input type="textarea" rows="3" placeholder="请填写回避理由" v-model="excludedMemo"></el-input>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="excludeDialog=false">取 消</el-button>
                <el-button type="primary" @click="excludeMember()">确 定</el-button>
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
                userData: [],
                selectedRadio: '',
                selectedUser: {},
                userDialog: false,
                editorDialog: false,
                delDialog: false,
                loading: false,
                loading2: false,
                idx: -1
            }
        },
        created() {
            
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
            // 获取成员数据
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/eval/member/list?sectionUid=' + this.tenderSection.sectionUid).then((data) => {
                    this.tableData = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            // 选定评标组长
            selLeader(row) {
                this.loading = true;
                this.$http.post('/tas/eval/member/update?leader=是&sectionUid=' + row.sectionUid + '&userUid=' + row.userUid).then((data) => {
                    this.loading = false;
                    this.fetchData();
                }).catch(() => this.loading = false);
            },
            // 确定参与人员，人员确定后才能进入项目
            confirmMember(index) {
                let member = this.tableData[index];
                member.status = 'CONFIRMED';
                this.tableData[index] = member;
            },
            // 回避操作对话框
            showExcludeDialog(index) {
                this.excludeDialog = true;
            },
            // 确定回避
            excludeMember(index) {
                let member = this.tableData[index];
                member.status = 'EXCLUDED';
                this.tableData[index] = member;
            },
            // 推荐评标组长
            recommendLeader(index) {
                let judge = this.tableData[index];
                judge.vote++;
                this.tableData[index] = judge;
                this.currentUser.recommended = '已推荐';
                console.log(this.tableData)
            }
        }
    }
</script>