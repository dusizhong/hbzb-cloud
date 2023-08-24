<template>
    <div>

        <div class="container">
            <el-card v-if="end" shadow="hover" style="text-align:center;">
                <div style="font-size:24px; font-weight:bold; color:#E6A23C">开标已结束</div>
                <div style="margin-top:20px; font-size:16px"><b>结束时间：</b>{{openRecord.endTime}}</div>
                <div style="margin-top:10px; font-size:16px"><b>操作人：</b>{{openRecord.creatorName}}</div>
            </el-card>
            <el-card v-else shadow="hover" style="text-align:center;">
                <div style="font-size:24px; font-weight:bold; color:#E6A23C">开标尚未结束</div>
                <div style="margin-top:20px; font-size:16px">请耐心等待主持人结束开标...</div>
            </el-card>
        </div>

    </div>
</template>


<script>
    export default {
        name: 'BidEnd',
        props:{
            section: {},
            openRecord: {}
        },
        data() {
            return {
                end: false
            }
        },
        watch: {
            openRecord(newValue, oldValue) {
                console.log('BidEnd watch openRecord: updated')
                if(this.openRecord.endTime) {
                    this.end = true;
                }
            }
        },
        created() {
            if(this.openRecord.endTime) {
                this.end = true;
            }
        },
        methods: {
            
        }
    }
</script>
