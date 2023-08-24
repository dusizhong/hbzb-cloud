<template>
	<div style="margin-top:60px; padding:30px; text-align:center" v-loading="loading">
		<el-row>
			<el-col :xs="24" :sm="{span:6, offset:9}">
				<img src="../assets/logo.png" style="width:170px; height:170px">
				<h3 style="margin-bottom:50px; font-size:20px">欢迎使用HBZB招标代理系统</h3>
				<el-form ref="loginForm" :model="loginForm" :rules="loginRules">
					<el-form-item prop="username">
						<el-input type="text" v-model="loginForm.username" placeholder="手机号或账号" autofocus></el-input>
					</el-form-item>
					<el-form-item prop="password">
						<el-input type="password" v-model="loginForm.password" placeholder="密码" @keyup.enter.native="login('loginForm')"></el-input>
					</el-form-item>
					<el-button style="width:100%; margin-top:16px" type="primary" v-on:click="login('loginForm')">登录</el-button>
				</el-form>
				<div style="font-size:14px; line-height:60px">
					<a style="float:left; color:#909399" href="#" @click="resetPasswordVisible=true">忘记密码?</a>
					<a style="float:right; color:#409EFF" href="#" @click="registerVisible=true">没有账号，立即注册?</a>
				</div>
			</el-col>
		</el-row>
		<el-dialog title="用户注册" :visible.sync="registerVisible">
			<div style="margin:0px auto" v-loading="loading">
			<el-form style="padding:50px" label-width="70px" ref="registerForm" :model="registerForm" :rules="registerRules" v-loading="loading">
				<el-form-item label="手机号" prop="phone">
					<el-input type="text" v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
				</el-form-item>
				<el-form-item label="验证码" prop="vcode">
					<el-input type="text" v-model="registerForm.vcode" placeholder="请输入短信验证码">
						<el-button slot="append" @click="getVcode(registerForm.phone)">获取验证码</el-button>
					</el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input type="password" v-model="registerForm.password" placeholder="设置登录密码"></el-input>
				</el-form-item>
				<el-button style="width:80%; margin:16px" type="primary" v-on:click="register('registerForm')">立即注册</el-button>
				<el-checkbox v-model="checked">我已阅读并同意HBZB电子招投标交易平台<a href="agreement.html" target="_blank">《用户注册协议》</a></el-checkbox>
			</el-form>
			</div>
		</el-dialog>
		<el-dialog title="忘记密码" :visible.sync="resetPasswordVisible">
			<el-form style="padding:50px" label-width="80px" ref="resetPasswordForm" :model="resetPasswordForm" :rules="resetPasswordRules" v-loading="loading">
				<el-form-item label="手机号" prop="phone">
					<el-input type="text" v-model="resetPasswordForm.phone" placeholder="请输入手机号"></el-input>
				</el-form-item>
				<el-form-item label="验证码" prop="vcode">
					<el-input type="text" v-model="resetPasswordForm.vcode" placeholder="请输入短信验证码">
						<el-button slot="append" @click="getVcode(resetPasswordForm.phone)">获取验证码</el-button>
					</el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="password">
					<el-input type="password" v-model="resetPasswordForm.password" placeholder="请输入新密码"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="repassword">
					<el-input type="password" v-model="resetPasswordForm.repassword" placeholder="请再次输入新密码"></el-input>
				</el-form-item>
				<el-button style="width:80%; margin:16px" type="primary" v-on:click="resetPassword('resetPasswordForm')">重置密码</el-button>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		data () {
			var checkPassword = (rule, value, callback) => {
        		if (!value) {
        			callback(new Error('请再次输入新密码'));
        		} else if (value != this.resetPasswordForm.password) {
	        		callback(new Error('两次输入密码不一致'));
	        	} else {
	        		callback();
	        	}
        	};
			return {
				loginForm: { username: '', password: '' },
				loginRules: {
					username: [{ required: true, message: '请输入手机号或账号', trigger: 'blur' }],
					password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
				},
				registerForm: { phone: '', vcode: '', password: '' },
				registerRules: { 
					phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
					vcode: [{ required: true, message: '请输入短信验证码', trigger: 'blur' }], 
					password: [{ required: true, message: '请设置登录密码', trigger: 'blur' }]
				},
				resetPasswordForm: { phone: '', vcode: '', password: '', repassword: '' },
				resetPasswordRules: { 
					phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
					vcode: [{ required: true, message: '请输入短信验证码', trigger: 'blur' }], 
					password: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
					repassword: [{ required: true, validator: checkPassword, trigger: 'blur' }]
				},
				registerVisible: false,
				resetPasswordVisible: false,
				checked: true,
				loading: false
			}
		},
		methods: {
			// 用户登录
			login(loginForm) {
				this.$refs[loginForm].validate((valid) => {
					if (valid) {
						this.loading = true;
						let params = { 
							"grant_type": "password",
							"username": this.loginForm.username,
							"password": this.loginForm.password
						};
						this.$http.login('/uaa/oauth/token', params).then((data) => {
			                this.$http.get('/uaa/user/detail').then((data) => {
			                	sessionStorage.setItem('currentUser', JSON.stringify(data));
				                this.loading = false;
				                this.$message({ type: 'success', message: '登录成功！' });
				                this.$router.push('/');
			                }).catch(() => this.loading = false);
						}).catch(() => this.loading = false);
					} else {
						return false;
					}
				})
			},
			// 获取验证码
			getVcode(phone) {
				this.loading = true;
				this.$http.getVcode('/uaa/sms_vcode?phone=' + phone).then((data) => {
					this.loading = false;
					this.$message({ type: 'success', message: '验证码已发送！' });
				}).catch(() => this.loading = false);
			},
			// 用户注册
			register(registerForm) {
				this.$refs[registerForm].validate((valid) => {
					if (valid) {
						if(this.checked) {
							this.loading = true;
			                this.$http.post('/uaa/register', this.registerForm).then((data) => {
			                	this.loading = false;
			                	this.$message({ type: 'success', message: '注册成功！' });
			                	this.registerForm = {};
								this.registerVisible = false;
			                }).catch(() => this.loading = false);
						} else {
							this.$message({ type: 'error', message: '请勾选注册协议' });
						}
					} else {
						console.log('error register!!');
						return false;
					}
				})
			},
			// 重置密码
			resetPassword(resetPasswordForm) {
				this.$refs[resetPasswordForm].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$http.post('/uaa/reset_password', this.resetPasswordForm).then((data) => {
							this.loading = false;
							this.$message({ type: 'success', message: '密码重置成功！' });
							this.resetPasswordForm = {};
							this.resetPasswordVisible = false;
						}).catch(() => this.loading = false);
					} else {
						console.log('error reset password!!');
						return false;
					}
				})
			}
		}
	}
</script>