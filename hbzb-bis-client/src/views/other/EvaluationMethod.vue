<template>
    <div>

        <div class="container">
            <h4 style="margin-bottom:20px; padding:20px; text-align:center; color:#303133; background: #F2F6FC">评标办法设置</h4>

            <el-collapse v-model="activeNames" @change="handleChange">

                <el-collapse-item title="评标办法" name="1">
                    <div style="font-size:18px"><b>全流程政府采购综合评分法</b></div>
                    <div>全流程政府采购综合评分法包括初步评审、详细评审及否决项全流程评审环节，适用于中高规模政府采购项目评标。</div>
                </el-collapse-item>

                <!-- 1、初步评审设置 -->
                <el-collapse-item title="1、初步评审设置" name="2">
                    <el-tabs v-model="activeTab" type="card">
                        <!-- 资格评审 -->
                        <el-tab-pane label="资格评审" name="first">
                            <!-- <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button> -->
                            <el-table style="width:100%; font-size:14px;" border :data="tableData" v-loading="loading">
                                <el-table-column>
                                    <template slot="header" slot-scope="scope">
                                        <el-button type="primary" size="mini" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button>
                                    </template>
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column label="操作" align="center" width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                        <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <!-- 符合性评审 -->
                        <el-tab-pane label="符合性评审" name="second">
                            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button>
                            <el-table style="width:100%; font-size:14px;" border :data="tableData" v-loading="loading">
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column label="操作" align="center" width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                        <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>
                </el-collapse-item>

                <!-- 2、详细评审设置 -->
                <el-collapse-item title="2、详细评审设置" name="3">
                    <el-tabs v-model="activeTab" type="card">
                        <!-- 报价 -->
                        <el-tab-pane label="报价" name="first">
                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button>
                                </el-col>
                                <el-col :span="12" align="right">总分值：40分</el-col>
                            </el-row>
                            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" strip border :data="tableData" v-loading="loading">
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column prop="minScore" label="最低分" align="center" width="80"></el-table-column>
                                <el-table-column prop="maxScore" label="最高分" align="center" width="80"></el-table-column>
                                <el-table-column label="操作" width="120" align="center">
                                    <template slot-scope="scope">
                                    <el-button type="text"size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                    <el-button type="text"size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <!-- 商务部分 -->
                        <el-tab-pane label="商务部分" name="second">
                            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button>
                            <el-table style="width:100%; font-size:14px;" strip border :data="tableData" v-loading="loading">
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column prop="minScore" label="最低分" align="center" width="80"></el-table-column>
                                <el-table-column prop="maxScore" label="最高分" align="center" width="80"></el-table-column>
                                <el-table-column label="操作" width="120" align="center">
                                    <template slot-scope="scope">
                                    <el-button type="text"size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                    <el-button type="text"size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                        <!-- 技术部分 -->
                        <el-tab-pane label="技术部分" name="third">
                            <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增评分点</el-button>
                            <el-table style="width:100%; font-size:14px;" strip border :data="tableData" v-loading="loading">
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="评分点名称" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="评审标准" align="center"></el-table-column>
                                <el-table-column prop="scoreType" label="打分方式" align="center" width="100"></el-table-column>
                                <el-table-column prop="minScore" label="最低分" align="center" width="80"></el-table-column>
                                <el-table-column prop="maxScore" label="最高分" align="center" width="80"></el-table-column>
                                <el-table-column label="操作" width="120" align="center">
                                    <template slot-scope="scope">
                                    <el-button type="text"size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                    <el-button type="text"size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>
                </el-collapse-item>

                <!-- 3、否决项 -->
                <el-collapse-item title="3、否决项" name="3">
                    <el-button type="primary" size="mini" style="margin-bottom:10px" @click="showEditor(scope.$index, scope.row)">新增否决项</el-button>
                            <el-table style="width:100%; font-size:14px;" border :data="tableData" v-loading="loading">
                                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                                <el-table-column prop="name" label="否决项" align="center" width="200"></el-table-column>
                                <el-table-column prop="content" label="说明" align="center"></el-table-column>
                                <el-table-column label="操作" align="center" width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                                        <el-button type="text" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                </el-collapse-item>

            </el-collapse>
        </div>

        <el-dialog title="新增评审参数设置" :visible.sync="visible">
            <div class="form-box" style="width:600px; margin:10px auto" v-loading="loading">
                <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                    <el-form-item label="序号" prop="sortId">
                        <el-input v-model="form.sortId"></el-input>
                    </el-form-item>
                    <el-form-item label="评分点名称" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="评审标准" prop="standards">
                        <el-input v-model="form.standards"></el-input>
                    </el-form-item>
                    <el-form-item label="打分方式" prop="type">
                        <el-select v-model="form.type" style="width:100%" placeholder="请选择">
                            <el-option label="自动打分" key="自动打分" value="自动打分"></el-option>
                            <el-option label="直接打分" key="直接打分" value="直接打分"></el-option>
                        </el-select>
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
                    <el-form-item label="评分查看地址" prop="redirectUrl">
                        <el-input v-model="form.redirectUrl"></el-input>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-switch v-model="form.enabled"></el-switch>
                    </el-form-item>
                    <div style="width:100%; text-align:center">
                        <el-button style="width:170px" type="primary" v-on:click="onSave">保存</el-button>
                    </div>
                </el-form>
            </div>
        </el-dialog>
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="onDelete">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'EvaluationMethod',
        data() {
            return {
                activeNames: ['1', '2'],
                activeTab: 'first',
                form: {},
                rules: {
                    type: [{ required: true, message: '请选择机构类型', trigger: 'change' }],
                    name: [{ required: true, message: '请填写', trigger: 'blur' }],
                    content: [{ required: true, message: '请填写', trigger: 'blur' }],
                    scoreType: [{ required: true, message: '请选择', trigger: 'change' }]
                },
                tableData: [],
                visible: false,
                delVisible: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            fetch('./data/evaluations.json').then(res => res.json()).then(data => {
                this.tableData = data.evaluations;
            })
        },
        methods: {
            handleChange(val) {
                console.log(val);
            },
            getData() {
                this.loading = true;
                this.$http.get('/guarantor/list').then((data) => {
                    this.tableData = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            onDelete(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            },
            showEditor(index, row) {
                this.form = {};
                if(row) {
                    this.form = row;
                    this.form.id = row.id;
                    this.form.area = row.area.split(',');
                }
                this.visible = true;
            },
            onReset() {
                this.form = {};
                this.$refs.form.resetFields();
            },
            onSave() {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let params = Object.assign({}, this.form);
                        params.area = params.area.toString();
                        console.log(this.form)
                        this.$http.post('/guarantor/create', params).then((data) => {
                            this.loading = false;
                            this.form = {};
                            this.$refs.form.resetFields();
                            this.getData();
                            this.visible = false;
                            this.$message.success('保存成功！');
                        }).catch(() => this.loading = false);
                    } else {
                        return false;
                    }
                })
            }
        }
    }
</script>
