import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import clientPage from '@/views/clientPage.vue'
import home from '@/layouts/client/home.vue'
import drama from '@/layouts/client/drama.vue'
import actorInfo from '@/layouts/client/actorInfo.vue'
import communicate from '@/layouts/client/communicate.vue'
import knowledge from '@/layouts/client/knowledge.vue'
import news from '@/layouts/client/news.vue'
import profile from '@/layouts/client/profile.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'home',
      component: HomeView,
    },
    {
      path:'/',
      name:'clientPage',
      component:clientPage,
      redirect:'home',
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
