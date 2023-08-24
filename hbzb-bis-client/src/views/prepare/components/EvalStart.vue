<template>
    <div>

        <div class="container">
            <el-card shadow="hover" style="text-align:center;">
                <el-button v-show="showEndBtn" type="primary" @click="endBid()">开启评标</el-button>
                <div v-show="showEndInfo" style="margin-top:20px; font-size:18px">开启时间：<b>2020-05-31 11:30:00</b></div>
                <div v-show="showEndInfo" style="margin-top:20px; font-size:18px">操作人：<b>张三丰</b></div>
            </el-card>
        </div>

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
                showEndBtn: true,
                showEndInfo: false,
                visible: false,
                loading: false,
            }
        },
        created() {
            
        },
        methods: {
            endBid() {
                this.$confirm('您确定要开启评标吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.showEndBtn = false;
                    this.showEndInfo = true;
                    this.$emit('handleEnd', 'end');
                    this.$message({ type: 'success', message: '操作成功!' });
                }).catch(() => { 
                    this.$message({ type: 'info', message: '取消操作' });
                });
            }
        }
    }
</script>
