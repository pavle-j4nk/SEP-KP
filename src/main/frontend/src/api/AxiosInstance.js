import axios from 'axios';

const hosts = {
    development: 'http://localhost/kp/',
    production: '/kp/'
};

const axiosInstance = axios.create({
    baseURL: hosts[process.env.NODE_ENV]
});

export default axiosInstance;
