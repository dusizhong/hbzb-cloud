<template>
	<div>
		<div class="container">
			<!-- 标题栏 -->
			<h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">编辑邀请</h3>
            
			<el-collapse v-model="collapse">

				<!-- 项目信息 -->
				<el-collapse-item title="项目信息" name="1">
					<template slot="title">
						<span style="font-size:15px"><b>项目名称：</b>{{project.name}}</span>
					</template>
					<div><b>项目编号：</b>{{project.serialNo}}</div>
		        </el-collapse-item>
				<el-collapse-item title="标段信息" name="2">
					<template slot="title">
						<span style="font-size:15px"><b>标段信息</b></span>
					</template>
					<el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" :data="sections" v-loading="loading">
						<el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
						<el-table-column prop="serialNo" label="标段编号" align="center" width="200"></el-table-column>
						<el-table-column prop="name" label="标段名称" align="left"></el-table-column>
						<el-table-column prop="tradeType" label="采购方式" align="center" width="120"></el-table-column>
						<el-table-column prop="replyDeadline" label="回复截止时间" align="center" width="120"></el-table-column>
					</el-table>
				</el-collapse-item>

				<!-- 邀请函 -->
				<el-collapse-item title="邀请函" name="3">
					<template slot="title">
						<span style="font-size:15px"><b>邀请函</b></span>
					</template>
					<Tinymce v-model="form.content" apiKey="4l9z5al04c5e2jf7alkeyzrdfipz4fqsk7r20lh5xx3uicf7" :init="tinymceConfig">正文</Tinymce>
				</el-collapse-item>

				<!-- 邀请单位信息 -->
				<el-collapse-item title="邀请单位信息" name="4">
					<template slot="title">
						<span style="font-size:15px"><b>邀请单位信息</b></span>
					</template>
					<InviteRecord :invitation="invitation" @reload="reload"></InviteRecord>
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
	import InviteRecord from './components/InviteRecord';

	export default {
		name: 'InvitationEditor',
		components: { Tinymce, InviteRecord },
		data() {
			return {
				collapse: ['2','3','4'],
				project: {},
				sections: [],
				invitation: { 'uid': '', 'sectionUids': '' },
				form: {},
				rules: {
					title: [{ required: true, message: '请填写', trigger: 'blur' }],
					replyDeadline: [{ required: true, message: '请选择', trigger: 'change' }],
					content: [{ required: true, message: '请填写', trigger: 'blur' }]
				},
				tinymceConfig: {
			        height: 600,
			        // language_url: '/tinymce/zh_CN.js',
			        language: 'zh_CN',
				},
				loading: false
			}
		},
		created() {
			this.initData();
		},
		watch: {
            $route(to, from) {
            	this.initData()
            }
        },
		methods: {
			initData() {
				if(sessionStorage.getItem("invitation")) {
					this.invitation = JSON.parse(sessionStorage.getItem("invitation"));
					if(this.invitation.sectionUids) {
						this.loading = true;
						this.$http.get('/tas/section/list2?sectionUids=' + this.invitation.sectionUids).then((data) => {
							if(data.length > 0) {
								this.sections = data;
								this.$http.get('/tas/project/detail?projectUid=' + data[0].projectUid).then((data) => {
									this.project = data;
									this.loading = false;
								}).catch(() => this.loading = false);
							} else this.loading = false;
						}).catch(() => this.loading = false);
					}
					if(this.invitation.uid) {
						this.loading = true;
						this.$http.get('/tas/invitation/detail?invitationUid=' + this.invitation.uid).then((data) => {
							this.form = data;
						}).catch(() => this.loading = false);
					} else this.form = {};
				} else {
					this.$message.error('异常！sessionStorage未获取到传参。')
				}
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
					this.$http.post('/tas/invitation/create', params).then((data) => {
						sessionStorage.setItem("invitation", JSON.stringify(data));
						this.loading = false;
						this.initData();
						this.$message.success('保存成功！');
					}).catch(() => this.loading = false);
				});
			},

			reload() {
				// 与组件reload方法配合实现组件数据刷新
				this.invitation = JSON.parse(sessionStorage.getItem("invitation"));
			}
		}
	}
</script>
