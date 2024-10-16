// 导入axios
import axios from 'axios';
// 导入Message组件 - 用于在请求出错的时候弹出错误消息
import { Message } from 'element-ui';
// 导入MessageBox组件 - 用于在Session失效后对话框,点击对话框后跳转到登录页面
import { MessageBox } from 'element-ui';
// 导入路由器对象
import router from '@/router'

// 创建axios实例, 并命名为http
const http = axios.create({
    // 后端的基础URL地址
    baseURL: process.env.VUE_APP_API_BASE,
    // 设置请求头 - 用于POST请求,如果不设置,那么POST请求后端无法取到数据
    headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
    // 设置请求超时时长 - 如果15秒钟后端没有响应, 请求就结束
    timeout: 15000,
    // 是否携带Cookie
    // withCredentials:true
})
// 设置请求的前置拦截器
http.interceptors.request.use(
    (config) => {
        // 在每次请求发送之前显示加载状态
        try {
            const user = JSON.parse(localStorage.getItem("user"))
            config.headers.Auth = user.token
            return config
        }catch (e) {
            return config
        }
    },
    (error) => {
        return Promise.reject(error)
    }
)
// 设置请求的后置拦截器
http.interceptors.response.use(
    // 每一次http请求回来后都会先执行这里
    (response) => {
        // 取出响应报文(response)里面的响应体(data)
        const data = response.data
        // 此处data的结构 = 后端Result对象的结构 {code， message， data}
        if(data.code == '10000') {
            return data.data
        }
        return Promise.reject(data).catch((error) => {
            if(error.code === '20005'){
                MessageBox.alert(error.message, '提示', {
                    confirmButtonText: '确定',
                    callback: action => {
                        router.replace('/login')
                    }
                })
            }else {
                Message.error(error.message)
            }
        })
    },
    (error) => {
        if (error && error.response) {
            switch (error.response.status) {
                case 404:
                    Message.error({
                        showClose:true,
                        message:'404:找不到资源!'
                    })
                    break
                case 500:
                    Message.error({
                        showClose:true,
                        message:'500:服务器出错!'
                    })
                    break
                default:
                    Message.error({
                        showClose:true,
                        message:'请求失败,请稍后再试或者联系管理员'
                    })
            }
        } else {
            Message.error({
                showClose:true,
                message:'请求失败,请检查网络或者联系管理员'
            })
        }
        return Promise.reject(error)
    }
)
// 导出http对象
export default http;
