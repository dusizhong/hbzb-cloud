<template>
    <div>

        <div class="container">
            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="addItem()">新增唱标项</el-button>
            <el-table :header-cell-style="{color:'#333'}" border :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="center"></el-table-column>
                <el-table-column prop="bidPrice" label="投标报价（元）" width="180" align="center"></el-table-column>
                <el-table-column :prop="(index+1).toString()" :label="col.item" v-for="(col, index) in tableCol" width="200" align="center">
                    <template slot="header" slot-scope="scope">
                        {{col.item}}<span v-if="col.unit">（{{col.unit}}）</span>
                        <el-tooltip style="margin-left:5px" effect="dark" content="删除" placement="top-start"><i class="el-icon-delete" @click="showDelDialog(col.id)"></i></el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 新增对话框 -->
        <el-dialog title="新增唱标项" center :visible.sync="editorDialog">
            <div class="form-box" style="width:90%; margin:10px auto" v-loading="loading">
                <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                    <el-form-item label="唱标项" prop="item">
                        <el-input v-model="form.item" type="datetime" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="数据类型" prop="type">
                        <el-select v-model="form.type" style="width:100%" placeholder="请选择">
                            <el-option label="文字" key="文字" value="文字"></el-option>
                            <el-option label="数字" key="数字" value="数字"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="单位" prop="unit">
                        <el-input v-model="form.unit"></el-input>
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input type="textarea" rows="5" v-model="form.memo"></el-input>
                    </el-form-item>
                    <div style="width:100%; margin-top:40px; text-align:center">
                        <el-button v-on:click="onCancel">取消</el-button>
                        <el-button type="primary" v-on:click="onSave">保存</el-button>
                    </div>
                </el-form>
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
        name: 'TenderSheet',
        props:{
            section: {}
        },
        data() {
            return {
                form: {},
                rules: {
                    item: [{ required: true, message: '请填写唱标项', trigger: 'blur' }],
                    type: [{ required: true, message: '请选择数据类型', trigger: 'change' }],
                },
                tableCol: [],
                tableData: [{ "bidderName": "-", "bidPrice": "-", "1": "-", "2": "-", "3": "-", "4": "-" }],
                editorDialog: false,
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
                this.tableCol = [];
                this.$http.get('/tas/tender/sheet/list?sectionUid=' + this.section.uid).then((data) => {
                    this.tableCol = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            addItem() {
                this.form = {};
                this.editorDialog = true;
            },
            showEditor(row) {
                this.form = row;
                this.editorDialog = true;
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
                        params.sortId = 0;
                        params.sectionUid = this.section.uid;
                        this.$http.post('/tas/tender/sheet/create', params).then((data) => {
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
            showDelDialog(id) {
                this.idx = id;
                this.delDialog = true;
            },
            onDel() {
                this.loading = true;
                this.$http.get('/tas/tender/sheet/del?id=' + this.idx).then((data) => {
                    this.loading = false;
                    this.delDialog = false;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
