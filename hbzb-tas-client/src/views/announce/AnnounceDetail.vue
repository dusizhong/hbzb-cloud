<template>
	<div>

		<div class="container">

			<!-- 标题栏 -->
			<h4 style="margin-bottom:20px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">公告详情</h4>

			<el-collapse v-model="collapse">
				<!-- 项目信息 -->
				<el-collapse-item title="项目信息" name="1">
					<el-form label-position="left">
						<!-- <el-row :gutter="10">
							<el-col :span="24"> -->
								<el-form-item label="项目编号"><b>{{ project.serialNo }}</b></el-form-item>
							<!-- </el-col>
						</el-row>
						<el-row :gutter="10">
							<el-col :span="24"> -->
								<el-form-item label="项目名称"><b>{{ project.name }}</b></el-form-item>
							<!-- </el-col>
						</el-row>
						<el-row :gutter="10">
							<el-col :span="12"> -->
								<el-form-item label="采购单位"><b>{{ project.tendereeName }}</b></el-form-item>
							<!-- </el-col>
							<el-col :span="12"> -->
								<el-form-item label="采购联系电话"><b>{{ project.tendereeName }}</b></el-form-item>
							<!-- </el-col>
						</el-row> -->
					</el-form>
				</el-collapse-item>

				<!-- 分包信息 -->
				<el-collapse-item title="分包信息" name="2">
					<el-table stripe border :data="bidSections" v-loading="loading">
						<el-table-column prop="id" label="序号" width="50"></el-table-column>
						<el-table-column prop="serialNo" label="分包编号" width="200"></el-table-column>
						<el-table-column prop="name" label="分包名称"></el-table-column>
						<!-- <el-table-column prop="price" label="单价（元）" width="120"></el-table-column>
						<el-table-column prop="num" label="采购数量" width="120"></el-table-column> -->
						<el-table-column prop="totalBudget" label="预算总额（元）" width="120"></el-table-column>
						<el-table-column prop="procurementType" label="采购方式" width="120"></el-table-column>
					</el-table>
				</el-collapse-item>

				<!-- 公告信息 -->
				<el-collapse-item title="公告信息" name="4">
					<el-form :model="form" :rules="rules" ref="form" label-width="140px">
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="公告标题" prop="title">
									<el-input v-model="form.title" placeholder="请填写"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="发售时间" prop="startTime">
									<el-date-picker v-model="form.startTime" type="datetime" placeholder="报名开始/交易文件发售时间" default-time="09:00:00" style="width:100%"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="截止时间" prop="endTime">
									<el-date-picker v-model="form.endTime" type="datetime" placeholder="报名结束/交易文件截止时间" default-time="23:59:59" style="width:100%"></el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="发布媒体" prop="media">
									<el-select v-model="selectedMedia" multiple placeholder="请选择" style="width:100%">
										<el-option v-for="item in mediaList" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item label="公告内容" prop="content" style="margin-left:-60px;margin-bottom:-30px"></el-form-item>
									<el-input type="textarea" rows="50" v-model="form.content"></el-input>
							</el-col>
						</el-row>
					</el-form>
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
						<el-table-column label="操作" width="160">
							<template slot-scope="scope">
								<el-upload v-if="!scope.row.url" ref="upload" 
									:action="uploadUrl"
									:headers="headers" 
									:data="params" 
									:before-upload="(res)=>{return handleBeforeUpload(res, scope.row.id)}" 
									:on-success="(res)=>{return handleSuccess(res, scope.$index)}" 
									:on-error="handleError" 
									:show-file-list="false"
									>
									<el-button size="mini" plain>上传</el-button>
								</el-upload>
								<el-button v-else size="mini" plain @click="shopDialog(scope.$index)">删除</el-button>
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

<script type="text/javascript">
	export default {
		name: 'AnnounceDetail',
		data() {
			return {
				collapse: ['1','2','3', '4'], // show all collapse
				project: { "id": 1, "serialNo": "WLGS32222020001", "name": "河北西柏坡发电有限责任公司#4炉除尘器布袋采购项目", "area": "石家庄市,平山县", "procurementType": "公开招标", "status": "编辑中" },
				bidSections: [],
				mediaList: [ 
					{ value: '选项1', label: 'E招冀成' },
					{ value: '选项2', label: '河北省公共资源交易中心' },
					{ value: '选项3', label: '河北省政府采购网' }
				],
				selectedMedia: [],
				form: {},
				rules: {
					title: [{ required: true, message: '请填写', trigger: 'blur' }],
					startTime: [{ required: true, message: '请选择', trigger: 'change' }],
					endTime: [{ required: true, message: '请选择', trigger: 'change' }],
					media: [{ required: true, message: '请选择', trigger: 'change' }],
					content: [{ required: true, message: '请填写', trigger: 'blur' }]
				},
			}
		},
		created() {
			fetch('./data/bidSections.json').then(res => res.json()).then(data => {
				this.bidSections = data.bidSections;
			});
			fetch('./data/areas.json').then(res => res.json()).then(data => {
				console.log(data)
				this.areas = data.areas;
			});
			fetch('./data/bidSections.json').then(res => res.json()).then(data => {
				console.log(data)
				this.bidSections = data.bidSections;
			});
		},
		methods: {

		}
	}
</script>


