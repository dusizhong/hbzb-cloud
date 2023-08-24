<template>
    <div>
        <div class="container">

            <!-- 标题 -->
            <h3 style="margin-bottom:20px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">招标代理管理</h3>

            <!-- 操作栏 -->
            <el-row :gutter="20" style="margin-bottom:20px">
                <el-col :span="4">
                    <!-- <el-button type="primary" icon="el-icon-plus" @click="goEditor()">新增</el-button> -->
                </el-col>
                <el-col :span="12">
                    <el-input style="width:300px; margin-right:10px" v-model="search_word" placeholder="搜索关键词" prefix-icon="el-icon-search"></el-input>
                    <el-button type="primary" plain icon="search" @click="search()">搜索</el-button>
                    <el-button @click="reset()">重置</el-button>
                </el-col>
                <el-col :span="8" style="text-align:right">
                    <el-radio-group v-model="radioGroup" size="medium">
                        <el-radio-button plain label="全部"></el-radio-button>
                        <el-radio-button label="待审核"></el-radio-button>
                        <el-radio-button label="审核通过"></el-radio-button>
                        <el-radio-button label="审核不通过"></el-radio-button>
                    </el-radio-group>
                </el-col>
            </el-row>

            <!-- 代理列表 -->
            <el-table :header-cell-style="{color:'#606266'}" stripe :data="agencies" v-loading="loading">
                <el-table-column prop="id" label="序号" width="80" align="center"></el-table-column>
                <el-table-column prop="name" label="企业名称"></el-table-column>
                <el-table-column prop="uniformCode" label="统一社会信用代码" width="200" align="center"></el-table-column>
                <el-table-column prop="legalPerson" label="法人" width="100" align="center"></el-table-column>
                <el-table-column prop="role" label="角色" width="100" align="center"></el-table-column>
                <el-table-column prop="status" label="状态" width="80" align="center">
                    <template slot-scope="scope">
                        <strong v-if="scope.row.status=='CERTIFIED'" style="color:#409EFF">已认证</strong>
                        <strong v-else style="color:#909399">待认证</strong>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="goAgencyDetail(scope.row)">管理</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange()" layout="prev, pager, next" :total="100"></el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'AgencyList',
        data() {
            return {
                search_word: '',
                radioGroup: '全部',
                agencies: [],
                cur_page: 1,
                visible: false,
                loading: false,
                idx: -1
            }
        },
        created() {
            this.requestAgencies();
        },
        methods: {
            requestAgencies() {
                this.loading = true;
                this.$http.get('/uaa/corp/list?role=AGENCY').then((data) => {
                    this.agencies = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },
            search() {
                
            },
            reset() {

            },
            handleCurrentChange(val) {
                this.cur_page = val;
                this.requestAgencies();
            },
            goAgencyDetail(row) {
                // this.$router.push({ name: 'AgencyDetail', params: {"row": "456"} }); // 刷新丢失
                // this.$router.push({ path: '/AgencyDetail', query: {"row": "123"} }); // 地址显示
                sessionStorage.setItem('agencyUid', row.uid);
                this.$router.push('/AgencyDetail');
            }
        }
    }
</script>