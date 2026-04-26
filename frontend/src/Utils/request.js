import axios from 'axios';

const api = axios.create({ baseURL: 'http://localhost:8081/user' });



api.interceptors.request.use(
    config => {
        const accessToken = localStorage.getItem('accessToken') || localStorage.getItem('token');

        if (accessToken) {
            config.headers.Authorization = `${accessToken}`;
        }

        return config;
    },
    error => Promise.reject(error)
);

export default api;
