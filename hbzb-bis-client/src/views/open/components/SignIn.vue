<template>
    <div>

        <div class="container" v-loading="loading">
            <el-row :gutter="20" style="margin-bottom:20px">
                <!-- <el-col :span="6">
                    <el-tooltip effect="dark" content="请点击这里刷新" placement="top" v-model="tooltip" :manual="true">
                        <el-button type="primary" plain icon="el-icon-refresh" @click="refresh">刷新</el-button>
                    </el-tooltip>
                </el-col> -->
                <el-col :span="24" style="line-height:36px; text-align:center">
                    <div class="el-icon-info" style="margin-right:10px; font-size:18px; color:#E6A23C"> 正在等待公布投标人，请稍后...</div>
                    <el-button type="text" @click="refresh">刷新</el-button>
                </el-col>
            </el-row>
            <el-carousel height="300px">
                <el-carousel-item v-for="item in 4" :key="item">
                    <h2 style="line-height:300px; text-align:center; background-color:#d3dce6">开标纪律：公开 公平 公正{{ item }}</h2>
                </el-carousel-item>
            </el-carousel>
            <div style="margin:10px">{{welcome}}</div>
            <!-- <div align="center">
                <el-button v-if="signined" type="primary" @click="onSignIn" disabled>已签到，请耐心等待主持人开标...</el-button>
                <el-button v-else type="primary" style="width:170px" @click="onSignIn" class="blink">立即签到</el-button>
            </div> -->
        </div>

    </div>
</template>


<script>
    export default {
        name: 'SignIn',
        props:{
            section: {}
        },
        data() {
            return {
                welcome: '',
                tooltip: false,
                myTooltip: null,
                signined: false,
                loading: false
            }
        },
        created() {
            let now = new Date();
            let seconds = now.getSeconds().toString().length>1? now.getSeconds() : '0'+now.getSeconds();
            let time = now.getHours() + ":" + now.getMinutes() + ":" + seconds;
            let currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
            setTimeout(()=>{this.welcome = time + ' 欢迎投标人 '+currentUser.realName+' 进入开标大厅！(自动签到成功)'}, 2000);
        },
        // mounted() {
        //     this.myTooltip = setInterval(() => {
        //         this.tooltip = true;
        //         setTimeout(() => { this.tooltip = false }, 2000);
        //     }, 3000);
        // },
        // beforeDestroy () {
        //     clearInterval(this.myTooltip);
        // },
        methods: {
            onSignIn() {
                this.signined = true;
                this.$message.success('签到成功');
            },
            refresh() {
                this.$emit('handleStep', 0);
            }
        }
    }
</script>