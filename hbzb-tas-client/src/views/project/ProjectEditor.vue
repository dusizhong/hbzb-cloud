<template>
	<div v-loading="loading">

		<div class="container">
			<h3 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">项目编辑器</h3>

			<div v-if="showTip" style="padding:30px; text-align:center">
				<div style="margin-bottom:30px; font-size:20px">提交成功</div>
				<el-button type="primary">关闭</el-button>
			</div>

			<el-collapse v-model="collapse" v-if="visible">
				<!-- 项目信息 -->
				<el-collapse-item title="1.项目信息" name="1">
					<el-form :model="form" :rules="rules" ref="form" label-width="130px">
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="项目编号" prop="serialNo">
									<el-input v-model="form.serialNo" placeholder="请填写招标文件中的项目编号">
										<template slot="append">生成</template>
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="项目名称" prop="name">
									<el-input v-model="form.name" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="采购单位" prop="tendereeName">
									<el-input v-model="form.tendereeName" placeholder="请选择采购单位">
										<el-button slot="append" icon="el-icon-search" @click="showTenderee()"></el-button>
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="采购地址" prop="address">
									<el-input v-model="form.address" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="采购联系人" prop="contactPerson">
									<el-input v-model="form.contactPerson" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购联系电话" prop="contactPhone">
									<el-input v-model="form.contactPhone" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="所在辖区" prop="area">
									<el-cascader v-model="form.area" :options="areas" style="width:100%"></el-cascader>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购方式" prop="tradeType">
									<el-select v-model="form.tradeType" style="width:100%" placeholder="请选择">
										<el-option label="公开招标" key="公开招标" value="公开招标"></el-option>
										<el-option label="邀请招标" key="邀请招标" value="邀请招标"></el-option>
										<el-option label="竞争性谈判" key="竞争性谈判" value="竞争性谈判"></el-option>
										<el-option label="竞争性磋商" key="竞争性磋商" value="竞争性磋商"></el-option>
										<el-option label="询价" key="询价" value="询价"></el-option>
										<el-option label="单一来源" key="单一来源" value="单一来源"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="采购交易分类" prop="tradeCategory">
									<el-select v-model="form.tradeCategory" style="width:100%" placeholder="请选择">
										<el-option label="工程类" key="工程类" value="工程类"></el-option>
										<el-option label="服务类" key="服务类" value="服务类"></el-option>
										<el-option label="货物类（含药品集中采购）" key="货物类（含药品集中采购）" value="货物类（含药品集中采购）"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="交易组织形式" prop="organizeType">
									<el-select v-model="form.organizeType" style="width:100%" placeholder="请选择">
										<el-option label="自行招标" key="自行招标" value="自行招标"></el-option>
										<el-option label="委托招标" key="委托招标" value="委托招标"></el-option>
										<el-option label="采购中心" key="采购中心" value="采购中心"></el-option>
										<el-option label="其他" key="其他" value="其他"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="行政监督部门" prop="supervisor">
									<el-select v-model="form.supervisor" multiple filterable allow-create default-first-option placeholder="请选择" style="width:100%">
										<el-option v-for="item in supervisors" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="公共资源" prop="resourcer">
									<el-select v-model="form.resourcer" filterable placeholder="请选择" style="width:100%">
										<el-option v-for="item in resourcers" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="投资项目统一代码" prop="investmentCode">
									<el-input v-model="form.investmentCode" placeholder="代码长度：24位，代码格式：年份代码-地区（部门）代码-行业代码-项目类型代码-流水号"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="投资主体性质" prop="investorType">
									<el-select v-model="form.investorType" style="width:100%" placeholder="请选择">
										<el-option label="政府投资" key="政府投资" value="政府投资"></el-option>
										<el-option label="企业投资" key="企业投资" value="企业投资"></el-option>
										<el-option label="其他" key="其他" value="其他"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="是否PPP项目" prop="isPPP">
									<el-radio-group v-model="form.isPPP">
										<el-radio :label="1">是</el-radio>
										<el-radio :label="0">否</el-radio>
									</el-radio-group>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="项目内容" prop="content">
									<el-input type="textarea" rows="5" v-model="form.content"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="推送平台" prop="platform">
									<el-select v-model="form.platform" multiple placeholder="请选择" style="width:100%">
										<el-option v-for="item in platforms" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<!-- <el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="代理机构" prop="agencyName">
									<el-input v-model="form.tendererContactPerson" :disabled="true"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="申报责任人" prop="createrName">
									<el-input v-model="form.createrName" :disabled="true"></el-input>
								</el-form-item>
							</el-col>
						</el-row> -->
					</el-form>
				</el-collapse-item>

				<!-- 附件信息 -->
				<el-collapse-item title="附件信息" name="2">
					<el-table :header-cell-style="{background:'#d3dce6',color:'#333'}" :data="materials">
						<el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
						<el-table-column prop="name" label="电子件名称" align="center" width="300">
							<template slot-scope="scope">
								<div><span v-if="scope.row.needed" style="color:#F56C6C">* </span>{{scope.row.name}}</div>
							</template>
						</el-table-column>
						<el-table-column prop="memo" label="说明" >
							<template slot-scope="scope">
								<div>{{scope.row.memo}}<span v-if="scope.row.example">（<a :href="scope.row.example" target="_blank">样例</a>）</span></div>
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
								<el-button v-else type="text" size="mini" plain @click="delMaterial(scope.$index)">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</el-collapse-item>
			</el-collapse>

			<!-- 底部按钮 -->
			<div style="margin-top:20px; text-align:center" v-if="visible">
				<el-button type="primary" plain @click="onSave">保存</el-button>
				<el-button @click="onSubmit('APPROVAL')">无须审核</el-button>
				<el-button type="primary" @click="onSubmit('SUBMIT')">提交审核</el-button>
			</div>

		</div>

		<!-- 选择采购单位 -->
		<!-- <el-dialog title="请选择采购单位" center width="70%" :visible.sync="tendereeDialogVisible">
			<el-row :gutter="20" style="margin-bottom:20px">
				<el-col :span="24">
					<el-input style="width:300px; margin-right:10px" v-model="search_word" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
					<el-button type="primary" plain icon="search" @click="search">搜索</el-button>
					<el-button @click="reset">重置</el-button>
				</el-col>
			</el-row>
			<el-table ref="singleTable" highlight-current-row :data="tenderees" @current-change="selTenderee()">
				<el-table-column width="55">
                    <template slot-scope="scope"><el-radio v-model="tendereeRadio" label={scope.$row.id}></el-radio></template>
                </el-table-column>
				<el-table-column prop="id" label="序号" align="center" width="80"></el-table-column>
				<el-table-column prop="name" label="单位名称"></el-table-column>
				<el-table-column prop="uniformCode" label="统一社会信用代码" align="center" width="200"></el-table-column>
				<el-table-column prop="contactPerson" label="联系人" align="center" width="120"></el-table-column>
				<el-table-column prop="contactNumber" label="联系电话" align="center" width="200"></el-table-column>
			</el-table>
			<div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="100"></el-pagination>
            </div>
            <div style="margin-top:20px; text-align:center">
                <el-button>取消</el-button>
                <el-button type="primary" @click="goEditor()">确定</el-button>
            </div>
		</el-dialog> -->

        <!-- 删除附件对话框 -->
		<el-dialog title="提示" :visible.sync="delMaterialDialog" width="300px" center>
           <span><i class="el-icon-warning" style="color:#E6A23C"></i> 确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delMaterialDialog=false">取 消</el-button>
                <el-button type="primary" @click="onDelMaterial()">确 定</el-button>
            </span>
        </el-dialog>

	</div>
</template>


<script>
	export default {
		name: 'ProjectEditor',
		data() {
			return {
				collapse: ['1','2'],
				form: {},
				rules: {
					serialNo: [{ required: true, message: '请填写项目编号', trigger: 'blur' }],
					name: [{ required: true, message: '请填写项目名称', trigger: 'blur' }],
					tendereeName: [{ required: true, message: '请填写采购单位', trigger: 'blur' }],
					area: [{ required: true, message: '请选择所在辖区', trigger: 'change' }],
					tradeType: [{ required: true, message: '请选择采购方式', trigger: 'change' }]
				},
				areas: [],
				supervisors: [],
				resourcers: [],
				platforms:[{"label":"河北省公共资源交易中心", "value":"河北省公共资源交易中心"}, {"label":"中国公共资源交易中心", "value":"中国公共资源交易中心"}],
				uploadUrl: this.$http.baseURL + '/tas/project/material/upload',
				uploadHeaders: { 'Authorization': sessionStorage.getItem('token') },
				uploadParams: { "projectUid": "", "materialName": "" },
				materials: [],
				projectUid: '',
				disabled: false,
				loading: false,
				delMaterialDialog: false,
				materialIdx: -1,
				showTip: false,
				visible: true
			}
		},
		created() {
			this.initData();
		},
		watch: {
			// 监控路由变化，实现在未关闭时由列表页重新传入参数更新页面
			$route(to, from) {
				this.initData();
			}
		},
		methods: {
			// 初始化
			async initData() {
				this.form = {};
				this.materials = [];
				this.projectUid = sessionStorage.getItem('projectUid');
				await this.$http.getJson('/data/areas.json').then((data) => {
					this.areas = data.areas;
				});
				await this.$http.getJson('/data/supervisors.json').then((data) => {
					this.supervisors = data.supervisors;
				});
				await this.$http.getJson('/data/resourcers.json').then((data) => {
					this.resourcers = data.resourcers;
				});
				await this.$http.getJson('/data/projectMaterials.json').then((data) => {
					this.materials = data.projectMaterials;
				});
				if(this.projectUid != 'null') {
					this.loading = true;
					this.$http.get('/tas/project/detail?projectUid=' + this.projectUid).then((data) => {
						data.area = data.area.split(',');
						data.supervisor = data.supervisor.split(',');
						data.resourcer = data.resourcer.split(',');
						data.platform = data.platform.split(',');
						this.form = data;
						this.uploadParams.projectUid = this.projectUid;
						this.fetchMaterials();
						this.loading = false;
					}).catch(() => this.loading = false);
				} else console.log('create new project');
			},

			// 保存项目
			onReset() {
				this.form = {};
				this.$refs.form.resetFields();
			},
			onSave() {
				this.$refs.form.validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = Object.assign({}, this.form);
						params.area = params.area.toString();
						params.supervisor = params.supervisor.toString();
						params.resourcer = params.resourcer.toString();
						params.platform = params.platform.toString();
						this.$http.post('/tas/project/create', params).then((data) => {
							this.loading = false;
							this.form.uid = data.uid;
							this.projectUid = data.uid;
							this.uploadParams.projectUid = data.uid;
							sessionStorage.setItem("projectUid", data.uid);
							this.$message.success('保存成功！');
						}).catch(() => this.loading = false);
					} else {
						return false;
					}
				})
			},

			// 获取已上传材料
			fetchMaterials() {
				this.loading = true;
				let materials = this.materials;
				this.$http.get('/tas/project/material/list?projectUid=' + this.projectUid).then((data) => {
					for(let m of materials) {
						for(let d of data) {
							if(m.name == d.name) {
								m.projectUid = d.projectUid;
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
				if(!this.uploadParams.projectUid) {
					this.$message.warning('请先保存!');
					return false;
				}
                if(res.type != 'image/jpeg' && res.type != 'image/png' && res.type != 'application/pdf') {
                  this.$message.warning('上传材料只能是jpg、png、pdf格式!');
                  return false;
                }
                if(res.size > 1024*1024*5) {
                  this.$message.warning('上传材料不能超过5MB!');
                  return false;
                }
			},
			handleSuccess(res, index) {
				if(res.code == '200') {
					this.materials[index].url = res.data.url;
					this.$message.success('上传成功');
				} else this.$message.error(res.message);
			},
			handleError(error) {
				this.$message.error('上传失败' + error);
			},
			delMaterial(value) {
				this.materialIdx = value;
				this.delMaterialDialog = true;
			},
			onDelMaterial() {
				this.loading = true;
				let material = this.materials[this.materialIdx];
				let params = { "projectUid": material.projectUid, "name": material.name };
				this.$http.post('/tas/project/material/del', params).then((data) => {
					material.url = '';
					this.materials[this.materialIdx] = material;
					this.delMaterialDialog = false;
					this.loading = false;
					this.$message.success('删除成功！');
				}).catch(() => this.loading = false);
			},

			// 提交审核
			onSubmit(status) {
				if(this.projectUid == 'null') {
					this.$message.warning('请先保存!');
					return false;
				}
				let needUpload = false;
				for(let m of this.materials) {
					if(m.needed) {
						if(!m.url) {
							needUpload = true;
							break;
						}
					}
				}
				if(needUpload) {
					this.$message.warning('请上传必须的附件!');
					return false;
				}
				this.loading = true;
				let params = { "projectUid": this.projectUid, "status": status };
				this.$http.post('/tas/project/status/update', params).then((data) => {
					this.form = {};
					this.loading = false;
					this.visible = false;
					this.showTip = true;
					this.$message.success('提交成功！');
				}).catch(() => this.loading = false);
			}
		}
	}
</script>
