import api from '../Utils/request';

// stores/userStore.js
import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        // 用户基本信息
        userResponse: {
            id: null,
            name: '',
            age: null,
            phoneNumber: '',
            email:'',
            imageUrl:''
        },
        // 登录状态
        isLoggedIn: false,
        // token（如果有）
        token: null
    }),

    getters: {
        // 是否已登录
        isAuthenticated: (state) => state.isLoggedIn && !!state.token,

        // 用户显示名称（优先显示 name，没有则显示手机号）
        displayName: (state) => state.userResponse.name || state.userResponse.phoneNumber || '用户',

        // 用户年龄（如果有）
        userAge: (state) => state.userResponse.age ? `${state.userResponse.age}岁` : '年龄未设置',

        // 用户手机号（脱敏显示）
        maskedPhone: (state) =>state.userResponse.phoneNumber,

        // 用户ID
        userId: (state) => state.userResponse.id,

        // 用户完整信息
        userInfo: (state) => state.userResponse
    },

    actions: {
        // 登录方法
        async login(loginData) {
            try {
                // 假设你的登录接口返回用户信息
                const response = await api.post('/user/login', loginData);
                const { code, msg, data } = response.data;

                if (code === 1) {
                    // 更新用户信息
                    this.userResponse = {
                        id: data.userResponse.id,
                        name: data.userResponse.name,
                        age: data.userResponse.age,
                        phoneNumber: data.userResponse.phoneNumber,
                        email:data.userResponse.email,
                        imageUrl:data.userResponse.imageUrl
                    };
                    this.isLoggedIn = true;
                    this.token = data.tokenResponse?.accessToken || null;

                    // 保存到 localStorage
                    this.saveToLocalStorage();

                    return { success: true, data };
                }
                return { success: false, message: msg || '登录失败' };
            } catch (error) {
                console.error('登录失败:', error);
                return { success: false, message: error.message };
            }
        },

        // 注册方法
        async register(registerData) {
            try {
                const response = await api.post('/user/register', registerData);
                return response;
            } catch (error) {
                console.error('注册失败:', error);
                return { success: false, message: error.message };
            }
        },

        // 退出登录
        async logout() {
            try {
                const userId = this.userResponse.id;
                if (userId) {
                    await api.post(`/logout/${userId}`);
                }
                this.clearUserInfo();
                return { success: true };
            } catch (error) {
                console.error('退出失败:', error);
                this.clearUserInfo();
                return { success: false, message: error.message };
            }
        },

        // 清除用户信息
        clearUserInfo() {
            this.userResponse = {
                id: null,
                name: '',
                age: null,
                phoneNumber: '',
                email:'',
                imageUrl:''
            };
            this.isLoggedIn = false;
            this.token = null;

            // 清除 localStorage
            localStorage.removeItem('userInfo');
            localStorage.removeItem('token');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('userId');
        },

        // 保存到 localStorage
        saveToLocalStorage() {
            const userData = {
                userResponse: this.userResponse,
                isLoggedIn: this.isLoggedIn,
                token: this.token
            };
            localStorage.setItem('userInfo', JSON.stringify(userData));
            if (this.token) {
                localStorage.setItem('token', this.token);
                localStorage.setItem('accessToken', this.token);
            }
            if (this.userResponse.id) localStorage.setItem('userId', this.userResponse.id);
        },


        // 设置用户信息（直接传入 userResponse 对象）
        setUserInfo(userResponse, token = null) {
            this.userResponse = {
                id: userResponse.id,
                name: userResponse.name,
                age: userResponse.age,
                phoneNumber: userResponse.phoneNumber
            };
            this.isLoggedIn = true;
            this.token = token;
            this.saveToLocalStorage();
        }
    }
});
