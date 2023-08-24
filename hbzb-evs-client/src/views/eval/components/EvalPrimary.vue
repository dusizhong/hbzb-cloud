<template>
    <div>

        <div class="container">
            <el-tabs v-model="tab" type="card" @tab-click="tabClick">
                <el-tab-pane v-for="tab in tabs" :label="tab.name" :name="tab.code">
                    <el-row :gutter="20">
                        <el-col :span="12" style="margin-bottom:10px">
                            <el-button type="primary" size="mini" v-on:click="showAllScore">完整打分</el-button>
                            <el-button type="primary" size="mini" v-on:click="showSingalScore">按单位打分</el-button>
                        </el-col>
                        <el-col :span="12" align="right" style="font-size:14px">
                            <el-button type="primary" plain size="mini" v-on:click="showFile" style="float:right">标书对比</el-button>
                        </el-col>
                    </el-row>

                    <!-- 完整打分 -->
                    <transition name="el-zoom-in-center">
                    <el-table v-if="allScore" :header-cell-style="{background:'#eef1f6', color:'#606266'}" border stripe :data="tableData" v-loading="loading">
                        <el-table-column type="index" label="序号" align="center" width="70" fixed></el-table-column>
                        <el-table-column prop="bidderName" label="投标人" align="left" minWidth="400" fixed>
                            <template slot-scope="scope">
                                <div>{{scope.row.bidderName}} <a href="./mock/bidFile.pdf" target="_blank"><span style="color:#409EFF">（投标文件）</span></a></div>
                            </template>
                        </el-table-column>
                        <el-table-column v-for="(col,i) in tableCols" :key="col.id" :label="col.name" align="center" width="200">
                            <template slot="header" slot-scope="scope">
                                <span style="margin-right:10px; color:#606266">{{col.name}}</span>
                                <i class="el-icon-circle-check-outline"></i>
                                <i class="el-icon-circle-close-outline"></i>
                            </template>
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row[col.code]">
                                    <el-radio label="通过">通过</el-radio>
                                    <el-radio label="不通过">不通过</el-radio>
                                </el-radio-group>        
                            </template>
                        </el-table-column>
                        <el-table-column prop="result" label="汇总" align="center" width="70"></el-table-column>
                    </el-table>
                    </transition>

                    <!-- 按单位打分 -->
                    <el-card v-if="singalScore" shadow="hover" style="font-size:18px; line-height:36px">
                    <el-row :gutter="20" style="border:1px dash #000">
                        <el-col :span="8" style="margin-bottom:10px">
                            <span style="font-size:15px; font-weight:bold">投标人：</span>
                            <el-select v-model="currentBidder" placeholder="请选择" @change="bidderChange">
                                <el-option v-for="item in tableData" :key="item.bidderUid" :label="item.bidderName" :value="item.bidderUid">
                                    <span style="float:left; font-weight:bold">{{ item.bidderName }}</span>
                                    <span style="float:right; margin-left:60px; color:#8492a6">{{ item.result }}</span>
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="16" align="right" style="font-size:14px">
                            <div>共 <span style="font-size:22px">{{tableData.length}}</span>  家投标人，已评审 <span style="font-size:22px">{{tableData.length-2}}</span>  家</div>
                        </el-col>
                    </el-row>
                    <transition name="el-zoom-in-center">
                    <el-row v-if="singalScoreContent" :gutter="20">
                        <el-col :span="8" style="margin-bottom:10px">
                            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}" border stripe :data="tableCols" v-loading="loading">
                                <el-table-column type="index" label="序号" align="center" width="55"></el-table-column>
                                <el-table-column prop="name" label="评分点" align="center" minWidth="200"></el-table-column>
                                <el-table-column prop="totalScore" label="评分" align="center" minWidth="200">
                                    <template slot-scope="scope">
                                        <el-radio-group v-model="scope.row.totalScore">
                                            <el-radio label="通过">通过</el-radio>
                                            <el-radio v-if="scope.row.totalScore=='不通过'" label="不通过"><span style="color:red">不通过</span></el-radio>
                                            <el-radio v-else label="不通过">不通过</el-radio>
                                        </el-radio-group> 
                                        <span v-if="scope.row.totalScore=='不通过'" style="margin-left:5px"><i class="el-icon-edit-outline"></i></span> 
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-card shadow="hover" style="margin-top:30px; height:300px;">
                                <div slot="header"><span style="font-weight:bold; font-size:15px">投标文件</span></div>
                                <el-tree :data="treeData" :props="defaultProps" node-key="id" :default-expanded-keys="[0, 1]"></el-tree>
                            </el-card>
                        </el-col>
                        <el-col :span="16" align="right" style="font-size:14px">
                            <iframe :src="pdfUrl" width="100%" height="1000" frameborder="0"></iframe>
                        </el-col>
                    </el-row>
                    </transition>
                </el-card>
                </el-tab-pane>
            </el-tabs>

            <div style="margin-top:30px" align="center">
                <el-button type="primary" v-on:click="onConfirm">提交</el-button>
                <el-button type="primary" v-on:click="showDialog">汇总提交</el-button>
                <el-button type="primary" @click="nextStep">下一项</el-button>
            </div>
        </div>

        <!-- 标书对比查看 -->
        <el-dialog title="标书对比查看" center width="90%" :visible.sync="fileVisiable">
            <el-row :gutter="20" style="border:1px dash #000">
                <el-col :span="12" style="margin-bottom:10px">
                            <span style="font-size:15px; font-weight:bold">投标人：</span>
                            <el-select v-model="currentBidder" placeholder="请选择" @change="bidderChange">
                                <el-option v-for="item in tableData" :key="item.bidderUid" :label="item.bidderName" :value="item.bidderUid">
                                    <span style="float:left; font-weight:bold">{{ item.bidderName }}</span>
                                    <span style="float:right; margin-left:60px; color:#8492a6">{{ item.result }}</span>
                                </el-option>
                            </el-select>
                </el-col>
                <el-col :span="12" style="font-size:14px">
                    <span style="font-size:15px; font-weight:bold">投标人：</span>
                            <el-select v-model="currentBidder" placeholder="请选择" @change="bidderChange">
                                <el-option v-for="item in tableData" :key="item.bidderUid" :label="item.bidderName" :value="item.bidderUid">
                                    <span style="float:left; font-weight:bold">{{ item.bidderName }}</span>
                                    <span style="float:right; margin-left:60px; color:#8492a6">{{ item.result }}</span>
                                </el-option>
                            </el-select>            
                </el-col>
            </el-row>
            
            <el-row :gutter="20">
                <el-col :span="12" style="margin-bottom:10px">
                    <transition name="el-zoom-in-center">
                        <iframe v-if="singalScoreContent" :src="pdfUrl" width="100%" height="800" frameborder="0"></iframe>
                    </transition>
                </el-col>
                <el-col :span="12" style="margin-bottom:10px">
                    <transition name="el-zoom-in-center">
                        <iframe v-if="singalScoreContent" :src="pdfUrl" width="100%" height="800" frameborder="0"></iframe>
                    </transition>
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button style="width:170px" @click="fileVisiable=false">关闭</el-button>
            </span>
        </el-dialog>

        <!-- 对话框 -->
        <el-dialog title="资格评审汇总表" center width="80%" :visible.sync="visible">
            <el-table :header-cell-style="{color:'#606266'}" border stripe :data="tableData" v-loading="loading">
                <el-table-column type="index" label="序号" align="center" width="70" fixed></el-table-column>
                <el-table-column prop="bidderName" label="投标人" align="left" minWidth="400"></el-table-column>
                <el-table-column v-for="(col,i) in tableCols" :key="col.id" :label="col.name" align="center" width="200">
                    <template slot="header" slot-scope="scope">
                        <span style="margin-right:10px; color:#606266">{{col.name}}</span>
                    </template>
                    <template slot-scope="scope">
                        <span>通过</span>      
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin-top:30px" align="center">
                <el-button type="primary" plain>手写签章</el-button>
                <el-button type="primary" plain>CA签章</el-button>
                <el-button type="primary" plain>远程签章</el-button>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="visible=false">取消</el-button>
                <el-button type="primary" @click="onConfirm">确定提交</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'EvalPrimary',
        props:{
            tenderSection: {}
        },
        data() {
            return {
                tab: '',
                tabs: [],
                tableCols: [],
                tableData: [],
                showTable: false,
                visible: false,
                loading: false,
                fileVisiable: false,
                currentBidder: {},
                allScore: true,
                singalScore: false,
                singalScoreContent: false,
                treeData: [{id: 1, label: '投标文件组成', children: [{id: 2, label: '封面.pdf'}, {id: 3, label: '正文.pdf'}]}],
                defaultProps: { children: 'children', label: 'label' },
                pdfUrl: './pdfjs/web/viewer.html?file=' + encodeURIComponent('http://127.0.0.1:8081/mock/bidFile.pdf')
            }
        },
        computed: {

        },
        created() {
            this.fetchTabs();
        },
        methods: {
            fetchTabs() {
                this.loading = true;
                this.$http.get('/tas/eval/criteria/list?sectionUid=' + this.tenderSection.sectionUid + '&evalMethod=' + this.tenderSection.evalMethod).then((data) => {
                    console.log(data)
                    for(let d of data) {
                        if(d.name == '初步评审') {
                            this.tabs = d.children;
                            break;
                        }
                    }
                    this.tab = this.tabs[0].code;
                    this.loading = false;
                    this.fetchData(this.tab);
                }).catch(() => this.loading = false);
            },
            fetchData(parentCode) {
                this.loading = true;
                this.showTable = false;
                this.$http.get('/tas/eval/record/list?sectionUid=' + this.tenderSection.sectionUid + '&parentCode=' + parentCode).then((data) => {
                        this.tableCols = data.tableCols;
                        this.tableData = data.tableData;
                        this.tableData[0].result = '通过'
                        this.tableData[1].result = '通过'
                        console.log(this.tableData)
                        this.showTable = true;
                        this.loading = false;
                }).catch(() => this.loading = false);
            },
            tabClick(tab, event) {
                let parentCode = tab.name;
                this.tab = parentCode;
                this.fetchData(parentCode)
            },
            showSingalScore() {
                this.allScore = false;
                this.singalScore = true;
            },
            showAllScore() {
                this.singalScore = false;
                this.allScore = true;
            },
            bidderChange(val) {
                this.singalScoreContent = false;
                setTimeout(() => {this.singalScoreContent = true}, 500);
                console.log(val)
            },
            showFile() {
                this.fileVisiable = true;
            },
            showDialog() {
                this.visible = true;
            },
            onConfirm() {
                this.$message.success('提交成功！');
            },
            onSubmit() {
                this.$message.success('汇总提交成功！');
            },
            nextStep() {
                let tab = Number(this.tab);
                tab = tab + 1;
                this.tab = tab.toString();
            }
        }
    }
</script>