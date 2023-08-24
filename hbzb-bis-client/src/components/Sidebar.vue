<template>
    <div class="sidebar" v-loading="loading">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from './bus';
    export default {
        data() {
            return {
                collapse: true, //默认隐藏菜单栏(Home & Sidebar)
                loading: false,
                items: [],
                userMenu: [
                    { icon: 'el-icon-menu', index: 'SectionCenter', title: '项目大厅' },
                    { icon: 'el-icon-tickets', index: 'MySectionList', title: '我的项目' }
                ],
                agencyMenu: [
                ],
                expertMenu: [
                    { icon: 'el-icon-view', index: 'bidOpen', title: '开评标',
                        subs: [
                            { index: 'EvalPrepare', title: '评标准备' },
                        ]
                    }
                ],
                adminMenu: [
                    { icon: 'el-icon-setting', index: '9', title: '系统管理',
                        subs: [
                            // { index: 'UserList', title: '用户管理' },
                            // { index: 'AgencyList', title: '招标代理管理' },
                            // { index: 'ExpertList', title: '专家管理' }
                        ]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        },
        created() {
            // 获取用户信息分配菜单项
            let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            if(currentUser.corpUid) {
                this.loading = true;
                this.$http.get('/uaa/corp/detail').then((data) => {
                    this.loading = false;
                    this.corp = data;
                    if(this.corp.status == 'CERTIFIED') {
                        if(currentUser.authorities.indexOf('ROLE_AGENCY')!= -1) {
                            this.items = this.items.concat(this.userMenu);
                            this.items = this.items.concat(this.agencyMenu);
                        } else if(currentUser.authorities.indexOf('ROLE_SYS_ADMIN') != -1) {
                            this.items = this.items.concat(this.userMenu);
                            this.items = this.items.concat(this.agencyMenu);
                            this.items = this.items.concat(this.expertMenu);
                            this.items = this.items.concat(this.adminMenu);
                        }
                    } else {
                        this.items = this.userMenu;
                    }
                }).catch(() => this.loading = false);
            } else {
                this.items = this.userMenu;
            }
            // collapse sidebar
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 250px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
