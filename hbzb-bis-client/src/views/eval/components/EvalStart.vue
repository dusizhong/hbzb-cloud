<template>
    <div>

        <div class="container">

            <el-carousel height="300px" style="margin:30px auto;">
                <el-carousel-item v-for="item in 4" :key="item">
                    <h1 style="line-height:300px; text-align:center; background-color:#d3dce6">评标纪律  公开 公平 公正{{ item }}</h1>
                </el-carousel-item>
            </el-carousel>
            <div>09:25:00 欢迎专家 <b>{{currentUser.realName}}</b> 进入评标大厅！</div></el-col>
            <div align="center">
                <el-button v-if="signed" type="primary" @click="onSignIn" disabled>已签到，请推荐评标组长</el-button>
                <el-button v-else type="primary" @click="onSignIn">立即签到</el-button>
            </div>

        </div>

        <el-dialog title="对话框" :visible.sync="visible">
            
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'EvalStart',
        props:{
            tenderSection: {}
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
            tenderSection(oldValue, newValue) {
                    
            }
        },
        methods: {
            onSignIn() {
                this.signed = true;
                this.$message.success('签到成功');
            }
        }
    }
</script>
