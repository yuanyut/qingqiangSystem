

import router from './router';

router.beforeEach((to, from) => {
  console.log('导航守卫', to.path);
  return true;
})