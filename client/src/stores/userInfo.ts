import { defineStore } from 'pinia'
import type { UserInfo } from '@/types/user'
import { reactive, computed } from 'vue'
import { getUserInfo } from '@/api/user'
export const useUserInfoStore = defineStore('userInfo',()=>{
    
    const UserInfos = reactive<UserInfo>({
        username:'',
        nickname:'游客',
        avatar:'',
        isLogin:false,
    })
     function setUserInfo(userInfo:UserInfo){
        UserInfos.username=userInfo.username
        UserInfos.nickname=userInfo.nickname
        UserInfos.avatar=userInfo.avatar
        UserInfos.isLogin=true
    }
    
    // 检查本地存储的token是否存在
    const isLoggedIn = computed(() => {
        return localStorage.getItem('token') !== null
    })
    
    // 验证token有效性
    const validateToken = async () => {
        const token = localStorage.getItem('token')
        if (token) {
            try {
                const res = await getUserInfo()
                if (res.code === 200 && res.data) {
                    // token有效，更新用户信息
                    setUserInfo({
                        username: res.data.username,
                        nickname: res.data.nickname,
                        avatar: res.data.avatar,
                        isLogin: true
                    })
                    return true
                } else {
                    // token无效，清除token
                    localStorage.removeItem('token')
                    UserInfos.isLogin = false
                    return false
                }
            } catch (error) {
                // 请求失败，清除token
                localStorage.removeItem('token')
                UserInfos.isLogin = false
                return false
            }
        }
        return false
    }
    
    // 退出登录
    const logout = () => {
        localStorage.removeItem('token')
        UserInfos.username = ''
        UserInfos.nickname = '游客'
        UserInfos.avatar = ''
        UserInfos.isLogin = false
    }
    
    return {
        UserInfos,
        isLoggedIn,
        // 方法
        setUserInfo,
        validateToken,
        logout
    }
})
