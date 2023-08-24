<template>
    <div>

        <div class="container" v-loading="loading">
            <el-form ref="form" :model="form" :rules="rules" label-width="150px" style="width:90%">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="开标时间" prop="bidOpenTime">
                            <el-date-picker v-model="form.bidOpenTime" type="datetime" placeholder="选择日期时间（即投标文件递交截止时间）" value-format="yyyy-MM-dd HH:mm:ss" style="width:100%" :disabled="disable"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                       <el-form-item label="开标时长" prop="bidOpenPeriod">
                            <el-input v-model="form.bidOpenPeriod" placeholder="请填写（即解密时间）" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="开标场地" prop="bidOpenPlace">
                            <el-input v-model="form.bidOpenPlace" placeholder="请填写" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="评标时间" prop="bidEvalTime">
                            <el-date-picker v-model="form.bidEvalTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" style="width:100%" :disabled="disable"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                       <el-form-item label="评标时长" prop="bidEvalPeriod">
                            <el-input v-model="form.bidEvalPeriod" placeholder="请填写" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="评标场地" prop="bidEvalPlace">
                            <el-input v-model="form.bidEvalPlace" placeholder="请填写" :disabled="disable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>

            <div style="width:100%; margin-top:40px; text-align:center">
                <el-button type="primary" plain @click="disable=false">修改</el-button>
                <el-button type="primary" plain @click="onSave" :disabled="disable">保存</el-button>
            </div>

        </div>
        
    </div>
</template>


<script>
    export default {
        name: 'BidOpen',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                form: {},
                rules: {
                    bidOpenTime: [{ required: true, message: '请填写', trigger: 'blur' }],
                    bidOpenPeriod: [{ required: true, message: '请填写', trigger: 'blur' }],
                    bidOpenArea: [{ required: true, message: '请填写', trigger: 'blur' }],
                    bidEvalTime: [{ required: true, message: '请填写', trigger: 'blur' }],
                    bidEvalPeriod: [{ required: true, message: '请填写', trigger: 'blur' }],
                    bidEvalArea: [{ required: true, message: '请填写', trigger: 'blur' }]
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
            tenderSection(oldValue, newValue) {
                console.log('watch tenderSection')
                this.fetchData();
            }
        },
        methods: {
            // 获取数据
            fetchData() {
                this.loading = true;
                this.form = {};
                this.$refs.form.resetFields();
                this.$http.get('/tas/tender/section/detail?sectionUid=' + this.tenderSection.sectionUid).then((data) => {
                    this.form = data;
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
                        this.$http.post('/tas/tender/section/update', params).then((data) => {
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