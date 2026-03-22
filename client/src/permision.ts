import {useUserInfoStore} from '@/stores/userInfo'
const userInfo =useUserInfoStore();
import router from './router';
const isLogin = userInfo.UserInfos.isLogin;
router.beforeEach((to,form,next)=>{

})