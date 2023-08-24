<template>
    <div>

        <div class="container">
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="createBidFile()">新增信息</el-button>
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" border :data="tableData">
                <el-table-column prop="sortId" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="name" label="文件名称" align="center" width="200"></el-table-column>
                <el-table-column prop="note" label="说明" align="center"></el-table-column>
                <el-table-column prop="needed" label="是否必须" align="center" width="110"></el-table-column>
                <el-table-column prop="sealed" label="是否签章" align="center" width="110"></el-table-column>
                <el-table-column prop="template" label="模板" align="center" width="300"></el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" @click="showEditor(scope.row)">修改</el-button>
                        <el-button type="text" size="mini" @click="showDelDialog(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 新增投标文件组成项 -->
        <el-dialog title="新增投标文件组成项" center :visible.sync="editorDialog">
            <div class="form-box" style="width:90%; margin:10px auto" v-loading="loading">
                <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                    <el-form-item label="序号" prop="sortId">
                        <el-input v-model="form.sortId" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="文件名称" prop="name">
                        <el-input v-model="form.name" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="说明">
                        <el-input type="textarea" rows="5" v-model="form.note"></el-input>
                    </el-form-item>
                    <el-form-item label="是否必须" prop="needed">
                        <el-select v-model="form.needed" style="width:100%" placeholder="请选择">
                            <el-option label="是" key="是" value="是"></el-option>
                            <el-option label="否" key="否" value="否"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="是否签章" prop="sealed">
                        <el-select v-model="form.sealed" style="width:100%" placeholder="请选择">
                            <el-option label="是" key="是" value="是"></el-option>
                            <el-option label="否" key="否" value="否"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="文件模板">
                        <el-upload ref="upload" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" multiple :limit="3" :auto-upload="false">
                            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
  <div slot="tip" class="el-upload__tip">（只能上传docx/pdf文件，且不超过50M）</div>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div style="width:100%; margin-top:40px; text-align:center">
                    <el-button v-on:click="onCancel">取消</el-button>
                    <el-button type="primary" v-on:click="onSave">保存</el-button>
                </div>
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
        name: 'BidFile',
        props:{
            section: {}
        },
        data() {
            return {
                form: {},
                rules: {
                    sortId: [{ required: true, message: '请填写', trigger: 'blur' }],
                    name: [{ required: true, message: '请填写', trigger: 'blur' }],
                    needed: [{ required: true, message: '请选择', trigger: 'blur' }],
                    sealed: [{ required: true, message: '请选择', trigger: 'blur' }]
                },
                tableData: [],
                fileList: [],
                editorDialog: false,
                delDialog: false,
                loading: false,
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
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/tender/bidfile/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableData = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            createBidFile() {
                this.form = {};
                this.editorDialog = true;
            },
            showEditor(row) {
                this.form = row;
                this.editorDialog = true;
            },
            // 上传文件
            handlePreview(file) {
                console.log(file)
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            onCancel() {
                this.form = {};
                this.$refs.form.resetFields();
                this.editorDialog = false;
            },
            onSave() {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let params = Object.assign({}, this.form);
                        params.sectionUid = this.section.uid;
                        this.$http.post('/tas/tender/bidfile/create', params).then((data) => {
                            this.loading = false;
                            this.editorDialog = false;
                            this.$message.success('保存成功！');
                            this.$refs.form.resetFields();
                            this.fetchData();
                        }).catch(() => this.loading = false);
                    } else {
                        return false;
                    }
                })
            },
            showDelDialog(row) {
                this.idx = row.id;
                this.delDialog = true;
            },
            onDel() {
                this.loading = true;
                this.$http.get('/tas/tender/bidfile/del?id=' + this.idx).then((data) => {
                    this.loading = false;
                    this.delDialog = false;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>