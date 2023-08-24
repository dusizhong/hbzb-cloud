<template>
	<div>

		<div class="container">

			<!-- 标题栏 -->
			<h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">编辑公告</h3>

			<!-- 项目信息 -->
            <el-card shadow="hover" style="margin:20px 0; font-size:16px; line-height:36px">
            	<div>项目编号：<b>{{project.serialNo}}</b></div>
                <div>项目名称：<b>{{project.name}}</b></div>
            </el-card>

			<el-collapse v-model="collapse">
				<!-- 标段信息 -->
				<el-collapse-item title="标段信息" name="1">
					<template slot="title">
						<span style="font-size:15px"><b>标段信息</b></span>
					</template>
					<el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="sections" v-loading="sectionLoading">
						<el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
						<el-table-column prop="serialNo" label="标段编号" align="center" width="200"></el-table-column>
						<el-table-column prop="name" label="标段名称" align="left"></el-table-column>
						<el-table-column prop="tradeType" label="采购方式" align="center" width="120"></el-table-column>
						<el-table-column label="操作" fixed="right" align="center" width="120">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="removeSection(scope.$index)">移除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</el-collapse-item>
				<!-- 公告信息 -->
				<el-collapse-item title="公告信息" name="2">
					<template slot="title">
						<span style="font-size:15px"><b>公告信息</b></span>
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
								<el-form-item label="公告类型" prop="type">
									<el-radio-group v-model="form.type">
										<el-radio label="采购预告">采购预告</el-radio>
										<el-radio label="采购公告">采购公告</el-radio>
										<el-radio label="其他">其他</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
						</el-row>
						<!-- <el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="报名开始时间" prop="startTenderTime">
									<el-date-picker v-model="form.startTenderTime" type="datetime" placeholder="报名开始/交易文件发售时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" style="width:100%"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="报名截止时间" prop="endTenderTime">
									<el-date-picker v-model="form.endTenderTime" type="datetime" placeholder="报名开始/交易文件发售时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="23:59:59" style="width:100%"></el-date-picker>
								</el-form-item>
							</el-col>
						</el-row> -->
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
								<Tinymce v-model="form.content" apiKey="4l9z5al04c5e2jf7alkeyzrdfipz4fqsk7r20lh5xx3uicf7" :init="tinymceConfig">公告内容</Tinymce>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<!-- 附件信息 -->
								<el-form-item label="相关附件">
									<el-upload ref="upload" :action="uploadUrl" :headers="uploadHeaders"
		                                    :data="uploadParams" :before-upload="handleBeforeUpload" :on-progress="handleOnProgress" 
		                                    :on-success="handleSuccess" :on-error="handleError" :on-remove="handleRemove" 
		                                    :on-preview="handlePreview" :file-list="fileList" multiple :limit="3" :auto-upload="false">
		                                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
		                                    <el-button style="margin-left:10px" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
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
		name: 'AnnounceEditor',
		components: { 'Tinymce': Tinymce },
		data() {
			return {
				collapse: ['1','2','3'],
				project: {},
				sections: [],
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
					type: [{ required: true, message: '请选择', trigger: 'change' }],
					// attribute: [{ required: true, message: '请选择', trigger: 'change' }],
					// startTenderTime: [{ required: true, message: '请选择', trigger: 'change' }],
					// endTenderTime: [{ required: true, message: '请选择', trigger: 'change' }],
					media: [{ required: true, message: '请选择', trigger: 'blur' }],
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
				sectionLoading: false
			}
		},
		created() {
			this.fetchData();
		},
		watch: {
            $route(to, from) {
            	this.fetchData()
            }
        },
		methods: {
			fetchData() {
				if(sessionStorage.getItem("announce")) {
					let announce = JSON.parse(sessionStorage.getItem("announce"));
					console.log('announce', announce)
					if(announce.sectionUids) {
						this.loading = true;
						this.$http.get('/tas/section/list2?sectionUids=' + announce.sectionUids).then((data) => {
							console.log('sections', data)
							if(data.length > 0) {
								this.sections = data;
								this.$http.get('/tas/project/detail?projectUid=' + data[0].projectUid).then((data) => {
									console.log('project', data)
									this.project = data;
									this.loading = false;
								}).catch(() => this.loading = false);
							} else this.loading = false;
						}).catch(() => this.loading = false);
					}
					if(announce.uid) {
						this.loading = true;
						this.$http.get('/tas/announce/detail?announceUid=' + announce.uid).then((data) => {
							console.log('announce', data)
							this.form = data;
							if(data.media) this.form.media = data.media.split(',');
							this.uploadParams.announceUid = data.uid;
							this.fileList = data.materials;
							this.loading = false;
						}).catch(() => this.loading = false);
					} else this.form = {};
				} else {
					this.$message.error('异常！sessionStorage未获取到传参。')
				}
			},
			
			removeSection(index) {
				this.sections.splice(index, 1);
			},

            // 上传附件
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
            submitUpload() {
            	if(!this.uploadParams.announceUid) {
            		this.$message.warning('请先保存');
            		return;
            	}
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
					if (!valid) return false;
					this.loading = true;
					let params = Object.assign({}, this.form);
					let sectionUids = '';
					for(let s of this.sections) {
						if(!sectionUids) sectionUids = s.uid;
						else sectionUids = sectionUids + ',' + s.uid;
					}
					params.sectionUids = sectionUids;
					params.media = params.media.toString();
					params.attribute = '正常公告';
					this.$http.post('/tas/announce/create', params).then((data) => {
						sessionStorage.setItem("announce", JSON.stringify(data));
						this.loading = false;
						this.fetchData();
						this.$message.success('保存成功！');
					}).catch(() => this.loading = false);
				});
			}
		}
	}
</script>


