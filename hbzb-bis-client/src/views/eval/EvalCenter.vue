<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h1 style="margin-bottom:10px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">评标中心</h1>

            <!-- 电子时钟 -->
            <el-card shadow="hover" style="text-align:center">
                <div style="font-size:16px; font-weight:bold; margin-bottom:10px;">{{clock}}</div>
                <div style="font-size:12px">国家授时中心</div>
                <div style="float:right; font-size:18px; color:#409EFF; margin-top:-35px"><span class="blink">正在评标</span></div>
                <!-- <div style="float:right; font-size:18px; color:#F56C6C; margin-top:-40px">剩余时间：{{timer}}秒</div> -->
            </el-card>

            <!-- 标段信息 -->
            <el-row :gutter="20">
                <el-col :span="18">
                    <!-- 标段信息 -->
                    <el-card shadow="hover" style="margin-top:30px; font-size:18px; line-height:36px">
                        <div>标段编号：<b>{{tenderSection.serialNo}}</b>（{{tenderSection.tradeType}}）</div>
                        <div>标段名称：<b>{{tenderSection.name}}</b></div>
                        <div>招 标 人：<b>中国邮政储蓄银行股份有限公司石家庄市分行</b></div>
                        <div>招标代理：<b>{{tenderSection.agencyName}}</b></div>
                        <div>评标时间：<b>{{tenderSection.bidEvalTime}}</b>（时长：{{tenderSection.bidEvalPeriod}}分钟）<span v-if="showTimer" style="float:right; color:#F56C6C">剩余时间：{{timer}}秒</span></div>
                        <div>招标文件：<b><i class="el-icon-document"></i><a href="./mock/tenderFile.pdf" target="_blank">查看招标文件</a></b> <el-button type="primary" size="mini" @click="showStep('showWaiting')" style="margin-left:20px">查看评标办法</el-button></div>
                    </el-card>
                </el-col>
                <el-col :span="6">
                    <!-- 实时消息 -->
                    <el-card shadow="hover" style="margin-top:30px;">
                        <el-table :data="bidExperts" :header-cell-style="{color:'#606266'}" max-height="250" size="mini" v-loading="loading">
                            <el-table-column>
                                <template slot="header" slot-scope="scope">
                                    <div style="height:30px; width:100%">在线人员（7）<span style="float:right"><i class="el-icon-refresh"></i></span></div>
                                </template>
                                <template slot-scope="scope">
                                    <div style="display:-webkit-flex; display:flex; align-items:center; font-size:14px">
                                        <img style="width:36px; height:36px; margin-right:10px" src="../../assets/avatar.png">
                                        <span style="flex-grow:1; font-weight:bold; color:#409EFF">{{scope.row.name}}（{{scope.row.role}}）</span>
                                        <span style="color:#666">{{scope.row.status}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
            </el-row>
            
            <!-- 操作流程按钮 -->
            <el-row style="margin:30px auto">
                <el-col :span="20">

                <el-button v-if="show=='EvalStart'" type="primary" @click="showEvalStart()">评标准备</el-button>
                <el-button v-else @click="showEvalStart()" :disable="disable">评标准备</el-button>

                <el-button v-if="show=='EvalExpert'" type="primary" @click="showEvalExpert()">推荐评标组长</el-button>
                <el-button v-else @click="showEvalExpert()" :disable="disable">推荐评标组长</el-button>

                <el-button @click="showStep('showPrepare')">资格审查</el-button>

                <el-button v-if="show=='EvalPrimary'" type="primary" @click="showEvalPrimary()">初步评审</el-button>
                <el-button v-else @click="showEvalPrimary()">初步评审</el-button>

                <el-button v-if="show=='EvalDetail'" type="primary" @click="showEvalDetail()">详细评审</el-button>
                <el-button v-else @click="showEvalDetail()">详细评审</el-button>

                <el-button v-if="show=='EvalPrice'" type="primary" @click="showEvalPrice()">价格评审</el-button>
                <el-button v-else @click="showEvalPrice()">价格评审</el-button>

                <el-button v-if="show=='EvalEnd'" type="primary" @click="showEvalEnd()">评标结束</el-button>
                <el-button v-else @click="showEvalEnd()" :disable="disable">评标结束</el-button>

                <el-button type="primary" @click="goEvalCenter2()">评标报告</el-button>

                </el-col>
                <el-col :span="4" style="line-height:40px; text-align:right">{{tip}}</el-col>
            </el-row>


            <div v-loading="loading">
                <!-- 评标准备 -->
                <EvalStart v-if="show=='EvalStart'" :tenderSection="tenderSection"></EvalStart>

                <!-- 推荐评标组长 -->
                <EvalExpert v-if="show=='EvalExpert'" :tenderSection="tenderSection"></EvalExpert>

                <!-- 初步评审 -->
                <EvalPrimary v-if="show=='EvalPrimary'" :tenderSection="tenderSection"></EvalPrimary>

                <!-- 详细评审 -->
                <EvalDetail v-if="show=='EvalDetail'" :tenderSection="tenderSection"></EvalDetail>

                <!-- 价格评审 -->
                <EvalPrice v-if="show=='EvalPrice'" :tenderSection="tenderSection"></EvalPrice>

                <!-- 评标结束 -->
                <EvalEnd v-if="show=='EvalEnd'" :tenderSection="tenderSection"></EvalEnd>
            </div>

        </div>

        <!-- 确定回避人员对话框 -->
        <el-dialog title="确定回避人员" width="30%" :visible.sync="excludeDialog">
            <div>
                <div style="margin-bottom:20px;">您确定要将评标专家<span style="font-size:18px; font-weight:bold"> 欧阳锋 </span>设置为回避吗？被回避后将不能参与评标。</div>
                <el-input type="textarea" rows="3" placeholder="请填写回避理由" v-model="excludedMemo"></el-input>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="excludeDialog=false">取 消</el-button>
                <el-button type="primary" @click="excludeMember()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

    import EvalStart from './components/EvalStart';
    import EvalExpert from './components/EvalExpert';
    import EvalPrimary from './components/EvalPrimary';
    import EvalDetail from './components/EvalDetail';
    import EvalPrice from './components/EvalPrice';
    import EvalEnd from './components/EvalEnd';

    export default {
        name: 'EvalCenter',
        components: { EvalStart, EvalExpert, EvalPrimary, EvalDetail, EvalPrice, EvalEnd },
        data() {
            return {
                clock: '2020年',
                timer: 0,
                currentUser: {},
                tenderSection: {},
                evalCriterias: [],
                tip: '',
                showTimer: false,
                show: 'EvalStart',
                disable: false,
                loading: false,
            }
        },
        created() {
            this.tenderSection = JSON.parse(sessionStorage.getItem("tenderSection"));
            this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            this.timer = Number(this.tenderSection.bidEvalPeriod) * 60;
            // this.startClock();

            this.fetchEvalCriterias();

            this.$message({ message: '09:25:00 欢迎评标专家 '+this.currentUser.realName+' 来到评标中心！', type: 'success' });
        },
        methods: {
            startClock() {
                setInterval(() => {
                    let now = new Date();
                    let week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                    this.clock = now.getFullYear() + '年' + (now.getMonth()+1) + '月' + now.getDate() + '日 '+now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + ' ' + week[now.getDay()];
                }, 1000);
            },
            startTimer() {
                setInterval(() => { if(this.timer >0) this.timer--; }, 1000);
                this.showTimer = true;
            },

            //获取评标项
            fetchEvalCriterias() {
                this.loading = true;
                this.$http.get('/tas/eval/criteria/list?sectionUid=' + this.tenderSection.sectionUid + '&evalMethod=' + this.tenderSection.evalMethod).then((data) => {
                    this.evalCriterias = data;
                    this.loading = false;
                }).catch(() => this.loading = false);
            },

            showEvalStart() {
                this.show = 'EvalStart';
            },
            showEvalExpert() {
                this.show = 'EvalExpert';
            },
            showEvalPrimary() {
                this.show = 'EvalPrimary';
            },
            showEvalDetail() {
                this.show = 'EvalDetail';
            },
            showEvalPrice() {
                this.show = 'EvalPrice';
            },
            showEvalEnd() {
                this.show = 'EvalEnd';
            },
            goEvalCenter2() {
                this.$router.push('EvalCenter2');
            },
        },
        beforeDestroy () {
            clearInterval(timer);
        }
    }
</script>


<style scoped>
/* 定义keyframe动画，命名为blink */
@keyframes blink{
  0%{opacity: 1;}
  100%{opacity: 0;} 
}
/* 添加兼容性前缀 */
@-webkit-keyframes blink {
    0% { opacity: 1; }
    100% { opacity: 0; }
}
@-moz-keyframes blink {
    0% { opacity: 1; }
    100% { opacity: 0; }
}
@-ms-keyframes blink {
    0% {opacity: 1; } 
    100% { opacity: 0;}
}
@-o-keyframes blink {
    0% { opacity: 1; }
    100% { opacity: 0; }
}
/* 定义blink类*/
.blink{
    color: #dd4814;
    animation: blink 1s linear infinite;  
    /* 其它浏览器兼容性前缀 */
    -webkit-animation: blink 1s linear infinite;
    -moz-animation: blink 1s linear infinite;
    -ms-animation: blink 1s linear infinite;
    -o-animation: blink 1s linear infinite;
}
</style>
