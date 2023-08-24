<template>
    <div v-loading="loading">

        <!-- 警告消息 -->
        <el-alert v-if="tipAlert" type="warning" show-icon style="margin-bottom:10px">
            <template>
                <span style="font-size:14px">{{tip}}</span>
                <el-button type="text" @click="goUserCorp()">去提交</el-button>
            </template>
        </el-alert>

        <!-- 公告、代办 -->
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover">
                    <div slot="header">
                        <span>公告</span>
                        <el-button style="float:right; padding:3px 0" type="text">更多</el-button>
                    </div>
                    <el-table :data="messages" :show-header="false">
                        <el-table-column prop="id" label="序号" width="80"></el-table-column>
                        <el-table-column prop="title" label="内容"></el-table-column>
                        <el-table-column prop="createTime" label="时间" width="160"></el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <div slot="header">
                        <span>代办事宜</span>
                        <el-button style="float:right; padding:3px 0" type="text">更多</el-button>
                    </div>
                    <el-table :data="messages" :show-header="false">
                        <el-table-column prop="id" label="序号" width="80"></el-table-column>
                        <el-table-column prop="title" label="内容"></el-table-column>
                        <el-table-column prop="createTime" label="时间" width="160"></el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>

        <!-- 统计图标 -->
        <el-row :gutter="20" style="margin-top:20px">
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="bar" style="width:100%; height:300px" canvasId="bar" :data="data2" type="bar" :options="options"></schart>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="line" style="width:100%; height:300px" canvasId="line" :data="data" type="line" :options="options2"></schart>
                </el-card>
            </el-col>
        </el-row>

        <!-- 提示对话框 -->
        <el-dialog title="提示" :visible.sync="tipDialog">
            <!-- <i class="el-icon-warning" style="font-size:16px; color:#E6A23C"> {{tip}}</i> -->
            <span style="font-size:16px;"> {{tip}}</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="tipDialog=false">取 消</el-button>
                <el-button type="primary" @click="goUserCorp()">好的，现在就去</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import Schart from 'vue-schart';
    import bus from '../components/bus';
    export default {
        name: 'dashboard',
        data() {
            return {
                messages: [{
                        id: 10,
                        title: '今晚0:00至4:00对系统升级，届时将影响保函申请和查询。',
                        createTime: '2019-03-18 12:30:30',
                    },
                    {
                        id: 11,
                        title: '未进行实名认证的客户，请抓紧时间提交实名认证信息。',
                        createTime: '2019-03-12 09:30:38',
                    },
                    {
                        id: 12,
                        title: '保函申请流程已统一调整为审批后付费。',
                        createTime: '2019-03-10 12:38:08',
                    },
                    {
                        id: 13,
                        title: '新增微信支付通道，用户可以通过微信扫描快速支付。',
                        createTime: '2019-02-28 11:18:48',
                    },
                    {
                        id: 14,
                        title: '新上线客户月度统计报表，客户增长情况一看即知。',
                        createTime: '2019-02-26 12:30:14',
                    }
                ],
                data: [
                    { name: '1月', value: 33 },
                    { name: '2月', value: 10 },
                    { name: '3月', value: 30 }
                ],
                data2: [
                    { name: '石家庄', value: 33 },
                    { name: '保定', value: 10 },
                    { name: '衡水', value: 30 },
                    { name: '邢台', value: 20 },
                    { name: '邯郸', value: 40 },
                    { name: '沧州', value: 30 },
                    { name: '廊坊', value: 20 },
                    { name: '唐山', value: 60 },
                    { name: '承德', value: 50 },
                    { name: '秦皇岛', value: 40 },
                    { name: '张家口', value: 30 }
                ],
                options: {
                    title: '各地区业务量统计',
                    showValue: true,
                    fillColor: 'rgb(45, 140, 240)',
                    bottomPadding: 30,
                    topPadding: 30
                },
                options2: {
                    title: '月度业务量统计',
                    fillColor: '#FC6FA1',
                    axisColor: '#008ACD',
                    contentColor: '#EEEEEE',
                    bgColor: '#F5F8FD',
                    bottomPadding: 30,
                    topPadding: 30
                },
                tip: '',
                tipAlert: false,
                tipDialog: false,
                loading: false
            }
        },
        components: {
            Schart
        },
        created() {
            let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            if(currentUser.corpUid) {
                this.loading = true;
                this.$http.get('/uaa/corp/detail').then((data) => {
                    this.loading = false;
                    if(data.status != 'CERTIFIED') {
                        this.tip = '您好，您的企业信息未审核，请先提交审核！';
                        this.tipAlert = true;
                        this.tipDialog = true;
                    }
                }).catch(() => this.loading = false);
            } else {
                this.tip = '您好，您的企业信息不存在，请先提交企业信息！';
                this.tipAlert = true;
                this.tipDialog = true;
            }
            this.handleListener();
        },
        activated(){
            this.handleListener();
        },
        deactivated(){
            window.removeEventListener('resize', this.renderChart);
            bus.$off('collapse', this.handleBus);
        },
        methods: {
            goUserCorp() {
                this.tipDialog = false;
                this.$router.push('/UserCorp')
            },
            handleListener() {
                bus.$on('collapse', this.handleBus);
                // 调用renderChart方法对图表进行重新渲染
                window.addEventListener('resize', this.renderChart)
            },
            handleBus(msg) {
                setTimeout(() => {
                    this.renderChart()
                }, 300);
            },
            renderChart() {
                this.$refs.bar.renderChart();
                this.$refs.line.renderChart();
            }
        }
    }
</script>


<style scoped>
   
</style>
