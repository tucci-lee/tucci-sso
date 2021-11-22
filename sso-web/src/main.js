import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
// axios 请求
import axios from "axios";
// elementui
import ElementPlus, {ElMessage} from 'element-plus'
import 'element-plus/dist/index.css'
// 日期组件
import moment from 'moment'
// 自定义js
import url from "@/js/url";

axios.defaults.withCredentials = true;
// axios.defaults.baseURL = url.host; // 测试使用

const app = createApp(App);
app.use(router);
app.use(ElementPlus);
app.config.globalProperties.$message = ElMessage;
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$moment = moment;

app.mount('#app');

/**
 * axios拦截器，错误直接提示
 */
axios.interceptors.response.use(
    res => {
        if (res.data.code === 10101) {
            window.location.href = url.html.signinHtml + "?" + url.redirectUri + "=" + encodeURIComponent(window.location.href);
            return;
        }
        if (!res.data.status) {
            ElMessage.error(res.data.msg);
        }
        return res.data;
    },
    err => {
        let data = err.response.data;
        if (data) {
            ElMessage.error(data.msg)
        } else {
            ElMessage.error("error")
        }
        return Promise.reject(err);
    });