<template>
    <div>

        <div class="container">
            <h3 style="margin-bottom:20px; padding:20px; text-align:center; color:#303133; background: #F2F6FC">用户管理</h3>
            <div style="margin-bottom:20px">
                <el-button type="primary" icon="el-icon-plus" @click="showEditor">新增</el-button>
            </div>
            <el-table style="width:100%; font-size:14px;" strip :data="tableData" v-loading="loading">
                <el-table-column prop="id" label="序号" width="80"></el-table-column>
                <el-table-column prop="username" label="用户名" width="200"></el-table-column>
                <el-table-column prop="authorities" label="角色" width="200"></el-table-column>
                <el-table-column prop="phone" label="手机号" width="200"></el-table-column>
                <el-table-column prop="realName" label="姓名" width="260"></el-table-column>
                <el-table-column prop="idNo" label="身份证号" width="100"></el-table-column>
                <el-table-column prop="idFrontPic" label="身份证照片" width="120"></el-table-column>
                <el-table-column prop="idBackPic" label="身份证照片" width="100"></el-table-column>
                <el-table-column prop="status" label="是否认证" width="80">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.certified" style="color:#409EFF">已认证</strong>
                        <strong v-else style="color:#909399">待认证</strong>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" width="80">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.enabled" style="color:#409EFF">正常</strong>
                        <strong v-else style="color:#909399">停用</strong>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
                <el-table-column label="操作" width="220" align="center">
                    <template slot-scope="scope">
                    <el-button type="primary"size="mini" @click="showEditor(scope.$index, scope.row)">管理</el-button>
                  </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="用户信息" :visible.sync="visible">
            <div class="form-box" style="margin:10px auto" v-loading="loading">
                <el-form ref="form" :model="form" :rules="rules" :disabled="disable" label-width="120px">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="form.username"></el-input>
                    </el-form-item>
                    <el-form-item label="角色" prop="authorities">
                        <el-input v-model="form.authorities"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="form.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="realName">
                        <el-input v-model="form.realName"></el-input>
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
    </div>
</template>

<script>
    export default {
        name: 'UserList',
        data() {
            var checkPhone = (rule, value, callback) => {
                let regx =  /^1\d{10}$/;
                if (!value) {
                    callback(new Error('请填写手机号'));
                } else if (!regx.test(value)) {
                    callback(new Error('手机号无效'));
                } else {
                    callback();
                }
            };
            return {
                form: {},
                rules: {
                    realName: [{ required: true, message: '请填写真实姓名', trigger: 'blur' }],
                    authorities: [{ required: true, message: '请填写角色权限', trigger: 'blur' }],
                    username: [{ required: true, message: '请设置管理员账号', trigger: 'blur' }],
                    password: [{ required: true, message: '请设置登录密码', trigger: 'blur' }],
                    phone: [{ required: true, validator: checkPhone, trigger: 'blur' }]
                },
                tableData: [],
                visible: false,
                disable: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            this.getData();
        },
        methods: {
            getData() {
                this.loading = true;
                this.$http.get('/uaa/user/list').then((data) => {
                    this.tableData = data.content;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            showEditor(index, row) {
                if(row) {
                    this.form = row;
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
                        this.$http.post('/uaa/user/update', this.form).then((data) => {
                            //this.tableData.unshift(data);
                            this.loading = false;
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
