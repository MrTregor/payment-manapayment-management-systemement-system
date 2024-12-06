import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
});

axiosInstance.interceptors.request.use(
    config => {
        const token = localStorage.getItem('jwt');
        console.log('Token in axios interceptor:', token);

        if (token) {
            try {
                const decodedToken = window.jwtDecode(token);
                console.log('Decoded token in axios:', decodedToken);

                const currentTime = Math.floor(Date.now() / 1000);
                console.log('Current time:', currentTime);
                console.log('Token expiration:', decodedToken.exp);

                if (decodedToken.exp < currentTime) {
                    console.log('Token expired in axios');
                    localStorage.removeItem('jwt');
                    window.location.href = '/login';
                    return config;
                }

                config.headers['Authorization'] = `Bearer ${token}`;
            } catch (error) {
                console.error('Invalid token in axios', error);
                localStorage.removeItem('jwt');
                window.location.href = '/login';
            }
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

axiosInstance.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            console.log('401 error in axios');
            // localStorage.removeItem('jwt');
            // window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;