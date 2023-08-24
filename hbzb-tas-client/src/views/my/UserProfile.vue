<template>
	<div v-loading="loading">

		<div class="container">

			<h3 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">个人信息</h3>

			<div style="display:flex; justify-content:center">

				<!-- 头像 -->
				<div>
					<img style="width:160px; height:160px" src="../../assets/avatar.png">
				</div>

				<div style="width:800px">
					<el-form :model="form" :rules="rules" ref="form" label-width="140px">
						<el-form-item label="姓名" prop="name">
								<el-input v-model="form.name" placeholder="请填写" :disabled="true"></el-input>
							</el-form-item>
							<el-form-item label="身份证号码" prop="idcardNo">
								<el-input v-model="form.idcardNo" placeholder="请填写"></el-input>
							</el-form-item>
								<el-form-item label="联系电话" prop="phone">
								<el-input v-model="form.phone" placeholder="请填写"></el-input>
							</el-form-item>
							<el-form-item label="电子邮箱" prop="email">
								<el-input v-model="form.email" placeholder="请填写"></el-input>
							</el-form-item>
							<el-form-item label="通信地址" prop="address">
								<el-input v-model="form.address" placeholder="请填写有效通信地址及邮编"></el-input>
							</el-form-item>
							<el-form-item label="身份证电子件" prop="idcardPic">
								<el-upload v-if="!form.idcardNo" ref="upload" 
									:action="this.$http.baseURL + '/uaa/user/info/upload'"
									:headers="{ 'Authorization': sessionStorage.getItem('token') }"
									:data="params"
									:before-upload="(res)=>{return handleBeforeUpload(res, scope.row.id)}" 
									:on-success="(res)=>{return handleSuccess(res, scope.$index)}"
									:on-error="handleError"
									:show-file-list="false">
									<el-button type="primary" size="mini" plain>上传</el-button>
								</el-upload>
								<el-button type="text" size="mini" plain @click="showDelDialog(scope.$index)">删除</el-button>
							</el-form-item>
					</el-form>

					<!-- 底部按钮 -->
					<div style="margin-top:20px; text-align:center">
						<el-button type="primary" plain @click="onSave()">保存</el-button>
						<el-button type="primary" @click="onSubmit()">提交审核</el-button>
					</div>



					<el-collapse v-model="collapse">
						<!-- 账号信息 -->
						<el-collapse-item title="账号信息" name="2">
							<el-form label-width="140px">
							<el-form-item label="用户名">
								<span>13131197776</span>
							</el-form-item>
							<el-form-item label="密码" prop="password">
								<span>*********</span><el-button type="primary" plain siz="mini">修改密码</el-button>
							</el-form-item>
							<el-form-item label="手机号" prop="phone">
								<span>13131197776</span> <el-button type="primary" plain siz="mini">更换</el-button>
							</el-form-item>
							<el-form-item label="CA锁" prop="uKey">
								<span>河北招标集团网络科技有限公司865856</span> <el-button type="primary" plain siz="mini">更换</el-button>
							</el-form-item>
							</el-form>
						</el-collapse-item>
					</el-collapse>
			
				</div>
			</div>

		</div>

		<!-- 删除已上传文件 -->
		<el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
            <div class="del-dialog-cnt">确定删除吗？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取 消</el-button>
                <el-button type="primary" @click="handleRemove()">确 定</el-button>
            </span>
        </el-dialog>

	</div>
</template>


<script>
	export default {
		name: 'UserProfile',
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
				uploadUrl: this.$http.baseURL + '/uaa/user/upload',
				headers: { 'Authorization': sessionStorage.getItem('token') },
				params: {},
				materials: [
					{ "id": 1, "name": "营业执照", "memo": "营业执照彩色扫描件", "example": "./data/business_license.jpg", "url": "x.pdf" },
					{ "id": 2, "name": "法定代表人身份证", "memo": "法定代表人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 3, "name": "注册人身份证", "memo": "注册人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 4, "name": "授权委托书", "memo": "法人授权委托书扫描件", "example": "./data/authorization.jpg", "url": "" }
				],
				delDialog: false,
				loading: false,
				idx: -1
			}
		},
		created() {
			// 获取用户信息
			this.loading = true;
			this.$http.get('/uaa/user/info/detail').then((data) => {
				this.form = data;
				this.loading = false;
			}).catch(() => this.loading = false);
		},
		methods: {
			// check the file type and size
			handleBeforeUpload(res) {
                const isJPG = res.type === 'image/jpeg';
                const isLt2M = res.size / 1024 / 1024 < 2;
                if (!isJPG) {
                  this.$message.error('上传材料只能是jpg格式!');
                  return false;
                }
                if (!isLt2M) {
                  this.$message.error('上传材料不能超过2MB!');
                  return false;
                }
			},
			handleSuccess(row, index) {
				this.$message.success('上传成功');
			},
			handleError(error) {
				this.$message.error('上传失败' + error);
			},
			showDelDialog(value) {
				this.idx = value;
				this.delDialog = true;
			},
			handleRemove() {
				let row = this.materials[this.idx];
				this.$http.post('/uaa/corp/material/del?id=' + row.materialId).then((data) => {
					row.materialId = '';
					row.materialUrl = '';
					this.materials[this.idx] = row;
					this.delDialog = false;
				}).catch(() => this.loading = false);
			},
			onSave() {
				this.$refs.form.validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = this.form;
						// params.areas = params.area.toString();
						this.$http.post('/uaa/user/update', params).then((data) => {
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
