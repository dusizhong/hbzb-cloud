<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h2 style="margin-bottom:10px; padding:10px; text-align:center; color:#303133; background: #F2F6FC">评标中心</h2>

            <!-- 标段信息 -->
            <el-collapse v-model="collapse">
                <el-collapse-item name="1" >
                    <template slot="title"><span style="font-size:16px"><b>评标标段：</b>{{tenderSection.name}}</span></template>
                    <div>标段编号：<b>{{tenderSection.serialNo}}</b>（{{tenderSection.tradeType}}）</div>
                        <div>标段名称：<b>{{tenderSection.name}}</b></div>
                        <div>招 标 人：<b>中国邮政储蓄银行股份有限公司石家庄市分行</b></div>
                        <div>招标代理：<b>{{tenderSection.agencyName}}</b></div>
                        <div>评标时间：<b>{{tenderSection.bidEvalTime}}</b>（时长：{{tenderSection.bidEvalPeriod}}分钟）<span v-if="showTimer" style="float:right; color:#F56C6C">剩余时间：{{timer}}秒</span></div>
                        <div style="text-align:right">
                            <el-button type="primary" plain size="mini" icon="el-icon-document" round>招标文件</el-button>
                            <el-button type="primary" plain size="mini" icon="el-icon-message" round>答疑澄清</el-button>
                            <el-button type="primary" plain size="mini" icon="el-icon-circle-close-outline" round>否决项</el-button>
                        </div>
                </el-collapse-item>
            </el-collapse>
            
            <!-- 操作流程按钮 -->
            <el-row style="margin:30px auto">
                <el-col :span="20">

                <el-button v-if="show=='EvalStart'" type="primary" @click="showEvalStart()" class="blink">评标准备</el-button>
                <el-button v-else @click="showEvalStart()" :disable="disable">评标准备</el-button>

                <el-button v-if="show=='EvalExpert'" type="primary" @click="showEvalExpert()" class="blink">推荐评标组长</el-button>
                <el-button v-else @click="showEvalExpert()" :disable="disable">推荐评标组长</el-button>

                <el-button @click="showStep('showPrepare')">资格审查</el-button>

                <el-button v-if="show=='EvalPrimary'" type="primary" @click="showEvalPrimary()" class="blink">初步评审</el-button>
                <el-button v-else @click="showEvalPrimary()">初步评审</el-button>

                <el-button v-if="show=='EvalDetail'" type="primary" @click="showEvalDetail()" class="blink">详细评审</el-button>
                <el-button v-else @click="showEvalDetail()">详细评审</el-button>

                <el-button v-if="show=='EvalPrice'" type="primary" @click="showEvalPrice()" class="blink">价格评审</el-button>
                <el-button v-else @click="showEvalPrice()">价格评审</el-button>

                <el-button v-if="show=='EvalEnd'" type="primary" @click="showEvalEnd()">评标结束</el-button>
                <el-button v-else @click="showEvalEnd()" :disable="disable">评标结束</el-button>

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
        name: 'EvalCenter2',
        components: { EvalStart, EvalExpert, EvalPrimary, EvalDetail, EvalPrice, EvalEnd },
        data() {
            return {
                clock: '2020年',
                timer: 0,
                collapse: 1,
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
            }
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
    animation: blink 1s linear infinite;  
    /* 其它浏览器兼容性前缀 */
    -webkit-animation: blink 1s linear infinite;
    -moz-animation: blink 1s linear infinite;
    -ms-animation: blink 1s linear infinite;
    -o-animation: blink 1s linear infinite;
}
</style>
