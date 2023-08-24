<template>
	<div v-loading="loading">

		<el-card shadow="hover" style="margin-bottom:20px; font-size:16px;">
			<div style="margin-bottom:5px;">项目编号：<b>{{project.serialNo}}</b></div>
			<div>项目名称：<b>{{project.name}}</b></div>
		</el-card>

		<el-card shadow="hover">
			<el-form :model="form" :rules="rules" ref="form" label-width="130px">
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="标段编号" prop="serialNo">
							<el-input v-model="form.serialNo" placeholder="请填写"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="标段名称" prop="name">
							<el-input v-model="form.name" placeholder="请填写"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="所属辖区" prop="area">
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
						<el-form-item label="预算总价（元）" prop="budget">
							<el-input v-model="form.budget" placeholder="请填写"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12">
                        <el-form-item label="开标时间" prop="bidOpenTime">
                            <el-date-picker v-model="form.bidOpenTime" type="datetime" placeholder="选择日期时间（即投标文件递交截止时间）" value-format="yyyy-MM-dd HH:mm:ss" style="width:100%"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="开标场地" prop="bidOpenPlace">
                            <el-input v-model="form.bidOpenPlace" placeholder="请填写"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="标段内容" prop="content">
							<el-input type="textarea" rows="5" v-model="form.content"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="投标人资格条件" prop="qualification">
							<el-input type="textarea" rows="5" v-model="form.qualification"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="投标保证金" prop="needGuarantee">
							<el-radio-group v-model="form.needGuarantee">
								<el-radio label="true">有</el-radio>
								<el-radio label="false">无</el-radio>
							</el-radio-group>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="采用网上开评标" prop="bidOnline">
							<el-radio-group v-model="form.bidOnline">
								<el-radio label="true">是</el-radio>
								<el-radio label="false">否</el-radio>
							</el-radio-group>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
		</el-card>

		<div style="margin-top:20px; text-align:center">
			<el-button @click="onCancel">取消</el-button>
			<el-button type="primary" @click="onSave">保存</el-button>
		</div>

	</div>
</template>


<script>
	export default {
		name: 'SectionEditor',
		props:{ project: null, section: null },
		data() {
			return {
				form: {},
				rules: {
					serialNo: [{ required: true, message: '请填写标段编号', trigger: 'blur' }],
					name: [{ required: true, message: '请填写标段名称', trigger: 'blur' }],
					area: [{ required: true, message: '请选择所在辖区', trigger: 'change' }],
					tradeType: [{ required: true, message: '请选择采购方式', trigger: 'change' }]
				},
				areas: [],
				loading: false,
			}
		},
		created() {
			this.initData();
		},
		methods: {
			async initData() {
				await this.$http.getJson('/data/areas.json').then((data) => {
					this.areas = data.areas;
					this.form = {};
					if(this.section) {
						this.form = this.section;
					}
				});
			},
			onSave() {
				this.$refs.form.validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = Object.assign({}, this.form);
						params.projectUid = this.project.uid;
						params.area = params.area.toString();
						this.$http.post('/tas/section/create', params).then((data) => {
							this.loading = false;
							this.$refs.form.resetFields();
							this.$message.success('保存成功！');
							this.$emit('onSave', 'success');
						}).catch(() => this.loading = false);
					} else {
						return false;
					}
				})
			},
			onCancel() {
				this.$emit('onCancel', 'onCancel');
			}
		}
	}
</script>
