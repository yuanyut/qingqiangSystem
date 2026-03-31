import { createRouter, createWebHistory } from 'vue-router'
import login from '@/view/login.vue'
import home from '@/layouts/home.vue'
import manage from '@/view/manage.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      component:login
    },
    {
      path:'/manage/',
      name:'manage',
      redirect:'home',
      component:manage,
      children:[{
        path:'home',
        component:home
      }]
    }
  ],
})

export default router
