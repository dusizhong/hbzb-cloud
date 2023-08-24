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
            <el-steps style="margin:20px auto" :active="step" process-status="finish" finish-status="success" simple>
                <el-step class="step" title="1.公布投标人" @click.native="handleStep(0)"></el-step>
                <el-step class="step" title="2.文件解密" @click.native="handleStep(1)"></el-step>
                <el-step class="step" title="3.唱标" @click.native="handleStep(2)"></el-step>
                <el-step class="step" title="4.开标结束" @click.native="handleStep(3)"></el-step>
            </el-steps>
            <div v-loading="loading">
                <transition name="el-zoom-in-center">
                    <component :is="currentComponent" :section="section" :openRecord="openRecord" @handleStep="handleStep"></component>
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
                currentComponent: '',
                loading: false
            }
        },
        watch: {
            $route(to, from) {
                console.log('OpenCenter watch: section updated');
                this.handleStep();
            }
        },
        created() {
            this.startClock();
            this.handleStep();
            this.$socket.connect().then((stompClient) => {
                let that = this;
                stompClient.subscribe('/message', function(msg) {
                    console.log('websocket收到消息: ' + msg.body);
                    if(msg.body.op == 'REOPEN') {
                        this.$message.warning('开标已重置，请重新参与！');
                        setTimeout(() => { window.location.reload() }, 2000);
                    }
                    that.handleStep();
                })
            });
        },
        beforeDestroy () {
            clearInterval(this.myClock);
            this.$socket.close();
        },
        methods: {
            startClock() {
                this.myClock = setInterval(() => {
                    let now = new Date();
                    let seconds = now.getSeconds().toString().length>1? now.getSeconds() : '0'+now.getSeconds();
                    let week = [ '星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ];
                        this.clock = now.getFullYear() + '年' + (now.getMonth()+1) + '月' + now.getDate() + '日 '+now.getHours() + ":" + now.getMinutes() + ":" + seconds + ' ' + week[now.getDay()];
                }, 1000);
            },
            handleStep(step) {
                console.log('handleStep')
                this.loading = true;
                this.openRecord = {};
                this.section = JSON.parse(sessionStorage.getItem("section"));
                this.$http.get('/evs/open/record/detail?sectionUid=' + this.section.uid).then((data) => {
                    if(data) {
                        this.openRecord = data;
                        if(!step) step = data.step;
                    } else step = 0;
                    switch(step) {
                        case 0:
                            this.step = 0;
                            if(JSON.stringify(this.openRecord) != '{}') {
                                this.currentComponent = 'BidOpen';
                            } else this.currentComponent = 'SignIn';
                            break;
                        case 1:
                            if(this.openRecord.startDecryptTime) {
                                this.step = 1;
                                this.currentComponent = 'BidDecrypt';
                            } else this.$message.warning('解密未开启，请稍后再试！');
                            break;
                        case 2:
                            if(this.openRecord.startAnnounceTime) {
                                this.step = 2;
                                this.currentComponent = 'BidAnnounce';
                            } else this.$message.warning('唱标暂未开启，请稍后再试！')
                            break;
                        case 3:
                            this.step = 3;
                            this.currentComponent = 'BidEnd';
                            break;
                    }
                    this.loading = false;
                }).catch(() => this.loading = false);
            }
        }
    }
</script>


<style scoped>

/* 定义step hover */
.step:hover {
    cursor: pointer;
}
/* 定义keyframe动画，命名为blink */
@keyframes blink{
  0%{opacity: 1;}
  100%{opacity: 0.4;} 
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
    -webkit-animation: blink 2s linear infinite;
    -moz-animation: blink 1s linear infinite;
    -ms-animation: blink 1s linear infinite;
    -o-animation: blink 1s linear infinite;
}
</style>