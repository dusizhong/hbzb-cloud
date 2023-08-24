<template>
	<div v-loading="loading">

		<div class="container">

			<!-- 标题栏 -->
			<h4 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">项目详情</h4>

			<el-collapse v-model="collapse">

				<!-- 项目信息 -->
				<el-collapse-item title="项目信息" name="1">
					<el-form label-width="130px">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="项目编号" class="detail_item">{{ project.serialNo }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="项目名称" class="detail_item">{{ project.name }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="采购单位" class="detail_item">{{ project.tendereeName }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购地址" class="detail_item">{{ project.address }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="采购联系人" class="detail_item">{{ project.tendereeContactPerson }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购联系电话" class="detail_item">{{ project.tendereeContactNumber }}</el-form-item>
							</el-col>
						</el-row>
					<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="所在辖区" class="detail_item">{{ project.area }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购交易分类" class="detail_item">{{ project.procurementCategory }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="交易组织形式" class="detail_item">{{ project.organizeType }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="采购方式" class="detail_item">{{ project.procurementType }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="行政监督部门" class="detail_item">{{ project.supervisor }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="公共资源" class="detail_item">{{ project.resourcer }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="投资项目统一代码" class="detail_item">{{ project.investmentCode }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="投资主体性质" class="detail_item">{{ project.investorAttribute }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="是否PPP项目" class="detail_item">{{ project.isPPP }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="代理机构" class="detail_item">{{ project.agencyName }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="申报责任人" class="detail_item">{{ project.creatorName }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="审批人" class="detail_item">{{ project.approverName }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="状态" class="detail_item">{{ project.status }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="更新时间" class="detail_item">{{ project.updateTime }}</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="创建时间" class="detail_item">{{ project.createTime }}</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="项目内容" class="detail_item">{{ project.content }}</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</el-collapse-item>

				<!-- 分包信息 -->
				<el-collapse-item title="分包信息" name="2">
					<el-table stripe border :data="bidSections" v-loading="loading">
						<el-table-column prop="id" label="序号" width="50"></el-table-column>
						<el-table-column prop="serialNo" label="分包编号" width="200"></el-table-column>
						<el-table-column prop="name" label="分包名称"></el-table-column>
						<el-table-column prop="price" label="单价（元）" width="120"></el-table-column>
						<el-table-column prop="num" label="采购数量" width="120"></el-table-column>
						<el-table-column prop="totalBudget" label="预算总额（元）" width="120"></el-table-column>
						<el-table-column prop="procurementType" label="采购方式" width="120"></el-table-column>
					</el-table>
				</el-collapse-item>

				<!-- 附件信息 -->
				<el-collapse-item title="附件信息" name="3">
					<el-table :data="materials">
						<el-table-column prop="id" label="序号" width="80"></el-table-column>
						<el-table-column prop="name" label="电子件名称" width="240"></el-table-column>
						<el-table-column prop="memo" label="说明" width="350"></el-table-column>
						<el-table-column prop="url" label="电子件">
							<template slot-scope="scope">
								<a v-if="scope.row.url" :href="scope.row.url" target="_blank"><i class="el-icon-picture"></i> {{scope.row.name}}.jpg</a>
								<span v-else>未上传</span>
							</template>
						</el-table-column>
					</el-table>
				</el-collapse-item>
			</el-collapse>

			<!-- 底部按钮 -->
			<div style="margin-top:20px; text-align:center">
				<el-button>关闭</el-button>
			</div>
		</div>

	</div>
</template>


<script>
	export default {
		name: 'ProjectDetail',
		data() {
			return {
				collapse: ['1'],
				project: {},
				bidSections: [],
				materials: [
					{ "id": 1, "name": "(*)采购计划相关附件", "memo": "采购计划相关附件", "url": "x.pdf" },
					{ "id": 2, "name": "(*)委托协议（合同）", "memo": "请上传PDF格式文件", "url": "" },
					{ "id": 3, "name": "招标文件（首页盖公章）", "memo": "招标文件（首页盖公章）", "url": "" },
					{ "id": 4, "name": "承诺书", "memo": "", "url": "" },
					{ "id": 5, "name": "其他", "memo": "", "url": "" }
				],
				loading: false
			}
		},
		created() {
			fetch('./data/tenderProjects.json').then(res => res.json()).then(data => {
				this.project = data.tenderProjects[0];
			});
			fetch('./data/bidSections.json').then(res => res.json()).then(data => {
				this.bidSections = data.bidSections;
			});
		},
		methods: {
		}
	}
</script>


<style scoped>
	.detail_item {
		margin-bottom: -30px;
	}
</style>
