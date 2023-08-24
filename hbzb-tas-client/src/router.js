import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/dashboard'
    },
    {
      path: '/',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('./components/Home.vue'),
      meta: { title: 'Home' },
      children: [
        {
          path: '/dashboard',
          component: () => import('./views/Dashboard.vue'),
          meta: { title: '工作台' }
        },

        {
          path: '/ProjectList',
          component: () => import('./views/project/ProjectList.vue'),
          meta: { title: '项目管理' }
        },
        {
          meta: { title: '项目编辑器' },
          path: '/ProjectEditor',
          component: () => import('./views/project/ProjectEditor.vue'),
        },
        {
          meta: { title: '项目详情' },
          path: '/ProjectDetail',
          component: () => import('./views/project/ProjectDetail.vue'),
        },

        {
          meta: { title: '招标管理' },
          path: '/SectionList',
          component: () => import('./views/section/SectionList.vue'),
        },
        {
          meta: { title: '招标详情' },
          path: '/TenderCenter',
          component: () => import('./views/tender/TenderCenter.vue'),
        },
        
        {
          meta: { title: '投标信息' },
          path: '/BidRecord',
          component: () => import('./views/bid/BidRecord.vue'),
        },

        {
          path: '/AnnounceList',
          component: () => import('./views/announce/AnnounceList.vue'),
          meta: { title: '公告管理' }
        },
        {
          path: '/AnnounceEditor',
          component: () => import('./views/announce/AnnounceEditor.vue'),
          meta: { title: '编辑公告' }
        },
        {
          path: '/AnnounceModify',
          component: () => import('./views/announce/AnnounceModify.vue'),
          meta: { title: '变更公告' }
        },

        {
          path: '/InvitationList',
          component: () => import('./views/invitation/InvitationList.vue'),
          meta: { title: '投标邀请' }
        },
        {
          path: '/InvitationEditor',
          component: () => import('./views/invitation/InvitationEditor.vue'),
          meta: { title: '投标邀请编辑器' }
        },
        {
          path: '/InvitationEditor2',
          component: () => import('./views/invitation/InvitationEditor2.vue'),
          meta: { title: '投标邀请编辑器2' }
        },

        {
          path: '/Tenderee',
          component: () => import('./views/invitation/Tenderee.vue'),
          meta: { title: '招标人管理' }
        },
        
        // 管理员路由
        {
          path: '/UserList',
          component: () => import('./views/user/UserList.vue'),
          meta: { title: '用户管理' }
        },
        {
          path: '/ExpertList',
          component: () => import('./views/expert/ExpertList.vue'),
          meta: { title: '专家管理' }
        },
        {
          path: '/ExpertDetail',
          component: () => import('./views/expert/ExpertDetail.vue'),
          meta: { title: '专家详情' }
        },
        {
          path: '/AgencyList',
          name: 'AgencyList',
          component: () => import('./views/agency/AgencyList.vue'),
          meta: { title: '招标代理管理' }
        },
        {
          path: '/AgencyDetail',
          name: 'AgencyDetail',
          component: () => import('./views/agency/AgencyDetail.vue'),
          meta: { title: '招标代理详情' }
        },

        {
          path: '/UserProfile',
          component: () => import('./views/my/UserProfile.vue'),
          meta: { title: '个人信息' }
        },
        {
          path: '/UserCorp',
          component: () => import('./views/my/UserCorp.vue'),
          meta: { title: '单位信息' }
        },
        {
          path: '/UserCorpDetail',
          component: () => import('./views/my/UserCorpDetail.vue'),
          meta: { title: '单位信息' }
        },

        {
          path: '/403',
          component: () => import(/* webpackChunkName: "about" */ './views/403.vue'),
          meta: { title: '403' }
        },
        {
          path: '/404',
          component: () => import(/* webpackChunkName: "about" */ './views/404.vue'),
          meta: { title: '404' }
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '*',
      redirect: '/404'
    }
  ]
})
