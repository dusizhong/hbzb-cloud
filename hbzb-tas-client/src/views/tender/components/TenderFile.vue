<template>
    <div>
        <div class="container" v-loading="loading">
            <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="售价" prop="price">
                            <el-input v-model.number="form.price" :disabled="disable">
                                <span style="margin-right:10px" slot="suffix">元</span>
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="发售时间" prop="startSellTime">
                                <el-date-picker v-model="form.startSellTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" style="width:100%" :disabled="disable"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="发售截止时间" prop="endSellTime">
                                <el-date-picker v-model="form.endSellTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" style="width:100%" :disabled="disable"></el-date-picker>
                            </el-form-item>
                        </el-col>
                </el-row>
                <el-row :gutter="20">
                        <el-col :span="24">
                            <el-form-item label="招标文件获取方法">
                                <el-input type="textarea" rows="5" v-model="form.obtainMethod" :disabled="disable"></el-input>
                            </el-form-item>
                        </el-col>
                </el-row>
                <el-row :gutter="20">
                        <el-col :span="24">
                            <el-form-item label="招标文件">
                                <el-upload ref="upload" :action="uploadUrl" :headers="uploadHeaders"
                                    :data="uploadParams" :before-upload="handleBeforeUpload" :on-progress="handleOnProgress" 
                                    :on-success="handleSuccess" :on-error="handleError" :on-remove="handleRemove" 
                                    :on-preview="handlePreview" :file-list="fileList" multiple :limit="3" :auto-upload="false">
                                    <el-button slot="trigger" size="small" type="primary" :disabled="disable">选取文件</el-button>
                                    <el-button style="margin-left:10px" size="small" type="success" @click="submitUpload" :disabled="disable">上传到服务器</el-button>
                                    <div slot="tip" class="el-upload__tip">（只能上传docx/pdf文件，且不超过50M）</div>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                </el-row>
                <!-- <div>预览盖章(CA控件)</div>
                <el-input type="textarea" rows="50" v-model="form.pdf"></el-input> -->
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
        name: 'TenderFile',
        props: { section: {} },
        data() {
            return {
                form: {},
                rules: {
                    price: [{ required: true, message: '请填写招标文件售价', trigger: 'blur' }],
                    startSellTime: [{ required: true, message: '请选择招标文件发售时间', trigger: 'change' }],
                    endSellTime: [{ required: true, message: '请选择招标文件发售截止时间', trigger: 'change' }]
                },
                uploadUrl: this.$http.uploadURL + '/tas/tender/file/upload',
                uploadHeaders: { 'Authorization': sessionStorage.getItem('token') },
                uploadParams: { 'sectionUid': this.section.uid },
                fileList: [],
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
            // watch section
            section(oldValue, newValue) {
                console.log('watch section')
                this.fetchData();
            }
        },
        methods: {
            // 获取招标文件信息
            fetchData() {
                this.loading = true;
                this.form = {};
                this.fileList = [];
                this.$refs.form.resetFields();
                this.$http.get('/tas/tender/file/detail?sectionUid=' + this.section.uid).then((data) => {
                    this.form = data;
                    // 拼装格式
                    let file = { 'name': data.name, 'url': data.originFile };
                    this.fileList.push(file);
                    this.disable = true;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            
            // 上传文件
            handleBeforeUpload(res) {
                if(res.type != 'application/pdf') {
                  this.$message.warning('只能是pdf格式文件!');
                  return false;
                }
                if(res.size > 1024*1024*50) {
                  this.$message.warning('文件不能超过50MB!');
                  return false;
                }
            },
            handleOnProgress(event, file, fileList) {
                let loadProgress = Math.floor(event.percent);
                console.log('on progress', loadProgress)
            },
            handleSuccess(res, index) {
                if(res.code == '200') {
                    this.$message.success('上传成功');
                } else this.$message.error(res.message);
            },
            handleError(error) {
                this.$message.error('上传失败' + error);
            },
            handlePreview(file) {
                console.log('preview', file)
            },
            handleRemove(file, fileList) {
                console.log(fileList);
            },
            submitUpload() {
                this.$refs.upload.submit();
            },

            // 保存信息
            onSave() {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let params = Object.assign({}, this.form);
                        params.sectionUid = this.section.uid;
                        this.$http.post('/tas/tender/file/create', params).then((data) => {
                            this.disable = true;
                            this.loading = false;
                            this.$message.success('保存成功！');
                        }).catch(() => this.loading = false);
                    } else {
                        return false;
                    }
                })
            },
            onModify() {
                this.disable = false;
            }
        }
    }
</script>