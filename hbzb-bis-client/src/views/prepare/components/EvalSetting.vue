<template>
    <div>

        <div class="container">
            <el-card shadow="hover" style="text-align:left">
                <div style="margin-bottom:20px">
                <span>评标组长选举方式：</span>
                <el-radio-group v-model="form.recommendType" :disabled="disable">
                            <el-radio label="直接指定"></el-radio >
                            <el-radio label="组内投票"></el-radio >
                </el-radio-group>
                </div>

                <div style="margin-bottom:20px">
                <span>签章方式：</span>
                <el-radio-group v-model="form.type" :disabled="disable">
                            <el-radio label="手写签章/CA签章"></el-radio >
                            <el-radio label="云签章"></el-radio >
                            <el-radio label="省平台云签章"></el-radio >
                </el-radio-group>
            </div>
                <el-button type="primary" @click="onSave()">保存</el-button>
            </el-card>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'EvalSetting',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                form: {},
                visible: false,
                disable: false,
                loading: false,
            }
        },
        created() {
            
        },
        methods: {
            onSave() {
                this.$message({ type: 'success', message: '保存成功!' });
            }
        }
    }
</script>
