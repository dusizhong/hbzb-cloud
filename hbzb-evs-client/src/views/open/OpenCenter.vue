<template>
    <div>

        <div class="container">
            <h2 style="margin-bottom:10px; padding:15px; text-align:center; color:#303133; background: #F2F6FC">开标大厅</h2>
            <el-row :gutter="20" style="margin-top:20px">
                <el-col :span="18">
                    <el-card shadow="hover" style="font-size:16px; line-height:30px; minHeight:210px">
                        <div><b>标段编号：</b>{{section.serialNo}}（{{section.tradeType}}）</div>
                        <div><b>标段名称：</b>{{section.name}}</div>
                        <div><b>招 标 人：</b>沧州市建设开发有限公司</div>
                        <div><b>招标代理：</b>{{section.agencyName}}</div>
                        <div><b>开标时间：</b>{{section.bidOpenTime}}</div>
                    </el-card>
                </el-col>
                <el-col :span="6">
                    <el-card shadow="hover" style="text-align:center">
                        <div style="font-size:17px; font-weight:bold; margin-bottom:10px;">{{clock}}</div>
                        <div style="font-size:12px">国家授时中心</div>
                    </el-card>
                    <el-card shadow="hover" style="minHeight:100px; margin-top:20px; padding-left:10px">
                        <div style="font-size:14px"><b>开标人：</b>{{openRecord.creatorName}}</div>
                        <div style="font-size:14px"><b>监督人：</b>{{openRecord.supervisor}}</div>
                        <div style="font-size:14px"><b>公证人：</b>{{openRecord.notary}}</div>
                    </el-card>
                </el-col>
            </el-row>
            <!-- 操作栏 -->
            <!-- <el-row style="margin-top:20px">
                <el-col :span="12">
                    <el-button-group>
                        <el-button type="primary" size="mini" icon="el-icon-arrow-left" @click="handleStep(-1)" :disabled="step==0">上一步</el-button>
                        <el-button type="primary" size="mini" @click="handleStep(1)" :disabled="step==4">下一步<i class="el-icon-arrow-right el-icon--right"></i></el-button>
                    </el-button-group>
                </el-col>
                <el-col :span="12" style="padding-right:5px; line-height:30px; text-align:right">
                    <div class="el-icon-info blink"> {{tip}}</div>
                </el-col>
            </el-row> -->
            <!-- 步骤条 -->
            <el-steps :active="step" process-status="finish" finish-status="success" style="margin:20px auto" simple>
                <!-- <el-step class="step" title="1.投标人签到"></el-step> -->
                <el-step class="step" title="1.公布投标人" @click.native="handleStep(0)"></el-step>
                <el-step class="step" title="2.文件解密" @click.native="handleStep(1)"></el-step>
                <el-step class="step" title="3.唱标" @click.native="handleStep(2)"></el-step>
                <el-step class="step" title="4.开标结束" @click.native="handleStep(3)"></el-step>
            </el-steps>
            <div v-loading="loading">
                <transition name="el-zoom-in-center">
                    <component :is="currentComponent" :section="section" :openRecord="openRecord"></component>
                </transition>
            </div>
        </div>

    </div>
</template>


<script>
    import SignIn from './components/SignIn';
    import BidOpen from './components/BidOpen';
    import BidDecrypt from './components/BidDecrypt';
    import BidAnnounce from './components/BidAnnounce';
    import BidEnd from './components/BidEnd';

    export default {
        name: 'OpenCenter',
        components: { SignIn, BidOpen, BidDecrypt, BidAnnounce, BidEnd },
        data() {
            return {
                section: {},
                openRecord: {},
                clock: '2020年',
                myClock: null,
                step: 0,
                tip: '',
                currentComponent: '',
                loading: false
            }
        },
        watch: {
            $route(to, from) {
                console.log('OpenCenter watch: section updated');
                this.section = JSON.parse(sessionStorage.getItem("section"));
            }
        },
        created() {
            this.setClock(true);
            this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
            this.section = JSON.parse(sessionStorage.getItem("section"));
            this.$socket.connect().then((msg) => {
                console.log('websocket收到消息: ', msg);
            });
            this.handleStep();
        },
        beforeDestroy () {
            this.setClock(false);
            this.$socket.close();
        },
        methods: {
            setClock(enabled) {
                if(enabled) {
                    this.myClock = setInterval(() => {
                        let now = new Date();
                        let seconds = now.getSeconds().toString().length>1? now.getSeconds() : '0'+now.getSeconds();
                        let week = [ '星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ];
                        this.clock = now.getFullYear() + '年' + (now.getMonth()+1) + '月' + now.getDate() + '日 '+now.getHours() + ":" + now.getMinutes() + ":" + seconds + ' ' + week[now.getDay()];
                    }, 1000);
                } else clearInterval(this.myClock);
            },
            handleStep(step) {
                this.loading = true;
                this.openRecord = {};
                this.$http.get('/evs/open/record/detail?sectionUid=' + this.section.uid).then((data) => {
                    if(data) this.openRecord = data;
                    this.loading = false;
                    if(!step) {
                        if(data) step = data.step;
                        else step = 0;
                    }
                    this.step = step;
                    if(step == 0) this.currentComponent = 'BidOpen';
                    else if(step == 1) this.currentComponent = 'BidDecrypt';
                    else if(step == 2) this.currentComponent = 'BidAnnounce';
                    else if(step == 3) this.currentComponent = 'BidEnd';
                    
                }).catch(() => this.loading = false);
            }
        }
    }
</script>


<style scoped>
.step:hover {
    cursor: pointer;
}
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
