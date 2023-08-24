<template>
    <div>
        <div class="container">
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="handleCreate()">添加邀请人</el-button>
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left"></el-table-column>
                <el-table-column prop="bidderUniformCode" label="统一社会信用代码" align="center" width="200"></el-table-column>
                <el-table-column prop="inviteUrl" label="邀请函" align="center" width="80">
                    <template slot-scope="scope">
                        <el-button v-if="!scope.row.inviteUrl" type="primary" size="mini" @click="handleSend(scope.row)">生成</el-button>
                        <i v-else class="el-icon-tickets" @click="handleSend(scope.row)"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="replyUrl" label="回执" align="center" width="60">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.replyUrl" type="text" @click="handleSend(scope.row)"><i class="el-icon-message"></i></el-button>
                        <span v-else>-</span>
                    </template>
                </el-table-column>
                <el-table-column prop="inviteTime" label="回执时间" align="center" width="120"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="70">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status=='待处理'">待处理</span>
                        <span v-if="scope.row.status=='已发出'" style="color:#67C23A">已发出</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right" align="center" width="60">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status!='已发出'" type="text" size="mini" @click="handleDel(scope.row)">移除</el-button>
                        <div v-else>-</div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params.size" :total="params.total" @current-change="pageChange"></el-pagination>
            </div>
        </div>

        <!-- 选择对话框 -->
        <el-dialog title="请选择投标人" center width="70%" :close-on-click-modal="false" :visible.sync="selectorDialog">
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params2.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search2">搜索</el-button>
                    <el-button @click="reset2">重置</el-button>
                </el-col>
            </el-row>
            <el-table ref="singleTable" :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="bidders" highlight-current-row @row-click="rowClick" v-loading="loading2">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="name" label="投标人名称" align="center" width="200"></el-table-column>
                <el-table-column prop="uniformCode" label="统一社会信用代码" align="left"></el-table-column>
                <el-table-column prop="contactName" label="联系人" align="center" width="160"></el-table-column>
                <el-table-column label="选择" align="center" width="80">
                    <template slot-scope="scope">
                        <el-radio v-model="selectedRadio" :label="scope.row.id">{{''}}</el-radio>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params2.size" :total="params2.total" @current-change="pageChange2"></el-pagination>
            </div>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="selectorDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmCreate">添加</el-button>
            </div>
        </el-dialog>

        <!-- 查看邀请函 -->
        <el-dialog title="查看邀请函" center width="70%" :close-on-click-modal="false" :visible.sync="inviteDialog">
            <div style="width:100%;height:600px">邀请函</div>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="inviteDialog=false">关闭</el-button>
                <el-button type="primary" @click="handleSend">发出</el-button>
            </div>
        </el-dialog>

        <!-- 查看回执 -->
        <el-dialog title="查看回执" center width="70%" :close-on-click-modal="false" :visible.sync="replyDialog">
            <div style="width:100%;height:600px">回执</div>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="replyDialog=false">关闭</el-button>
                <!-- <el-button type="primary" @click="confirmCreate">添加</el-button> -->
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'InviteRecord',
        props: { invitation:{} },
        data() {
            return {
                params: { "keyword": "", "status": "APPROVAL", "page": 0, "size": 5, "total": 0 },
                params2: { "role": "BIDDER", "status": "APPROVAL", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                bidders: [],
                selectorDialog: false,
                delDialog: false,
                selectedRadio: '',
                selectedRow: '',
                loading: false,
                loading2: false,
                idx: -1,
                inviteDialog: false,
                replyDialog: false
            }
        },
        created() {
            this.fetchData();
            this.fetchBidders();
        },
        watch: {
            // 与组件reload方法配合实现组件数据刷新
            invitation(oldValue, newValue) {
                console.log('watch reload')
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/invite/record/list?invitationUid=' + this.invitation.uid).then((data) => {
                    this.tableData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            fetchBidders() {
                this.loading2 = true;
                this.$http.get('/uaa/corp/list?role=' + this.params2.role + '&status=' + this.params2.status + '&page=' + this.params2.page + '&size=' + this.params2.size).then((data) => {
                    this.bidders = data.content;
                    this.params2.total = data.totalElements;
                    this.loading2 = false;
                }).catch(() => this.loading2 = false);
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            },
            search2() {
                this.params2.page = 0;
                this.fetchBidders();
            },
            reset2() {
                this.params2 = { "role": "BIDDER", "status": "APPROVAL", "page": 0, "size": 5, "total": 0 };
                this.fetchData();
            },
            pageChange2(val) {
                this.params2.page = val - 1;
                this.fetchBidders();
            },
            rowClick(row) {
                this.selectedRadio = row.id;
                this.selectedRow = row;
            },

            handleCreate() {
                if(!this.invitation.uid) {
                    this.$message.warning('请先保存！');
                    return;
                }
                this.selectorDialog = true;
            },
            confirmCreate() {
                if(!this.selectedRow) {
                    this.$message.warning('请选择要邀请的投标人');
                    return;
                }
                if(!this.invitation.uid) {
                    this.$message.warning('异常！未获取到邀请函uid');
                    return;
                }
                let params = {};
                params.invitationUid = this.invitation.uid;
                params.bidderUid = this.selectedRow.uid;
                params.bidderName = this.selectedRow.name;
                params.bidderUniformCode = this.selectedRow.uniformCode;
                this.loading = true;
                this.$http.post('/tas/invite/record/create', params).then((data) => {
                    this.loading = false;
                    // 与组件reload方法配合实现组件数据刷新
                    this.$emit('reload', 'reload');
                }).catch(() => this.loading = false);
            },

            handleDel(row) {
                this.$confirm('确定删除吗？', '提示', {
                    type: 'warning',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(() => {
                    this.loading = true;
                    let params = { "uid": this.idx };
                    this.$http.post('/tas/invite/record/del', row).then((data) => {
                        this.loading = false;
                        this.$message.success('删除成功！');
                        // 与组件reload方法配合实现组件数据刷新
                        this.$emit('reload', 'reload');
                    }).catch(() => this.loading2 = false);
                });
            },

            handleSend(row) {
                this.inviteDialog = true;
            }
        }
    }
</script>
