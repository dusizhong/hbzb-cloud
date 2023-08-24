<template>
    <div>
        <div class="container">

            <!-- 标题 -->
            <h3 style="margin-bottom:20px; padding:20px 10px; text-align:center; color:#303133; background: #F2F6FC">专家管理</h3>

            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-plus" @click="showExpertDialog()">新增专家</el-button>
                </el-col>
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="search_word" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-col>
                <el-col :span="8" style="text-align:right">
                </el-col>
            </el-row>

            <!-- 专家列表 -->
            <el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="experts" v-loading="loading">
                <el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
                    <el-table-column prop="avatar" label="头像" align="center" width="60">
                        <template slot-scope="scope">
                            <img style="width:36px; height:36px; margin-right:10px" src="../../assets/avatar.png">
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="姓名" align="center" width="160"></el-table-column>
                    <el-table-column prop="catalog" label="专业类别" align="center" width="200"></el-table-column>
                    <el-table-column prop="workPlace" label="工作单位" align="left"></el-table-column>
                    <el-table-column prop="vote" label="得票数" align="center" width="160">
                        <template slot-scope="scope">
                                        <span style="font-size:16px; margin-right:10px">{{scope.row.vote}}票</span>
                                        <el-button v-if="currentUser.recommended !='已推荐'" type="primary" plain size="mini">推荐</el-button>
                                        
                        </template>
                    </el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="180"></el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="showExpertDialog(scope.row)">编辑</el-button>
                        <el-button type="primary" size="mini" @click="del(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="pageChange(page)" layout="prev, pager, next" :total="100"></el-pagination>
            </div>
        </div>

        <!-- 新增评委 -->
        <el-dialog title="新增专家" :visible.sync="expertDialog">
                <el-form ref="form" :model="form" :rules="rules" label-width="150px" style="margin-right:50px">
                   <el-form-item label="专家姓名" prop="name">
                        <el-input v-model="form.name" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="身份证号" prop="idCardNo">
                        <el-input v-model="form.idCardNo" placeholder="请填写"></el-input>
                    </el-form-item>
                   <el-form-item label="联系电话" prop="phone">
                        <el-input v-model="form.phone" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="专业类别" prop="catalog">
                        <el-cascader v-model="form.catalog" :options="bidCatalogs" @change="catalogChange" style="width:100%"></el-cascader>
                    </el-form-item>
                    <el-form-item label="工作单位" prop="workPlace">
                        <el-input v-model="form.workPlace" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input type="textarea" rows="5" v-model="form.memo"></el-input>
                    </el-form-item>
                    <el-form-item label="登录账户" prop="username">
                        <el-input v-model="form.username" placeholder="请填写"></el-input>
                    </el-form-item>
                    <el-form-item label="登录密码" prop="password">
                        <el-input v-model="form.password" placeholder="请填写"></el-input>
                    </el-form-item>
                    <div style="width:100%; margin-top:40px; text-align:center">
                        <el-button v-on:click="onCancel()">取消</el-button>
                        <el-button type="primary" v-on:click="onSave()">保存</el-button>
                    </div>
                </el-form>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'ExpertList',
        data() {
            return {
                form: {},
                rules: {
                    name: [{ required: true, message: '请填写单位名称', trigger: 'blur' }],
                    uniformCode: [{ required: true, message: '请填写统一社会信用代码', trigger: 'blur' }],
                    area: [{ required: true, message: '请选择所属地区', trigger: 'change' }],
                    creatorName: [{ required: true, message: '请填写您的姓名', trigger: 'blur' }],
                    creatorIdcardNo: [{ required: true, message: '请填写您的身份证号码', trigger: 'blur' }],
                    creatorPhone: [{ required: true, message: '请填写联系电话', trigger: 'blur' }],
                    creatorEmail: [{ required: true, message: '请填写您的电子邮箱', trigger: 'blur' }],
                    creatorAddress: [{ required: true, message: '请填写有效通讯地址及邮编', trigger: 'blur' }]
                },
                currentUser: {},
                experts: [],
                currentPage: 1,
                expertDialog: false,
                loading: false
            }
        },
        created() {
            this.requestExperts();
        },
        methods: {
            // 获取专家列表
            requestExperts() {
                this.loading = true;
                this.$http.get('/uaa/user/list').then((data) => {
                    this.experts = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            // 分页
            pageChange(page) {
                this.currentPage = page;
                this.requestExperts();
            },
            showExpertDialog() {
                this.expertDialog = true;
            },
            // 取消
            onCancel(row) {
                this.$router.push('/BidCenter');
            },
            // 保存
            onSave(row) {
                this.loading = true;
                let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
                this.loading = false;
                this.$message.success('保存成功！');
            }
        }
    }
</script>
