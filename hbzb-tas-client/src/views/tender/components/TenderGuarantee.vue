<template>
    <div>
        <div class="container" v-loading="loading">
            <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="保证金金额" prop="amount">
                            <el-input v-model.number="form.amount" :disabled="disable"><span style="margin-right:10px" slot="suffix">元</span></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="保证金缴纳截止时间" prop="deadline">
                            <el-date-picker v-model="form.deadline" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="选择日期时间" style="width:100%" :disabled="disable"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>

                    <el-form-item label="缴纳方式" prop="type">
                        <el-checkbox-group v-model="form.type" :disabled="disable">
                            <el-checkbox label="银行转账"></el-checkbox>
                            <el-checkbox label="银行保函"></el-checkbox>
                            <el-checkbox label="担保机构保函"></el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>

                    <el-form-item v-if="form.type.indexOf('银行转账')>0" label="保证金缴纳户名" prop="accountName">
                        <el-input v-model="form.accountName" :disabled="disable"></el-input>
                    </el-form-item>

                <el-row :gutter="20" v-if="form.type.indexOf('银行转账')>0">
                    <el-col :span="12">
                        <el-form-item label="保证金缴纳开户行" prop="bankName">
                            <el-input v-model="form.bankName" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="保证金缴纳账号" prop="accountNo">
                            <el-input v-model="form.accountNo" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                    <el-form-item label="必须基本户支付" prop="basicPay">
                        <el-radio-group v-model="form.basicPay" :disabled="disable">
                            <el-radio label="true">是</el-radio>
                            <el-radio label="false">否</el-radio>
                        </el-radio-group>
                    </el-form-item>

                <div style="width:100%; margin-top:40px; text-align:center">
                    <el-button type="primary" plain @click="disable=false">修改</el-button>
                    <el-button type="primary" plain @click="onSave" :disabled="disable">保存</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>


<script>
    export default {
        name: 'TenderGuarantee',
        props:{
            section: {}
        },
        data() {
            return {
                form: { 'type': [] },
                rules: {
                    amount: [{ required: true, message: '请填写保证金金额', trigger: 'blur' }],
                    deadline: [{ required: true, message: '请选择保证金缴纳截止时间', trigger: 'blur' }],
                    type: [{ required: true, type: 'array', message: '请选择缴纳方式', trigger: 'blur' }],
                },
                disable: false,
                loading: false
            }
        },
        created() {
            
        },
        mounted() {
            this.fetchData();
        },
        watch: {
            section(oldValue, newValue) {
                this.fetchData();
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                this.form = { 'type': [] };
                this.$refs.form.resetFields();
                this.$http.get('/tas/tender/guarantee/detail?sectionUid=' + this.section.uid).then((data) => {
                    this.form = data;
                    this.form.type = data.type.split(',');
                    this.disable = true;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            onModify() {
                this.disable = false;
            },
            onSave() {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let params = Object.assign({}, this.form);
                        params.sectionUid = this.section.uid;
                        params.type = params.type.toString();
                        this.$http.post('/tas/tender/guarantee/create', params).then((data) => {
                            this.disable = true;
                            this.loading = false;
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