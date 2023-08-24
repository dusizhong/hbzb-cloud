import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/SectionCenter'
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
          path: '/SectionCenter',
          component: () => import('./views/SectionCenter.vue'),
          meta: { title: '项目大厅' }
        },
        {
          path: '/OpenCenter',
          component: () => import('./views/open/OpenCenter.vue'),
          meta: { title: '开标大厅' }
        },
        {
          path: '/EvalPrepare',
          component: () => import('./views/prepare/EvalPrepare.vue'),
          meta: { title: '评标准备' }
        },
        {
          path: '/EvalCenter',
          component: () => import('./views/eval/EvalCenter.vue'),
          meta: { title: '评标中心' }
        },
        {
          path: '/EvalCenter2',
          component: () => import('./views/eval/EvalCenter2.vue'),
          meta: { title: '评标中心2' }
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
