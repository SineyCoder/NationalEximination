//基本设置
import axios from 'axios';
import R from "./R";

// axios.defaults.baseURL = 'http://39.105.173.231:4585'; //请求的时候，baseURL+接口URL（不通过代理 需要解决跨域问题） //39.105.173.231    47.110.95.240
axios.defaults.baseURL = R.BASE_URL; //请求的时候，baseURL+接口URL（不通过代理 需要解决跨域问题） //39.105.173.231    47.110.95.240
axios.defaults.withCredentials=true;

export default axios;
