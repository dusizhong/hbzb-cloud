<template>
    <div>

        <div class="container" v-loading="loading">
            <!-- 评标委员会成员列表 -->
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showUserDialog()">添加成员</el-button>
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showUserDialog()">添加投标人代表</el-button>
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showUserDialog()">添加资格审查人</el-button>
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="randomMember()">随机抽取</el-button>
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="randomMember()">省专家库抽取?</el-button>
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" v-loading="loading" element-loading-text="随机抽取中...">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="role" label="角色" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.role=='ROLE_USER'">审查人员</span>
                        <span v-if="scope.row.role=='ROLE_AGENCY'">代理</span>
                        <span v-if="scope.row.role=='ROLE_TENDEREE'">招标人代表</span>
                        <span v-if="scope.row.role=='ROLE_EXPERT'">评标专家</span>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="姓名" align="center" width="100"></el-table-column>
                <el-table-column prop="phone" label="联系电话" align="center" width="160"></el-table-column>
                <el-table-column prop="idCardNo" label="身份证号" align="center" width="200"></el-table-column>
                <el-table-column prop="profession" label="专业类别" align="center" width="200"></el-table-column>
                <el-table-column prop="work" label="工作单位" align="left"></el-table-column>
                <!-- <el-table-column prop="tendereeDelegate" label="是否招标人代表" align="center" width="160">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.index"></el-checkbox>
                            </template>
                </el-table-column> -->
                <el-table-column prop="leader" label="指定评委负责人" align="center" width="160">
                        <template slot-scope="scope">
                            <i v-if="scope.row.leader =='是'" class="el-icon-success" style="color:#409EFF"></i>
                            <i v-else class="el-icon-circle-check-outline" style="color:#DCDFE6" @click="selLeader(scope.row)"></i>
                        </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" @click="showDelDialog(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 选择人员对话框 -->
        <el-dialog title="请选择人员" width="80%" :visible.sync="userDialog">
            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-plus" @click="newTender()">新增</el-button>
                </el-col>
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="8" style="text-align:right">
                    <el-radio-group v-model="params.role" @change="radioChange" size="medium">
                        <el-radio-button plain label="">全部</el-radio-button>
                        <el-radio-button label="招标人"></el-radio-button>
                        <el-radio-button label="资格审查员"></el-radio-button>
                        <el-radio-button label="评标专家"></el-radio-button>
                    </el-radio-group>
                </el-col>
            </el-row>
            <el-table ref="singleTable" :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="userData" v-loading="loading2" highlight-current-row @row-click="selSection">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="authorities" label="角色" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.authorities=='ROLE_USER'">审查人员</span>
                        <span v-if="scope.row.authorities=='ROLE_AGENCY'">代理</span>
                        <span v-if="scope.row.authorities=='ROLE_TENDEREE'">招标人代表</span>
                        <span v-if="scope.row.authorities=='ROLE_EXPERT'">评标专家</span>
                    </template>
                </el-table-column>
                <el-table-column prop="realName" label="姓名" align="center" width="100"></el-table-column>
                <el-table-column prop="phone" label="联系电话" align="center" width="160"></el-table-column>
                <el-table-column prop="profession" label="专业类别" align="center" width="200"></el-table-column>
                <el-table-column prop="work" label="工作单位" align="left"></el-table-column>
                <el-table-column label="选择" align="center" width="80">
                    <template slot-scope="scope">
                        <el-radio v-model="selectedRadio" :label="scope.row.id">{{''}}</el-radio>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :total="params.total" :page-size="params.size" @current-change="pageChange" background layout="prev, pager, next"></el-pagination>
            </div>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="userDialog=false">取消</el-button>
                <el-button type="primary" plain @click="addMember">确定</el-button>
            </div>
        </el-dialog>

        <!-- 删除对话框 -->
        <el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
           <span><i class="el-icon-warning" style="color:#E6A23C"></i> 确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取消</el-button>
                <el-button type="primary" @click="onDel">确定</el-button>
            </span>
        </el-dialog>

    </div>
</template>


<script>
    export default {
        name: 'EvalMember',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                params: { "keyword": "", "role": "", "page": 0, "size": 5, "total": 0 },
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

            // 获取用户数据
            fetchUserData() {
                this.loading = true;
                this.$http.get('/uaa/user/list?keyword=' + this.params.keyword + '&role=' + this.params.role + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
                    this.userData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },

            // 显示添加对话框
            showUserDialog() {
                // fetch('../data/bidJudges.json').then(res => res.json()).then(data => {
                //     this.userData = data.bidJudges;
                // });
                this.fetchUserData();
                this.userDialog = true;
            },
            search() {
                this.params.page = 0;
                this.fetchUserData();
            },
            reset() {
                this.params = { "keyword": "", "role": "", "page": 0, "size": 5, "total": 0 };
                this.fetchUserData();
            },
            radioChange(val) {
                this.params.role = val;
                this.params.page = 0;
                this.fetchUserData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchUserData();
            },

            selSection(row) {
                this.selectedRadio = row.id;
                this.selectedUser = row;
            },

            // 添加成员
            addMember() {
                this.loading = true;
                this.$http.post('/tas/eval/member/create?sectionUid=' + this.tenderSection.sectionUid + '&userUid=' + this.selectedUser.uid + '&name=' + this.selectedUser.realName + '&role=' + this.selectedUser.authorities + '&phone=' + this.selectedUser.phone).then((data) => {
                    this.loading = false;
                    this.fetchData();
                }).catch(() => this.loading = false);
                this.userDialog = false;
            },

            randomMember() {
                this.loading = true;
                setInterval(() => {
                    fetch('./data/bidJudges.json').then(res => res.json()).then(data => {
                        this.tableData = data.bidJudges;
                    });
                    this.loading = false;
                }, 2000)
            },

            // 删除成员
            showDelDialog(row) {
                this.idx = row.id;
                this.delDialog = true;
            },
            onDel() {
                this.loading = true;
                this.$http.get('/tas/eval/member/del?id=' + this.idx).then((data) => {
                    this.loading = false;
                    this.delDialog = false;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            },

            // 选定评标组长
            selLeader(row) {
                this.loading = true;
                this.$http.post('/tas/eval/member/update?leader=是&sectionUid=' + row.sectionUid + '&userUid=' + row.userUid).then((data) => {
                    this.loading = false;
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>