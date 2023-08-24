<template>
    <div>

        <div class="container">
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">公告管理</h3>

            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-plus" @click="createAnnounce()">新增公告</el-button>
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
                <!-- <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-table :data="tableData" v-loading="loading">
                            <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                            <el-table-column prop="title" label="公告标题"></el-table-column>
                            <el-table-column prop="publishTime" label="发布时间" align="center" width="180"></el-table-column>
                            <el-table-column prop="status" label="状态" align="center" width="80">
                                <template slot-scope="scope">
                                    <strong v-if="scope.row.status=='EDIT'" style="color:#409EFF">编辑中</strong>
                                    <strong v-if="scope.row.status=='SUBMIT'" style="color:#E6A23C">待审核</strong>
                                    <strong v-if="scope.row.status=='APPROVAL'" style="color:#67C23A">审核通过</strong>
                                    <strong v-if="scope.row.status=='REJECT'" style="color:#F56C6C">审核不通过</strong>
                                </template>
                                
                            </el-table-column>
                            <el-table-column label="操作" align="center" width="180">
                                <template slot-scope="scope">
                                    <el-button type="text" plain size="mini" @click="goEditor(scope.$index, scope.row)">编辑</el-button>
                                    <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                    <el-button type="text" size="mini" @click="goModify(scope.$index, scope.row)">变更</el-button>
                                    <el-button type="primary" size="mini" @click="pushlish(scope.$index, scope.row)">发布</el-button>
                                    <el-button type="primary" size="mini" @click="pushlish(scope.$index, scope.row)">推送</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                </el-table-column> -->
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="title" label="公告标题"></el-table-column>
                <el-table-column prop="publishTime" label="发布时间" align="center" width="180"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="80">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.status=='EDIT'" style="color:#409EFF">编辑中</strong>
                        <strong v-if="scope.row.status=='SUBMIT'" style="color:#E6A23C">待审核</strong>
                        <strong v-if="scope.row.status=='APPROVAL'" style="color:#67C23A">审核通过</strong>
                        <strong v-if="scope.row.status=='REJECT'" style="color:#F56C6C">审核不通过</strong>
                        <strong v-if="scope.row.status=='CHANGE'" style="color:#F56C6C">已变更</strong>
                        <strong v-if="scope.row.status=='PUBLISH'" style="color:#67C23A">已发布</strong>
                        <strong v-if="scope.row.status=='STOP'" style="color:#F56C6C">已停止</strong>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" plain size="mini" @click="goEditor(scope.row)">编辑</el-button>
                        <el-button type="text" size="mini" @click="delAnnounce(scope.row)">删除</el-button>
                        <el-button type="text" size="mini" @click="goModify(scope.row)">变更</el-button>
                        <el-button type="text" size="mini" @click="stop(scope.$index, scope.row)">停止</el-button>
                        <el-button type="primary" size="mini" @click="publish(scope.$index, scope.row)">发布</el-button>
                        <el-button type="primary" size="mini" @click="publish(scope.$index, scope.row)">推送</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="prev, pager, next" :page-size="params.size" :total="params.total" @current-change="pageChange"></el-pagination>
            </div>
        </div>

        <!-- 选择标段对话框 -->
        <el-dialog title="请选择标段" center width="70%" :close-on-click-modal="false" v-if="sectionDialog" :visible.sync="sectionDialog">
            <SectionSelector @select="selectSection"></SectionSelector>
            <div style="margin-top:20px; text-align:center">
                <el-button @click="sectionDialog=false">取消</el-button>
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
        name: 'AnnounceList',
        components: { SectionSelector },
        data() {
            return {
                params: { "keyword": "", "status": "", "page": 0, "size": 5, "total": 0 },
                tableData: [],
                sectionDialog: false,
                delDialog: false,
                selectedSections: [],
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
                this.$http.get('/tas/announce/list?keyword=' + this.params.keyword + '&status=' + this.params.status + '&page=' + this.params.page + '&size=' + this.params.size).then((data) => {
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

            // 新增公告
            createAnnounce() {
                this.sectionDialog = true;
            },
            selectSection(val) {
                this.selectedSections = val;
            },
            confirmSelect() {
                if(this.selectedSections.length < 1) {
                    this.$message.warning('请选择标段');
                    return;
                } else {
                    // 检查所选标段是否同一项目
                    let same = true;
                    if(this.selectedSections.length > 0) {
                        let projectUid = this.selectedSections[0].projectUid;
                        for(let section of this.selectedSections) {
                            if(section.projectUid != projectUid) {
                                this.$message.warning('所选标段不是同一项目!');
                                same = false;
                                break;
                            }
                        }
                    }
                    if(!same) return;
                }
                let sectionUids = '';
                for(let s of this.selectedSections) {
                    if(!sectionUids) sectionUids = s.uid;
                    else sectionUids = sectionUids + ',' + s.uid;
                }
                let announce = { 'uid': '', 'sectionUids': sectionUids };
                sessionStorage.setItem("announce", JSON.stringify(announce));
                this.sectionDialog = false;
                this.$router.push('/AnnounceEditor');
            },

            // 编辑公告
            goEditor(index, row) {
                sessionStorage.setItem('announce', JSON.stringify(row));
                this.$router.push('/AnnounceEditor');
            },

            // 变更公告/答疑澄清
            goModify(index, row) {
                sessionStorage.setItem('announce', '');
                sessionStorage.setItem('orginAnnounce', JSON.stringify(row));
                this.$router.push('/AnnounceModify');
            },

            // 发布公告
            publish(index, row) {
                this.$confirm('确定要发布此公告吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.loading = true;
                    let params = { 'announceUid': row.uid, 'operate': '发布公告' };
                    this.$http.post('/tas/announce/publish', params).then((data) => {
                        this.loading = false;
                        this.fetchData();
                        this.$message.success('发布成功');
                    }).catch(() => this.loading = false);
                });
            },

            // 停止公告
            stop(index, row) {
                this.$confirm('确定要停止此公告吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({ type: 'success', message: '操作成功!' });
                });
            },

            // 删除公告
            delAnnounce(row) {
                this.idx = row.uid;
                this.delDialog = true;
            },
            confirmDel() {
                this.loading = true;
                let params = { "announceUid": this.idx };
                this.$http.post('/tas/announce/del', params).then((data) => {
                    this.delDialog = false;
                    this.loading = false;
                    this.idx = -1;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            },

        }
    }
</script>
