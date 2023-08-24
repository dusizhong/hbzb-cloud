<template>
	<div v-loading="loading">

		<div class="container">
			<h3 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">招标代理详情</h3>
<el-form :model="form" :rules="rules" ref="form" label-width="140px">
			<el-collapse v-model="collapse">
				<!-- 1.单位信息 -->
				<el-collapse-item title="1.单位信息" name="1">
					
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="单位名称" prop="name">
									<el-input v-model="form.name" placeholder="请填写与营业执照一致的单位全称"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="统一社会信用代码" prop="uniformCode">
									<el-input v-model="form.uniformCode" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单位类型" prop="type">
									<el-select v-model="form.type" style="width:100%" placeholder="请选择">
										<el-option label="企业" key="企业" value="企业"></el-option>
										<el-option label="事业单位" key="事业单位" value="事业单位"></el-option>
										<el-option label="个体" key="个体" value="个体"></el-option>
										<el-option label="其他" key="其他" value="其他"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="国别/地区" prop="nation">
									<el-select v-model="form.nation" style="width:100%" placeholder="请选择">
										<el-option v-for="item in nations" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单位性质" prop="attribute">
									<el-cascader v-model="form.attribute" :options="attributes" style="width:100%" placeholder="请选择"></el-cascader>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="行业类型" prop="industry">
									<el-select v-model="form.industry" style="width:100%" placeholder="请选择">
										<el-option v-for="item in industries" :key="item.diccode" :label="item.dicname" :value="item.dicname"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="资信等级" prop="creditLevel">
									<el-input v-model="form.creditLevel" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="所属地区" prop="area">
									<el-cascader v-model="form.area" :options="areas" style="width:100%"></el-cascader>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="法定代表人" prop="legalPerson">
									<el-input v-model="form.legalPerson" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="注册资本(万元)" prop="capital">
									<el-input v-model="form.capital" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="成立日期" prop="establishDate">
									<el-date-picker v-model="form.establishDate" value-format="yyyy-MM-dd" placeholder="请选择" style="width:100%"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="营业期限" prop="businessTerm">
									<el-input v-model="form.businessTerm" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="开户行" prop="openBank">
									<el-input v-model="form.openBank" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="开户行基本户账号" prop="bankAccountNo">
									<el-input v-model="form.bankAccountNo" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="注册地址" prop="registerAddress">
									<el-input v-model="form.registerAddress" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="经营范围" prop="businessScope">
									<el-input type="textarea" rows="5" v-model="form.businessScope"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
					
				</el-collapse-item>

				<!-- 2.注册人信息（信息申报责任人） -->
				<el-collapse-item title="2.注册人信息（信息申报责任人）" name="2">
					
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="注册人姓名" prop="creatorName">
								<el-input v-model="form.creatorName" placeholder="请填写"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="身份证号" prop="creatorIdcardNo">
								<el-input v-model="form.creatorIdcardNo" placeholder="请填写"></el-input>
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
								<el-button v-else type="text" size="mini" plain @click="showDelDialog(scope.$index)">删除</el-button>
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

		<!-- 删除已上传文件 -->
		<el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
            <div class="del-dialog-cnt">确定删除吗？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取 消</el-button>
                <el-button type="primary" @click="handleRemove()">确 定</el-button>
            </span>
        </el-dialog>

		<!-- 提示对话框 -->
		<el-dialog title="提示" center :visible.sync="tipDialog">
			<p>您的单位信息正在审核中，请耐心等待。</p>
		</el-dialog>

	</div>
</template>


<script>
	export default {
		name: 'AgencyDetail',
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
				agency: {},
				attributes: [],
				nations: [],
				industries: [],
				areas: [],
				uploadUrl: this.$http.baseURL + '/uaa/corp/upload',
				headers: { 'Authorization': sessionStorage.getItem('token') },
				params: {},
				materials: [
					{ "id": 1, "name": "营业执照", "memo": "营业执照彩色扫描件", "example": "./data/business_license.jpg", "url": "x.pdf" },
					{ "id": 2, "name": "法定代表人身份证", "memo": "法定代表人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 3, "name": "注册人身份证", "memo": "注册人身份证扫描件", "example": "./data/idcard.jpg", "url": "" },
					{ "id": 4, "name": "授权委托书", "memo": "法人授权委托书扫描件", "example": "./data/authorization.jpg", "url": "" }
				],
				delDialog: false,
				tipDialog: false,
				loading: false,
				idx: -1
			}
		},
		created() {
			// 获取用户详情
			this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
			// 获取页面传参
			// console.log(this.$route.params); // 刷新丢失
			// console.log(this.$route.query); // 地址显示
			// 获取代理信息
			this.requestAgency();
			// 获取国别地区
			this.$http.getJson('/data/nations.json').then((data) => {
				this.nations = data.nations;
			});
			// 获取单位性质
			this.$http.getJson('/data/attributes.json').then((data) => {
				this.attributes = data.attributes;
			});
			// 获取行业类型
			this.$http.getJson('/data/industries.json').then((data) => {
				this.industries = data;
			});
			// 获取所属地区
			this.$http.getJson('/data/areas.json').then((data) => {
				this.areas = data.areas;
			});
		},
		watch: {
			// 监控路由变化，实现在未关闭时传入新参数更新页面
			$route(to, from) {
				this.requestAgency();
			}
		},
		methods: {
			// 获取代理详情
			requestAgency() {
				this.loading = true;
				let agencyUid = sessionStorage.getItem('agencyUid');
				this.$http.get('/uaa/corp/detail?corpUid=' + agencyUid).then((data) => {
					data.area = data.area.split(',');
					data.attribute = data.attribute.split(',');
					this.form = data;
					this.loading = false;
				}).catch(() => this.loading = false);
			},
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
