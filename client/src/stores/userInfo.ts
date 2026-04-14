import { defineStore } from 'pinia'
import type { UserInfo } from '@/types/user'
import { reactive, computed } from 'vue'
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
    
    return {
        UserInfos,
        isLoggedIn,
        // 方法
        setUserInfo,
        
    }
})
