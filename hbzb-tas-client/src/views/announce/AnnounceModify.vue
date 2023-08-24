<template>
	<div>

		<div class="container">

			<!-- 标题栏 -->
			<h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">变更公告</h3>

			<el-collapse v-model="collapse">
				<!-- 原公告 -->
				<el-collapse-item title="原公告" name="1">
					<template slot="title">
						<span style="font-size:15px"><b>原公告：</b>{{orginAnnounce.title}}</span>
					</template>
					<el-card shadow="hover">
						<iframe width="100%" height="300px" frameborder="0" :srcdoc="orginAnnounce.content"></iframe>
					</el-card>
				</el-collapse-item>

				<!-- 变更公告 -->
				<el-collapse-item title="公告信息" name="2">
					<template slot="title">
						<span style="font-size:15px"><b>变更公告</b></span>
					</template>
					<el-form :model="form" :rules="rules" ref="form" label-width="80px">
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="公告标题" prop="title">
									<el-input v-model="form.title" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="公告类型" prop="attribute">
									<el-radio-group v-model="form.attribute">
										<el-radio label="变更公告">变更公告</el-radio>
										<el-radio label="答疑澄清">答疑澄清</el-radio>
										<el-radio label="其他">其他</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="发布媒体" prop="media">
									<el-select v-model="form.media" multiple placeholder="请选择" style="width:100%">
										<el-option v-for="item in mediaList" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="公告内容" prop="content">
									<Tinymce v-model="form.content" :init="tinymceConfig">公告内容</Tinymce>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<!-- 附件信息 -->
								<el-form-item label="相关附件">
									<el-upload ref="upload" :action="uploadUrl" :headers="uploadHeaders" :data="uploadParams" :before-upload="handleBeforeUpload" :on-progress="handleOnProgress" 
		                                    :on-success="handleSuccess" :on-error="handleError" :on-remove="handleRemove" 
		                                    :on-preview="handlePreview" :file-list="fileList" multiple :limit="3" :auto-upload="false">
		                                    <el-button slot="trigger" size="small" type="primary" :disabled="disable">选取文件</el-button>
		                                    <el-button style="margin-left:10px" size="small" type="success" @click="submitUpload" :disabled="disable">上传到服务器</el-button>
		                                    <div slot="tip" class="el-upload__tip">（只能上传docx/pdf文件，且不超过50M）</div>
		                            </el-upload>
		                        </el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</el-collapse-item>
			</el-collapse>

			<!-- 底部按钮 -->
			<div style="margin-top:20px; text-align:center">
				<el-button type="primary" @click="onSave">保存</el-button>
				<el-button type="primary" plain>无须审核</el-button>
				<el-button type="primary">提交审核</el-button>
			</div>
		</div>

	</div>
</template>

<script type="text/javascript">

	// import 'tinymce/themes/mobile/theme.js';
	import Tinymce from '@tinymce/tinymce-vue';

	export default {
		name: 'AnnounceModify',
		components: { 'Tinymce': Tinymce },
		data() {
			return {
				collapse: ['1','2','3'],
				orginAnnounce: {},
				announce: {},
				materials: [],
				mediaList: [ 
					{ value: 'E招冀成', label: 'E招冀成' },
					{ value: '河北省公共资源交易中心', label: '河北省公共资源交易中心' },
					{ value: '河北省政府采购网', label: '河北省政府采购网' }
				],
				media: [],
				form: {},
				rules: {
					title: [{ required: true, message: '请填写', trigger: 'blur' }],
					media: [{ required: true, message: '请选择', trigger: 'change' }],
					content: [{ required: true, message: '请填写', trigger: 'blur' }]
				},
				tinymceConfig: {
			        height: 600,
			        // language_url: '/tinymce/zh_CN.js',
			        language: 'zh_CN',
				},
				uploadUrl: this.$http.uploadURL + '/tas/announce/material/upload',
                uploadHeaders: { 'Authorization': sessionStorage.getItem('token') },
                uploadParams: { 'announceUid': '' },
                fileList: [],
				loading: false,
				sectionLoading: false,
				disable: false
			}
		},
		created() {
			if(sessionStorage.getItem("orginAnnounce")) {
				this.orginAnnounce = JSON.parse(sessionStorage.getItem("orginAnnounce"));
			}
			if(sessionStorage.getItem("announce")) {
				this.announce = JSON.parse(sessionStorage.getItem("announce"));
				this.form = this.announce;
				if(this.announce.media) this.form.media = this.announce.media.split(',');
				this.uploadParams.announceUid = this.announce.uid;
			}
		},
		watch: {
            $route(to, from) {
            	if(sessionStorage.getItem("orginAnnounce")) {
					this.orginAnnounce = JSON.parse(sessionStorage.getItem("orginAnnounce"));
				}
                this.announce = JSON.parse(sessionStorage.getItem("announce"));
				this.form = this.announce;
				if(this.announce.media) this.form.media = this.announce.media.split(',');
				this.uploadParams.announceUid = this.announce.uid;
            }
        },
		methods: {
            // 上传附件
            handleBeforeUpload(res) {
                if(res.type != 'application/pdf' && res.type === 'image/png' && res.type === 'image/jpeg') {
                  this.$message.warning('只能是png,jpg,pdf格式文件!');
                  return false;
                }
                if(res.size > 1024*1024*50) {
                  this.$message.warning('文件不能超过50MB!');
                  return false;
                }
            },
            submitUpload() {
                this.$refs.upload.submit();
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

			onSave() {
				this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let params = Object.assign({}, this.form);
                        params.orginUid = this.orginAnnounce.uid;
                        params.media = params.media.toString();
                        this.$http.post('/tas/announce/modify', params).then((data) => {
                            this.disable = true;
                            this.loading = false;
                            this.$refs.upload.submit();
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


