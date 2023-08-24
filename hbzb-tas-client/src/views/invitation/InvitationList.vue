<template>
    <div>
        <div class="container">
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">投标邀请</h3>

            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-plus" @click="handleCreate()">新增邀请</el-button>
                </el-col>
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="params.keyword" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="8" style="text-align:right">
                    <el-radio-group v-model="params.status" @change="radioChange" size="medium">
                        <el-radio-button plain label="">全部</el-radio-button>
                        <el-radio-button label="编辑中"></el-radio-button>
                        <el-radio-button label="待审核"></el-radio-button>
                        <el-radio-button label="审核通过"></el-radio-button>
                        <el-radio-button label="审核不通过"></el-radio-button>
                    </el-radio-group>
                </el-col>
            </el-row>

            <!-- 列表 -->
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="title" label="邀请标段" align="left"></el-table-column>
                <el-table-column prop="sendTime" label="发出时间" align="center" width="160"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="80">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.status=='EDIT'" style="color:#409EFF">编辑中</strong>
                        <strong v-if="scope.row.status=='SUBMIT'" style="color:#E6A23C">待审核</strong>
                        <strong v-if="scope.row.status=='APPROVAL'" style="color:#67C23A">审核通过</strong>
                        <strong v-if="scope.row.status=='REJECT'" style="color:#F56C6C">审核不通过</strong>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button type="text" plain size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="text" size="mini" @click="handleDel(scope.row)">删除</el-button>
                        <el-button type="primary" size="mini" @click="handleEdit2(scope.row)">发出邀请函</el-button>
                        <el-button type="primary" size="mini" @click="handleEdit2(scope.row)">查看回执</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :total="params.total" :page-size="params.size" @current-change="pageChange" background layout="prev, pager, next"></el-pagination>
            </div>
        </div>

        <!-- 选择对话框 -->
        <el-dialog title="请选择标段" center width="70%" :close-on-click-modal="false" :visible.sync="selectorDialog">
            <SectionSelector @select="handleSelect"></SectionSelector>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="selectorDialog=false">取消</el-button>
                <el-button type="primary" @click="confirmSelect">确定</el-button>
            </div>
        </el-dialog>

        <!-- 删除对话框 -->
        <el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
           <span><i class="el-icon-warning" style="color:#E6A23C"></i> 确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取 消</el-button>
                <el-button type="primary" @click="confirmDel">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>


<script>
    import SectionSelector from './components/SectionSelector';
    export default {
        name: 'InvitationList',
        components: { SectionSelector },
        data() {
            return {
                params: { "keyword": "", "status": "", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                selectedSection: null,
                selectorDialog: false,
                delDialog: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/invitation/list?keyword=' + this.params.keyword + '&status=' + this.params.status + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
                    this.tableData = data.content;
                    this.params.total = data.totalElements;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            search() {
                this.params.page = 0;
                this.fetchData();
            },
            reset() {
                this.params = { "keyword": "", "status": "", "page": 0, "size": 5, "total": 0 };
                this.fetchData();
            },
            radioChange(val) {
                this.params.status = val;
                this.params.page = 0;
                this.fetchData();
            },
            pageChange(val) {
                this.params.page = val - 1;
                this.fetchData();
            },

            // 新增
            handleCreate() {
                this.selectorDialog = true;
            },
            handleSelect(row) {
                this.selectedSection = row;
            },
            confirmSelect() {
                if(!this.selectedSection) {
                    this.$message.warning('请选择标段');
                    return;
                }
                let invitation = { 'uid': '', 'sectionUids': this.selectedSection.uid };
                sessionStorage.setItem('invitation', JSON.stringify(invitation));
                this.selectorDialog = false;
                this.$router.push('/InvitationEditor');
            },

            // 编辑
            handleEdit(row) {
                sessionStorage.setItem('invitation', JSON.stringify(row));
                this.$router.push('/InvitationEditor');
            },
            // 编辑
            handleEdit2(row) {
                sessionStorage.setItem('invitation', JSON.stringify(row));
                this.$router.push('/InvitationEditor2');
            },

            // 删除
            handleDel(row) {
                this.idx = row.uid;
                this.delDialog = true;
            },
            confirmDel() {
                this.loading = true;
                let params = { "uid": this.idx };
                this.$http.post('/tas/invitation/del', params).then((data) => {
                    this.delDialog = false;
                    this.loading = false;
                    this.idx = -1;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
