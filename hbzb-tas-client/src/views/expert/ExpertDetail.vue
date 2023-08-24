<template>
	<div v-loading="loading">

		<div class="container">

			<h3 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">专家详情</h3>

			<el-form :model="form" :rules="rules" ref="form" label-width="140px">

			<el-collapse v-model="collapse">

				<!-- 1.登录账号 -->
				<el-collapse-item title="1.登录账号" name="1">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="用户名" prop="username">
									<el-input v-model="form.username" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="密码" prop="password">
									<el-input v-model="form.password" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="CA锁" prop="uKey">
									<el-input v-model="form.uKey" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
				</el-collapse-item>

				<!-- 2.个人信息 -->
				<el-collapse-item title="2.个人信息" name="2">
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="姓名" prop="realName">
								<el-input v-model="form.realName" placeholder="请填写"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="性别" prop="sex">
									<el-select v-model="form.sex" style="width:100%" placeholder="请选择">
										<el-option label="男" key="男" value="男"></el-option>
										<el-option label="女" key="女" value="女"></el-option>
									</el-select>
								</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="出生年月" prop="birthday">
								<el-date-picker v-model="form.birthday" value-format="yyyy-MM-dd" placeholder="请选择" style="width:100%"></el-date-picker>
							</el-form-item>
							</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="联系电话" prop="creatorPhone">
								<el-input v-model="form.creatorPhone" placeholder="请填写"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="电子邮箱" prop="creatorEmail">
								<el-input v-model="form.creatorEmail" placeholder="请填写"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="24">
							<el-form-item label="通讯地址" prop="creatorAddress">
								<el-input v-model="form.creatorAddress" placeholder="请填写有效通讯地址及邮编"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
				</el-collapse-item>

				<!-- 3.附件信息 -->
				<el-collapse-item title="3.附件信息" name="3">
					<el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="materials">
						<el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
						<el-table-column prop="name" label="电子件名称" align="center" width="300"></el-table-column>
						<el-table-column prop="memo" label="说明" >
							<template slot-scope="scope">
								<div>{{scope.row.memo}}（<span><a v-if="scope.row.example" :href="scope.row.example" target="_blank">样例</a></span>）</div>
							</template>
						</el-table-column>
						<el-table-column prop="url" label="电子件" align="center" width="350">
							<template slot-scope="scope">
								<a v-if="scope.row.url" :href="scope.row.url" target="_blank"><i class="el-icon-picture"></i> {{scope.row.name}}.jpg</a>
								<span v-else>未上传</span>
							</template>
						</el-table-column>
						<el-table-column label="操作" align="center" width="150">
							<template slot-scope="scope">
								<el-upload v-if="!scope.row.url" ref="upload" 
									:action="uploadUrl"
									:headers="headers"
									:data="params"
									:before-upload="(res)=>{return handleBeforeUpload(res, scope.row.id)}" 
									:on-success="(res)=>{return handleSuccess(res, scope.$index)}"
									:on-error="handleError"
									:show-file-list="false">
									<el-button type="primary" size="mini" plain>上传</el-button>
								</el-upload>
								<el-button v-else type="text" size="mini" plain @click="shopDialog(scope.$index)">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</el-collapse-item>
			</el-collapse>
	</el-form>
			<!-- 底部按钮 -->
			<div style="margin-top:20px; text-align:center">
				<el-button type="primary" plain @click="onSave()">保存</el-button>
				<el-button type="primary" @click="onSubmit()">提交审核</el-button>
			</div>

		</div>

		<!-- 提示对话框 -->
		<el-dialog title="提示" center :visible.sync="tipDialog">
			<p>您的单位信息正在审核中，请耐心等待。</p>
		</el-dialog>

	</div>
</template>


<script>
	export default {
		name: 'ExpertDetail',
		data() {
			return {
				collapse: ['1','2','3'], // show all collapse
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
				userProfile: {},
				nations: [],
				industries: [],
				areas: [],
				materials: [
					{ "id": 1, "name": "营业执照", "memo": "营业执照彩色扫描件", "example": "./data/business_license.jpg", "url": "x.pdf" },
					{ "id": 2, "name": "法定代表人身份证", "memo": "法定代表人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 3, "name": "注册人身份证", "memo": "注册人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 4, "name": "授权委托书", "memo": "法人授权委托书扫描件", "example": "./data/authorization.jpg", "url": "" }
				],
				tipDialog: false,
				loading: false
			}
		},
		created() {
			// 获取用户信息
			this.loading = true;
			this.$http.get('/uaa/user/profile', params).then((data) => {
				this.userProfile = data;
				this.loading = false;
			}).catch(() => this.loading = false);
		},
		methods: {
			areaChange() {

			},
			onSave() {
				this.$refs.form.validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = this.form;
						// params.areas = params.area.toString();
						params.role = 'AGENCY';
						this.$http.post('/uaa/corp/create', params).then((data) => {
							this.loading = false;
							this.$message.success('保存成功！');
						}).catch(() => this.loading = false);
					} else {
						return false;
					}
				})
			},
			onSubmit() {
				this.tipDialog = true;
				this.$message.success('提交成功！');
			}
		}
	}
</script>
