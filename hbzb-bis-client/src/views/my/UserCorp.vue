<template>
	<div v-loading="loading">

		<div class="container">

			<h3 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background:#F2F6FC">单位信息</h3>

			<div v-if="showTip" style="padding:30px; text-align:center">
				<div style="font-size:20px">{{tip}}</div>
				<el-button type="text" @click="modifyDialog=true">修改信息</el-button>
			</div>

			<el-form :model="form" :rules="rules" ref="form" label-width="140px">
			<el-collapse v-model="collapse" v-if="!showTip">

				<!-- 1.单位信息 -->
				<el-collapse-item title="1.单位信息" name="1">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="单位名称" prop="name">
									<el-input v-model="form.name" placeholder="请填写与营业执照一致的单位全称" :disabled="disabled"></el-input>

								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="统一社会信用代码" prop="uniformCode">
									<el-input v-model="form.uniformCode" placeholder="请填写" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="所属辖区" prop="area">
									<el-cascader v-model="form.area" :options="areas" style="width:100%" :disabled="disabled"></el-cascader>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="国别/地区" prop="nation">
									<el-select v-model="form.nation" style="width:100%" placeholder="请选择" :disabled="disabled">
										<el-option v-for="item in nations" :key="item.value" :label="item.label" :value="item.value" :disabled="disabled"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="单位类型" prop="type">
									<el-select v-model="form.type" style="width:100%" placeholder="请选择" :disabled="disabled">
										<el-option label="企业" key="企业" value="企业"></el-option>
										<el-option label="事业单位" key="事业单位" value="事业单位"></el-option>
										<el-option label="个体" key="个体" value="个体"></el-option>
										<el-option label="其他" key="其他" value="其他"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="单位性质" prop="attribute">
									<el-cascader v-model="form.attribute" :options="attributes" style="width:100%" placeholder="请选择" :disabled="disabled"></el-cascader>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="行业类型" prop="industry">
									<el-select v-model="form.industry" style="width:100%" placeholder="请选择" :disabled="disabled">
										<el-option v-for="item in industries" :key="item.diccode" :label="item.dicname" :value="item.dicname"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="资信等级" prop="creditLevel">
									<el-input v-model="form.creditLevel" placeholder="请填写" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="法人姓名" prop="legalPerson">
									<el-input v-model="form.legalPerson" placeholder="请填写法定代表人姓名" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="法人身份证号" prop="legalPersonIdcardNo">
									<el-input v-model="form.legalPersonIdcardNo" placeholder="请填写法定代表人身份证号" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="成立日期" prop="establishDate">
									<el-date-picker v-model="form.establishDate" value-format="yyyy-MM-dd" placeholder="请选择" style="width:100%" :disabled="disabled"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="营业期限" prop="expiredDate">
									<el-date-picker v-model="form.expiredDate" value-format="yyyy-MM-dd" placeholder="请选择营业执照中营业期限截止日期" style="width:100%" :disabled="disabled"></el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="开户行" prop="bankName">
									<el-input v-model="form.bankName" placeholder="请填写基本户开户行" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="开户行账号" prop="bankAccountNo">
									<el-input v-model="form.bankAccountNo" placeholder="请填写基本户账号" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="注册资本(万元)" prop="registerCapital">
									<el-input v-model="form.registerCapital" placeholder="请填写" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="注册地址" prop="registerAddress">
									<el-input v-model="form.registerAddress" placeholder="请填写" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="经营范围" prop="businessScope">
									<el-input type="textarea" rows="5" v-model="form.businessScope" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
				</el-collapse-item>

				<!-- 2.信息申报责任人信息 -->
				<el-collapse-item title="2.信息申报责任人信息" name="2">
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="姓名" prop="creatorName">
								<el-input v-model="form.creatorName" placeholder="请填写" :disabled="disabled"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="身份证号" prop="creatorIdcardNo">
								<el-input v-model="form.creatorIdcardNo" placeholder="请填写" :disabled="disabled"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="联系电话" prop="creatorPhone">
								<el-input v-model="form.creatorPhone" placeholder="请填写" :disabled="disabled"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="电子邮箱" prop="creatorEmail">
								<el-input v-model="form.creatorEmail" placeholder="请填写" :disabled="disabled"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="24">
							<el-form-item label="通信地址" prop="creatorAddress">
								<el-input v-model="form.creatorAddress" placeholder="请填写有效通信地址及邮编" :disabled="disabled"></el-input>
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
						<el-table-column label="操作" align="center" width="150" v-if="!disabled">
							<template slot-scope="scope">
								<el-upload v-if="!scope.row.url" ref="upload" 
									:action="uploadUrl"
									:headers="uploadHeaders"
									:data="uploadParams"
									:before-upload="(res)=>{return handleBeforeUpload(res, scope.row)}" 
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
				<el-button v-if="!disabled" type="primary" plain @click="onSave()">保存</el-button>
				<el-button v-if="!disabled" type="primary" @click="showSubmitDialog()">提交审核</el-button>
			</div>

		</div>

		<!-- 删除对话框 -->
		<el-dialog title="提示" :visible.sync="delDialog" width="300px" center>
           <span><i class="el-icon-warning" style="color:#E6A23C"></i> 确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delDialog=false">取 消</el-button>
                <el-button type="primary" @click="handleRemove()">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 提交审核对话框 -->
		<el-dialog title="提示" :visible.sync="submitDialog" width="600px" center>
           <span style="font-size:16px"><i class="el-icon-warning" style="color:#E6A23C"></i> 确定提交审核吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="submitDialog=false">取 消</el-button>
                <el-button type="primary" @click="onSubmit()">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改信息对话框 -->
		<el-dialog title="提示" :visible.sync="modifyDialog" width="600px" center>
           <span style="font-size:16px"><i class="el-icon-warning" style="color:#E6A23C"></i> 确定要修改信息吗？修改后请重新提交审核。</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modifyDialog=false">取 消</el-button>
                <el-button type="primary" @click="onModify()">确 定</el-button>
            </span>
        </el-dialog>

	</div>
</template>


<script>
	export default {
		name: 'UserCorp',
		data() {
			return {
				collapse: ['1','2','3'], // show all collapse
				form: {},
				rules: {
					name: [{ required: true, message: '请填写单位名称', trigger: 'blur' }],
					uniformCode: [{ required: true, message: '请填写统一社会信用代码', trigger: 'blur' }],
					area: [{ required: true, message: '请选择所属辖区', trigger: 'change' }],
					creatorName: [{ required: true, message: '请填写您的姓名', trigger: 'blur' }],
					creatorIdcardNo: [{ required: true, message: '请填写您的身份证号码', trigger: 'blur' }],
					creatorPhone: [{ required: true, message: '请填写联系电话', trigger: 'blur' }],
					creatorEmail: [{ required: true, message: '请填写您的电子邮箱', trigger: 'blur' }],
					creatorAddress: [{ required: true, message: '请填写有效通讯地址', trigger: 'blur' }]
				},
				currentUser: {},
				nations: [],
				areas: [],
				attributes: [],
				industries: [],
				uploadUrl: this.$http.baseURL + '/uaa/corp/material/upload',
				uploadHeaders: { 'Authorization': sessionStorage.getItem('token') },
				uploadParams: { "corpUid": "", "name": "" },
				materials: [],
				tip: '',
				showTip: false,
				delDialog: false,
				submitDialog: false,
				modifyDialog: false,
				tipDialog: false,
				disabled: false,
				loading: false,
				idx: -1
			}
		},
		created() {
			this.initData();
		},
		watch: {
			// 监控路由变化，实现在未关闭时由列表页重新传入参数更新页面
			$route(to, from) {
				this.requestAgency();
			}
		},
		methods: {
			// 初始化页面数据
			async initData() {
				this.loading = true;
				this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
				await this.$http.getJson('/data/nations.json').then((data) => {
					this.nations = data.nations;
				});
				await this.$http.getJson('/data/areas.json').then((data) => {
					this.areas = data.areas;
				});
				await this.$http.getJson('/data/attributes.json').then((data) => {
					this.attributes = data.attributes;
				});
				await this.$http.getJson('/data/industries.json').then((data) => {
					this.industries = data;
				});
				await this.$http.getJson('/data/corpMaterials.json').then((data) => {
					this.materials = data.corpMaterials;
				});
				await this.$http.get('/uaa/corp/detail').then((data) => {
					if(data.status == 'SUBMIT') {
						this.tip = '信息已提交，我们将尽快为您审核，请耐心等待！';
						this.showTip = true;
						this.disabled = true;
						this.collapse = [];
					} else if(data.status == 'REJECT') {
						this.tip = '您的单位信息未通过审核，请修改后重新提交！' + data.memo;
						this.showTip = true;
						this.disabled = true;
						this.collapse = [];
					} else if(data.status == 'CERTIFIED') {
						this.tip = '审核通过！';
						this.showTip = true;
						this.disabled = true;
						this.collapse = [];
					} 
					data.area = data.area.split(',');
					data.attribute = data.attribute.split(',');
					this.form = data;
					this.uploadParams.corpUid = data.uid;
					this.fetchMaterials(data.uid);
					this.loading = false;
				}).catch(() => this.loading = false);
			},
			// 获取已上传材料
			fetchMaterials(corpUid) {
				this.loading = true;
				let materials = this.materials;
				this.$http.get('/uaa/corp/material/list?corpUid=' + corpUid).then((data) => {
					for(let m of materials) {
						for(let d of data) {
							if(m.name == d.name) {
								m.corpUid = d.corpUid;
								m.url = d.url;
								break;
							}
						}
					}
					this.materials = materials;
					this.loading = false;
				}).catch(() => this.loading = false);
			},
			// 上传文件
			handleBeforeUpload(res, row) {
				this.uploadParams.name = row.name;
				if(!this.uploadParams.corpUid) {
					this.$message.error('请先保存!');
					return false;
				}
                if(res.type != 'image/jpeg' && res.type != 'image/png' && res.type != 'application/pdf') {
                  this.$message.error('上传材料只能是jpg、png、pdf格式!');
                  return false;
                }
                if(res.size > 1024*1024*2) {
                  this.$message.error('上传材料不能超过2MB!');
                  return false;
                }
			},
			handleSuccess(res, index) {
				this.materials[index].url = res.data.url;
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
				this.loading = true;
				let material = this.materials[this.idx];
				let params = { "corpUid": material.corpUid, "materialName": material.name };
				this.$http.post('/uaa/corp/material/del', params).then((data) => {
					material.url = '';
					this.materials[this.idx] = material;
					this.delDialog = false;
					this.loading = false;
					this.$message.success('删除成功！');
				}).catch(() => this.loading = false);
			},
			onSave() {
				this.$refs.form.validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = Object.assign({}, this.form);
						params.area = params.area.toString();
						params.attribute = params.attribute.toString();
						params.role = 'AGENCY';
						this.$http.post('/uaa/corp/create', params).then((data) => {
							this.loading = false;
							this.form.uid = data.uid;
							this.uploadParams.corpUid = data.uid;
							this.$message.success('保存成功！');
							this.showMaterialCollapse = true;
						}).catch(() => this.loading = false);
					} else {
						return false;
					}
				})
			},
			showSubmitDialog() {
				let isSubmit = true;
				for(let material of this.materials) {
					if(!material.url) {
						isSubmit = false;
						this.$message.warning('您有未上传附件，请上传后提交！');
						break;
					}
				}
				if(isSubmit) {
					this.submitDialog = true;
				}
			},
			onSubmit() {
				this.loading = true;
				let params = { "corpUid": this.form.uid, "status": "SUBMIT" };
				this.$http.post('/uaa/corp/status/update', params).then((data) => {
					this.submitDialog = false;
					this.tip = '信息已提交，我们将尽快为您审核，请耐心等待！';
					this.showTip = true;
					this.disabled = true;
					this.collapse = [];
					this.loading = false;
					this.$message.success('提交成功！');
				}).catch(() => this.loading = false);
			},
			onModify() {
				this.loading = true;
				let params = { "corpUid": this.form.uid, "status": "EDIT" };
				this.$http.post('/uaa/corp/status/update', params).then((data) => {
					this.modifyDialog = false;
					this.tip = '';
					this.showTip = false;
					this.disabled = false;
					this.collapse = ['1','2','3'];
					this.loading = false;
				}).catch(() => this.loading = false);
			}
		}
	}
</script>
