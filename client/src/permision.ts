

import router from './router';
import { useUserInfoStore } from './stores/userInfo';

router.beforeEach(async (to, from) => {
  console.log('导航守卫', to.path);
  const userStore = useUserInfoStore();
  // 检查本地存储中是否有token
  const token = localStorage.getItem('token');
  if (token) {
    // 如果有token，验证其有效性
    await userStore.validateToken();
  }
  return true;
})