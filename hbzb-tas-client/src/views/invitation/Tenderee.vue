<template>
    <div>
        <div class="container">

            <!-- 标题栏 -->
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">招标人管理</h3>

            <!-- 搜索功能栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="8">
                    <el-button type="primary" icon="el-icon-plus" @click="createTenderee()">新增单位</el-button>
                </el-col>
                <el-col :span="16">
                    <div style="text-align:right">
                        <el-input style="width:300px; margin-right:10px" v-model="search_word" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                        <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                        <el-button @click="reset">重置</el-button>
                    </div>
                </el-col>
            </el-row>

            <!-- 采购单位列表 -->
            <el-table ref="singleTable" highlight-current-row :data="tenderees" @current-change="selTenderer()">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                <el-table-column prop="name" label="单位名称"></el-table-column>
                <el-table-column prop="uniformCode" label="统一社会信用代码" align="center" width="180"></el-table-column>
                <el-table-column prop="contactPerson" label="联系人" align="center" width="120"></el-table-column>
                <el-table-column prop="contactNumber" label="联系电话" align="center" width="200"></el-table-column>
                <el-table-column prop="creatorName" label="创建人" align="center" width="100"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center" width="200"></el-table-column>
                <el-table-column label="操作" align="center" width="160">
                    <template slot-scope="scope">
                        <el-button type="primary" plain size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="100"></el-pagination>
            </div>
        </div>

        <!-- 新增/编辑单位对话框 -->
        <el-dialog title="新增/编辑单位" center width="60%" :visible.sync="dialogVisible">
            <div style="width:600px; margin:20px auto; padding:30px" v-loading="loading">
                <div class="form-box">
                    <el-form :model="form" :rules="rules" ref="form" label-width="140px">
                        <el-form-item label="单位名称" prop="name">
                            <el-input v-model="form.name" placeholder="请填写单位全称"></el-input>
                        </el-form-item>
                        <el-form-item label="统一社会信用代码" prop="uniformCode">
                            <el-input v-model="form.uniformCode" placeholder="请填写"></el-input>
                        </el-form-item>
                        <el-form-item label="联系人" prop="contactPerson">
                            <el-input v-model="form.contactPerson" placeholder="请填写"></el-input>
                        </el-form-item>
                        <el-form-item label="联系电话" prop="contactNumber">
                            <el-input v-model="form.contactNumber" placeholder="请填写"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" prop="memo">
                            <el-input type="textarea" rows="5" v-model="form.memo"></el-input>
                        </el-form-item>
                        <div style="width:100%; text-align:center">
                            <el-button style="width:170px; margin-right:5px" v-on:click="onReset">取消</el-button>
                            <el-button style="width:170px" v-on:click="onSave">保存</el-button>
                        </div>
                    </el-form>
                </div>
            </div>
        </el-dialog>

    </div>
</template>


<script type="text/javascript">
    export default {
        name: 'Tenderee',
        data() {
            return {
                form: {},
                rules: {
                    name: [{ required: true, message: '请填写', trigger: 'blur' }],
                    uniformCode: [{ required: true, message: '请填写', trigger: 'blur' }],
                },
                tenderees: [],
                dialogVisible: false
            }
        },
        created() {
            fetch('./data/tenderees.json').then(res => res.json()).then(data => {
                this.tenderees = data.tenderees;
            });
        },
        methods: {
            createTenderee() {
                this.dialogVisible = true;
            }
        }
    }
</script>

