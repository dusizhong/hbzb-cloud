<template>
    <div>

        <div class="container">

            <!-- 评标方法 -->
            <div style="margin-bottom:30px">
                <span style="margin-right:10px; font-weight:bold">评标方法</span>
                <el-select v-model="evalMethod" change="changeMethod" placeholder="请选择">
                    <el-option label="综合评分法" key="综合评分法" value="综合评分法"></el-option>
                    <el-option label="最低评标价法" key="最低评标价法" value="最低评标价法"></el-option>
                </el-select>
                <el-button type="primary" style="margin-left:10px" @click="selectMethod">确定</el-button>
            </div>

            <!-- 评分标准 -->
            <el-tabs v-model="parentTab" type="card" v-if="visible">
                <el-tab-pane :label="parent.name" :name="index.toString()" v-for="(parent,index) in tableData">
                    <el-tabs v-model="childrenTab" type="border-card">
                        <el-tab-pane :label="child.name" :name="index.toString()" v-for="(child,index) in parent.children">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-button type="primary" size="mini" style="margin-bottom:10px" @click="createCriteria(child.code)">新增评分点</el-button>
                                </el-col>
                                <el-col :span="12" align="right" style="font-size:14px">总分值：40分</el-col>
                            </el-row>
                            <el-table :header-cell-style="{color:'#606266'}" border :data="child.data" v-loading="loading">
                                <el-table-column prop="sortId" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column prop="minScore" label="最低分" align="center" width="70"></el-table-column>
                                <el-table-column prop="maxScore" label="最高分" align="center" width="70"></el-table-column>
                                <el-table-column label="操作" align="center" width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="mini" @click="showEditor(scope.row)">修改</el-button>
                                        <el-button type="text" size="mini" @click="showDelDialog(scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>
                </el-tab-pane>
            </el-tabs>

        </div>

        <!-- 确定选择评标方法 -->
        <el-dialog title="提示" :visible.sync="confirmSelectDialog" width="300px" center>
            <div style="color:red">确定要更改评标方法吗？更改后原评标方法中已新增的评分点将被删除！</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="confirmSelectDialog = false">取消</el-button>
                <el-button type="primary" @click="confirmSelect">确定</el-button>
            </span>
        </el-dialog>

        <!-- 新增/修改评分标准 -->
        <el-dialog title="评分标准" center :visible.sync="editorDialog">
            <div class="form-box" style="width:600px; margin:10px auto" v-loading="loading">
                <el-form ref="form" :model="form" :rules="rules" label-width="130px">
                    <el-form-item label="序号" prop="sortId">
                        <el-input v-model="form.sortId"></el-input>
                    </el-form-item>
                    <el-form-item label="评分点名称" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="打分方式" prop="scoreType">
                        <el-select v-model="form.scoreType" style="width:100%" placeholder="请选择">
                            <el-option label="符合性打分" key="符合性打分" value="符合性打分"></el-option>
                            <el-option label="直接打分" key="直接打分" value="直接打分"></el-option>
                            <el-option label="自动打分" key="自动打分" value="自动打分"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="最低分" prop="minScore">
                        <el-input v-model="form.minScore"></el-input>
                    </el-form-item>
                    <el-form-item label="最高分" prop="maxScore">
                        <el-input v-model="form.maxScore"></el-input>
                    </el-form-item>
                    <el-form-item label="总分" prop="totalScore">
                        <el-input v-model="form.totalScore"></el-input>
                    </el-form-item>
                    <el-form-item label="基准值公式" prop="basicFormula">
                        <el-input v-model="form.basicFormula"></el-input>
                    </el-form-item>
                    <el-form-item label="扣分公式" prop="deductFormula">
                        <el-input v-model="form.deductFormula"></el-input>
                    </el-form-item>
                    <el-form-item label="评审标准说明" prop="content">
                        <el-input type="textarea" rows="4" v-model="form.content"></el-input>
                    </el-form-item>
                    <el-form-item label="评分查看地址" prop="toPage">
                        <el-input v-model="form.toPage"></el-input>
                    </el-form-item>
                    <div style="width:100%; text-align:center">
                        <el-button @click="editorDialog=false">取消</el-button>
                        <el-button type="primary" @click="onSave">保存</el-button>
                    </div>
                </el-form>
            </div>
        </el-dialog>

        <!-- 删除评审参数 -->
        <el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog = false">取消</el-button>
                <el-button type="primary" @click="onDel">确定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'EvalMethod',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                form: {},
                rules: {
                    sortId: [{ required: true, message: '请填写', trigger: 'blur' }],
                    name: [{ required: true, message: '请填写', trigger: 'blur' }],
                    scoreType: [{ required: true, message: '请选择', trigger: 'change' }]
                },
                evalMethod: '',
                confirmSelectDialog: false,
                parentTab: '0',
                childrenTab: '0',
                tableData: [],
                visible: false,
                editorDialog: false,
                delDialog: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            if(this.tenderSection.evalMethod) {
                this.evalMethod = this.tenderSection.evalMethod;
                this.fetchData();
            }
        },
        watch: {
            tenderSection(oldValue, newValue) {
                if(this.tenderSection.evalMethod) {
                    this.fetchData();
                }
            }
        },
        methods: {
            //获取数据
            fetchData() {
                this.loading = true;
                this.$http.get('/tas/eval/criteria/list?sectionUid=' + this.tenderSection.sectionUid + '&evalMethod=' + this.tenderSection.evalMethod).then((data) => {
                    this.tableData = data;
                    this.loading = false;
                    this.visible = true;
                }).catch(() => this.loading = false);
            },
            //初始化评标办法
            initMethod() {
                this.loading = true;
                this.$http.get('/tas/eval/criteria/init?sectionUid=' + this.tenderSection.sectionUid + '&evalMethod=' + this.evalMethod).then((data) => {
                    this.loading = false;
                    this.tenderSection.evalMethod = this.evalMethod; //更新tenderSection
                    sessionStorage.setItem('tenderSection', JSON.stringify(this.tenderSection));
                    this.fetchData();
                }).catch(() => this.loading = false);
            },
            // 选择评标方法
            changeMethod(val) {
                this.evalMethod = val;
            },
            selectMethod() {
                if(this.evalMethod) {
                    if(this.tenderSection.evalMethod) {
                        this.confirmSelectDialog = true;
                    } else {
                        this.initMethod();
                    }
                } else {
                    return;
                }
            },
            confirmSelect() {
                this.initMethod();
                this.confirmSelectDialog = false;
            },
            // 新增评分点
            createCriteria(parentCode) {
                if(!this.evalMethod) return;
                this.form = {};
                this.form.sectionUid = this.tenderSection.sectionUid;
                this.form.evalMethod = this.evalMethod;
                this.form.parentCode = parentCode;
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
                        this.$http.post('/tas/eval/criteria/create', params).then((data) => {
                            this.loading = false;
                            this.editorDialog = false;
                            this.$message.success('保存成功！');
                            this.fetchData();
                        }).catch(() => this.loading = false);
                    } else {
                        return false;
                    }
                })
            },
            // 删除评分点
            showDelDialog(row) {
                this.idx = row.id;
                this.delDialog = true;
            },
            onDel() {
                this.loading = true;
                this.$http.get('/tas/eval/criteria/del?id=' + this.idx).then((data) => {
                    this.loading = false;
                    this.delDialog = false;
                    this.$message.success('删除成功！');
                    this.fetchData();
                }).catch(() => this.loading = false);
            }
        }
    }
</script>
