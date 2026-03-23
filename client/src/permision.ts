

import router from './router';
import {useUserInfoStore} from '@/stores/userInfo'
router.beforeEach((to,form,next)=>{
    
const userInfo =useUserInfoStore();
 const isLogin = userInfo.UserInfos.isLogin;
    //没登录
   console.log('导航守卫')
    if(!isLogin){
        if(to.path.includes('profile')){
            next('/login')
        }
        else{
            next();
        }
    }
    else{
        next()
    }
    
})