<template>
    <div>
        <div class="container">
            <h3 style="padding:15px; text-align:center; color:#303133; background: #F2F6FC">招标详情</h3>

            <!-- 标段（包）信息 -->
            <el-card shadow="hover" style="margin:20px 0; font-size:18px; line-height:36px">
                <div>标段编号：<b>{{section.serialNo}}</b>（{{section.tradeType}}）</div>
                <div>标段名称：<b>{{section.name}}</b></div>
            </el-card>

            <!-- 招标设置 -->
            <el-tabs v-model="tab" type="card">
                <el-tab-pane name="1">
                    <span slot="label"><i class="el-icon-document"></i> 招标文件</span>
                    <TenderFile :section="section"></TenderFile>
                </el-tab-pane>
                <el-tab-pane name="2" v-if="section.needGuarantee=='true'">
                    <span slot="label"><i class="el-icon-message"></i> 投标保证金</span>
                    <TenderGuarantee :section="section"></TenderGuarantee>
                </el-tab-pane>
                <el-tab-pane name="3" v-if="section.bidOnline=='true'">
                    <span slot="label"><i class="el-icon-tickets"></i> 开标一览表</span>
                    <TenderSheet :section="section"></TenderSheet>
                </el-tab-pane>
                <el-tab-pane name="4" v-if="section.bidOnline=='true'">
                    <span slot="label"><i class="el-icon-sort"></i> 投标文件组成设置</span>
                    <TenderBidFile :section="section"></TenderBidFile>
                </el-tab-pane>
                <el-tab-pane name="5" v-if="section.bidOnline=='true'">
                    <span slot="label"><i class="el-icon-edit-outline"></i> 评标办法设置</span>
                    <EvalMethod :section="section"></EvalMethod>
                </el-tab-pane>
                <el-tab-pane name="6" v-if="section.bidOnline=='true'">
                    <span slot="label"><i class="el-icon-view"></i> 开评标成员设置</span>
                    <EvalMember :section="section"></EvalMember>
                </el-tab-pane>
                <!-- <el-tab-pane name="7" v-if="section.bidOnline=='true'">
                    <span slot="label"><i class="el-icon-date"></i> 开评标设置</span>
                    <TenderBidOpen :tenderSection="section"></TenderBidOpen>
                </el-tab-pane> -->
            </el-tabs>
        </div>

        <el-card shadow="hover" style="margin:20px 0; text-align: center">
            <div style="line-height:56px; font-size:14px">请确保以上各项设置准确无误后提交审核</div>
            <el-button type="primary" plain @click="visible=true">生成招标文件</el-button>
            <el-button type="primary" plain style="width:170px">无须审核</el-button>
            <el-button type="primary" style="width:170px">提交审核</el-button>
        </el-card>

        <el-dialog title="生成招标文件" center :close-on-click-modal="false" :visible.sync="visible">
            <div style="margin-bottom:30px;">请确保以上各项设置准确无误后，点击生成招标文件按钮。</div>
            <el-progress :text-inside="true" :stroke-width="24" :percentage="pt" status="success"></el-progress>
            <div style="margin-top:30px; text-align:center"><el-button type="primary" @click="generate" :loading="btnLoading">生成招标文件</el-button></div>
        </el-dialog>

    </div>
</template>


<script>
    import TenderFile from './components/TenderFile';
    import TenderGuarantee from './components/TenderGuarantee';
    import TenderSheet from './components/TenderSheet';
    import TenderBidFile from './components/TenderBidFile';
    import EvalMethod from './components/EvalMethod';
    import EvalMember from './components/EvalMember';
    import TenderBidOpen from './components/TenderBidOpen';

    export default {
        name: 'TenderCenter',
        components: { TenderFile, TenderGuarantee, TenderSheet, TenderBidFile, EvalMethod, EvalMember, TenderBidOpen },
        data() {
            return {
                section: {},
                tab: '1',
                loading: false,
                pt: 0,
                btnLoading: false,
                visible: false
            }
        },
        created() {
            this.section = JSON.parse(sessionStorage.getItem('section'));
        },
        watch: {
            $route(to, from) {
                this.section = JSON.parse(sessionStorage.getItem('section'));
            }
        },
        methods: {
            generate() {
                this.btnLoading = true;
                let counter = setInterval(() => {
                    this.pt++;
                    if (this.pt > 99) {
                        clearInterval(counter);
                        this.btnLoading = false;
                        this.visible = false;
                        this.$message.success('生成成功！');
                    }
                }, 50); 
            },
            onSave() {
                alert('onSave')
            }
        }
    }
</script>