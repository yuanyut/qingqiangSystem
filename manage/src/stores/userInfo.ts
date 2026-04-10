import { defineStore } from 'pinia'
import type {UserInfo} from '@/types/user'
import { reactive,ref } from 'vue'
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
    return {
        UserInfos,
        // 方法
        setUserInfo,
        
    }
})
