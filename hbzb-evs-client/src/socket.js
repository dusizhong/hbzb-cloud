
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Message } from 'element-ui';

let baseURL = 'http://192.168.33.111:9003/v1/evs';
let stompClient = null;


export default {

	connect() {
		let token = sessionStorage.getItem('token').replace('Bearer ', '');
		let socket = new SockJS(baseURL + '/websocket?access_token=' + token);
		return new Promise((resolve, reject) => {
			stompClient = Stomp.over(socket);
			stompClient.debug = false; // 禁用debug信息
			stompClient.connect({}, function(frame) {
				console.log('websocket连接成功！');
				stompClient.subscribe('/message', function(msg) {
					resolve(JSON.parse(msg.body));
				})
			}, function(error) {
				Message.error('websocket连接失败，请重新登录！');
			})
		})
	},

    send(json) {
		if(stompClient != null) {
			stompClient.send('/message', {}, JSON.stringify(json));
		} else console.log('websocket已断开，发送失败！');
	},

	close() {
		if(stompClient !== null) {
			stompClient.disconnect();
			console.log('websocket关闭成功！');
		}
	}
}
