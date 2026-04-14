

import router from './router';
import { useUserInfoStore } from './stores/userInfo';

router.beforeEach(async (to, from) => {
  console.log('导航守卫', to.path);
  const userStore = useUserInfoStore();
  // 如果有token，验证其有效性
  if (userStore.isLoggedIn) {
    await userStore.validateToken();
  }
  return true;
})