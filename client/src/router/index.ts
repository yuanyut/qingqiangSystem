import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import clientPage from '@/views/clientPage.vue'
import home from '@/views/home.vue'
import drama from '@/views/drama.vue'
import actorInfo from '@/views/actorInfo.vue'
import communicate from '@/views/communicate.vue'
import knowledge from '@/views/knowledge.vue'
import news from '@/views/news.vue'
import profile from '@/views/profile.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path:'/user',
      name:'clientPage',
      component:clientPage,
      redirect:'/user/home',
      children:[
        {
          path:'home',
          component:home,
        },
        {
         path:'drama',
         component:drama, 
        },
        {
          path:'actorInfo',
          component:actorInfo,
        },
        {
          path:'communicate',
          component:communicate,
        },
        {
          path:'knowledge',
          component:knowledge,
        },
        {
          path:'news',
          component:news,
        },
        {
          path:'profile',
          component:profile,
        },
      ]
    }
  ],
})

export default router
