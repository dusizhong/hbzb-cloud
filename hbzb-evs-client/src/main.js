import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import http from './http'
import socket from './socket'
import ElementUI from 'element-ui'
import './components/directives';
import 'element-ui/lib/theme-chalk/index.css'
// import './assets/theme-green/index.css';

//close tip in production
Vue.config.productionTip = false
Vue.prototype.$http = http;
Vue.prototype.$socket = socket;
Vue.use(ElementUI);

//router hook
router.beforeEach((to, from, next) => {
	const token = sessionStorage.getItem('token');
	if(!token && to.path !== '/login') {
		next('/login');
	} else {
		// lt ie10 tips
        if (navigator.userAgent.indexOf('MSIE') > -1) {
            Vue.prototype.$alert('为了保证最佳用户体验，请使用ie11更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        } else {
            next();
        }
	}
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
