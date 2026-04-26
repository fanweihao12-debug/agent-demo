import {reactive, ref} from "vue";
import axios from 'axios';

const api = axios.create({ baseURL: '/api/user' });

api.interceptors.request.use(
    config => {
        const accessToken = localStorage.getItem('accessToken');

        if (accessToken) {
            config.headers.Authorization = `${accessToken}`;
        }

        return config;
    },
    error => Promise.reject(error)
);

export default api;