<template>
    <div>

        <div class="container">

            <!-- 标题 -->
            <h1 style="margin-bottom:10px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">评标准备</h1>

            <!-- 电子时钟 -->
            <el-card shadow="hover" style="text-align:center;">
                <div style="font-size:16px; font-weight:bold; margin-bottom:10px;">{{clock}}</div>
                <div style="font-size:12px">国家授时中心</div>
            </el-card>

            <!-- 标段信息 -->
            <el-card shadow="hover" style="margin-top:30px; font-size:18px; line-height:36px">
                <div>标段编号：<b>{{tenderSection.serialNo}}</b>（{{tenderSection.tradeType}}）</div>
                <div>标段名称：<b>{{tenderSection.name}}</b></div>
                <div>招 标 人：<b>沧州市建设开发有限公司</b></div>
                <div>招标代理：<b>{{tenderSection.agencyName}}</b></div>
                <div>评标时间：<b>{{tenderSection.bidEvalTime}}</b>（时长：{{tenderSection.bidEvalPeriod}}分钟）</div>
                <div>招标文件：<b><i class="el-icon-document"></i> <a :href="tenderSection.tenderFile" target="_blank">查看招标文件</a></b></div>
            </el-card>
            
            <!-- 步骤按钮 -->
            <el-row style="margin:30px auto">
                <el-col :span="20">
                    <el-button v-if="buttons[0]=='show'" type="primary">评标设置</el-button>
                    <el-button v-else-if="buttons[0]=='hiden'" @click="show(0)">评标设置</el-button>
                    <el-button v-else disabled>评标设置</el-button>

                    <el-button v-if="buttons[1]=='show'" type="primary">评标办法</el-button>
                    <el-button v-else-if="buttons[1]=='hiden'" @click="show(1)">评标办法</el-button>
                    <el-button v-else disabled>评标办法</el-button>

                    <el-button v-if="buttons[2]=='show'" type="primary">评标委员会</el-button>
                    <el-button v-else-if="buttons[2]=='hiden'" @click="show(2)">评标委员会</el-button>
                    <el-button v-else disabled>评标委员会</el-button>

                    <el-button v-if="buttons[3]=='show'" type="primary">设定监标人</el-button>
                    <el-button v-else-if="buttons[3]=='hiden'" @click="show(3)">设定监标人</el-button>
                    <el-button v-else disabled>设定监标人</el-button>

                    <el-button v-if="buttons[3]=='show'" type="primary">确定回避单位</el-button>
                    <el-button v-else-if="buttons[3]=='hiden'" @click="show(3)">确定回避单位</el-button>
                    <el-button v-else disabled>确定回避单位</el-button>

                    <el-button v-if="buttons[3]=='show'" type="primary">标书雷同性分析</el-button>
                    <el-button v-else-if="buttons[3]=='hiden'" @click="show(3)">标书雷同性分析</el-button>
                    <el-button v-else disabled>标书雷同性分析</el-button>

                    <el-button v-if="buttons[4]=='show'" type="primary">开启评标</el-button>
                    <el-button v-else-if="buttons[4]=='hiden'" @click="show(4)">开启评标</el-button>
                    <el-button v-else disabled>开启评标</el-button>
                </el-col>

                <el-col :span="4" style="padding-right:5px; line-height:40px; text-align:right">
                    <span>{{tip}}</span>
                </el-col>
            </el-row>

            <div v-loading="loading">
                <!-- 评标设置 -->
                <EvalSetting v-if="contents[0]=='show'" :tenderSection="tenderSection"></EvalSetting>

                <!-- 评标方法 -->
                <EvalMethod v-if="contents[1]=='show'" :tenderSection="tenderSection" @handleTimer="startTimer"></EvalMethod>

                <!-- 评标委员会 -->
                <EvalMember v-if="contents[2]=='show'" :tenderSection="tenderSection"></EvalMember>

                <!-- 开启评标 -->
                <EvalStart v-if="contents[4]=='show'" :tenderSection="tenderSection" @handleEnd="handleBidEnd"></EvalStart>
            </div>

        </div>

    </div>
</template>


<script>

    import EvalSetting from './components/EvalSetting';
    import EvalMember from './components/EvalMember';
    import EvalMethod from './components/EvalMethod';
    import EvalStart from './components/EvalStart';

    export default {

        name: 'EvalPrepare',
        components: { EvalSetting, EvalMember, EvalMethod, EvalStart },
        data() {
            return {
                clock: '2020年',
                timer: 0,
                tenderSection: {},
                tableData: [],
                buttons: ['show', 'hiden', 'hiden', 'hiden', 'hiden'],
                contents: ['show', 'hiden', 'hiden', 'hiden', 'hiden'],
                tip: '',
                showTimer: false,
                visible: false,
                loading: false,
            }
        },
        created() {
            this.tenderSection = JSON.parse(sessionStorage.getItem("tenderSection"));
            this.timer = Number(this.tenderSection.bidOpenPeriod) * 60;
            this.startClock();
        },
        watch: {
            $route(to, from) {
                this.tenderSection = JSON.parse(sessionStorage.getItem('tenderSection'));
            }
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
                this.buttons[0] = 'disabled';
                this.buttons[1] = 'disabled;'
                this.show(2);
                this.showTimer = true;
            }, 
            show(index) {
                this.loading = true;
                for(let i=0; i<this.buttons.length; i++) {
                    if(this.buttons[i] == 'show') this.buttons[i] = 'hiden';
                    if(this.contents[i] == 'show') this.contents[i] = 'hiden';
                };
                this.buttons[index] = 'show';
                this.contents[index] = 'show';
                this.loading = false;
                // show sign in
                if(index == 1) this.tip = '请查看';
            },
            handleBidEnd() {
                for(let i=0; i<this.buttons.length; i++) {
                    this.buttons[i] = 'disabled';
                };
            }
        },
        beforeDestroy () {
            clearInterval(this.timer);
            console.log('beforeDestroy')
        }
    }
</script>
