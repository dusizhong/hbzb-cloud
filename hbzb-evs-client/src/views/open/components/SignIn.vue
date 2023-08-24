<template>
    <div>

        <div class="container">

            <el-carousel height="300px" style="margin:30px auto;">
                <el-carousel-item v-for="item in 4" :key="item">
                    <h1 style="line-height:300px; text-align:center; background-color:#d3dce6">公开 公平 公正{{ item }}</h1>
                </el-carousel-item>
            </el-carousel>
            <div>09:25:00 欢迎投标人 <b>{{currentUser.realName}}</b> 进入开标大厅！<span style="float:right"></span></div>
            <!-- <div align="center">
                <el-button type="primary" style="width:170px" @click="nextStep">公布投标人</el-button>
            </div> -->

        </div>

        <!-- <el-dialog title="对话框" :visible.sync="visible">
            
        </el-dialog> -->

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
                currentUser: {},
                signed: false,
                visible: false,
                loading: false,
            }
        },
        created() {
            this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
        },
        watch: {
            section(oldValue, newValue) {
                console.log('SignIn watch')
            }
        },
        methods: {
            // nextStep() {
            //     this.$confirm('确定公布投标人吗？公布后不能撤回！', '提示', {
            //         confirmButtonText: '确定',
            //         cancelButtonText: '取消',
            //         type: 'warning'
            //     }).then(() => {
            //         this.$emit('nextStep', 'BidOpen');
            //     }).catch(() => console.log('cancel'));
            // }
        }
    }
</script>
