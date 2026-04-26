import api from '../utils/request';

export function login(data) {
    return api.post('/login', data);
}

export function register(data) {
    return api.post('/register',data);
}