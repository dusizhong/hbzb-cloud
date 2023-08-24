
import axios from 'axios';
import axios2 from 'axios'; // for local data
import QS from 'qs';
import { Message } from 'element-ui';

let baseURL = 'http://192.168.33.112:9000/v1';
let uploadURL = 'http://192.168.33.111:9000/hbzb-gw-server/zuul/v1';
// let baseURL = 'http://219.148.37.62:8080/hbzb-gw-server/v1';
let clientSecret = { username: 'tas', password: '0c89b4f7' };

axios.defaults.timeout = 5000;
axios.defaults.baseURL = baseURL;
axios.defaults.uploadURL = uploadURL;
axios.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' }


export default {

	baseURL: baseURL,
	uploadURL: uploadURL,

	login(url, params) {
		let config = { auth: clientSecret };
		return new Promise((resolve, reject) => {
			axios.post(url, QS.stringify(params), config).then(response => {
				if(response.data.code == 200) {
					sessionStorage.setItem('token', 'Bearer ' + response.data.access_token);
					resolve(response.data.data);
				} else {
					Message.error(response.data.message);
					reject();
				}
	        }).catch((error) => {
	        	if(error.response.status == 401) {
	        		Message.error('客户端密钥错误');
	        	} else {
	        		Message.error('未知错误，请联系管理员');
	        	}
	        	reject(error)
	        })
		})
	},

	getVcode(url) {
		let config = { auth: clientSecret };
		return new Promise((resolve, reject) => {
			axios.get(url, config).then(response => {
				if(response.data.code == 200) {
					resolve(response.data.data);
				} else {
					Message.error(response.data.message);
					reject();
				}
	        }).catch((error) => { 
	        	if(error.response.status == 401) {
	        		Message.error('会话已过期，请退出重新登录');
	        	} else {
	        		Message.error('未知错误，请联系管理员');
	        	}
	        	reject(error);
	        })
		})
	},

	get(url) {
		let config = { headers: { 'Authorization': sessionStorage.getItem('token') } };
		return new Promise((resolve, reject) => {
			axios.get(url, config).then(response => {
				if(response.data.code == 200) {
					resolve(response.data.data);
				} else {
					Message.error(response.data.message);
					reject();
				}
	        }).catch((error) => { 
	        	if(error.response.status == 401) {
	        		Message.error('会话已过期，请退出重新登录');
	        	} else {
	        		Message.error('未知错误，请联系管理员');
	        	}
	        	reject(error);
	        })
		})
	},

	post(url, params) {
		let config = { headers: { 'Authorization': sessionStorage.getItem('token') } };
		return new Promise((resolve, reject) => {
			axios.post(url, QS.stringify(params), config).then(response => {
				if(response.data.code == 200) {
					resolve(response.data.data);
				} else {
					Message.error(response.data.message);
					reject();
				}
	        }).catch((error) => {
	        	if(error.response.status == 401) {
	        		Message.error('会话已过期，请退出重新登录');
	        	} else {
	        		Message.error('未知错误，请联系管理员');
	        	}
	        	reject(error);
	        })
		})
	},

	logout(url) {
		let config = { headers: { 'Authorization': sessionStorage.getItem('token') } };
		let params = {}; //axios使用post方法，参数必须
		return new Promise((resolve, reject) => {
			axios.post(url, params, config).then(response => {
				resolve(response.data);
	        }).catch((error) => {
	        	if(error.response.status == 401) {
	        		resolve(); //token失效时正常返回退出
	        	} else {
	        		Message.error('未知错误，请联系管理员');
	        		reject(error);
	        	}
	        })
		})
	},

	// sepcial for load local json data
	getJson(url) {
		return new Promise((resolve, reject) => {
			let ip = window.location.href;
            ip = ip.split('#')[0];
            ip = ip.slice(0, ip.length-1);
			axios2.get(ip + url).then(response => {
				resolve(response.data);
			}).catch((error) => {
				Message.error('加载本地json文件失败');
				reject(error);
			});
		})
	}
}
