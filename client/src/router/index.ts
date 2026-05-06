import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import clientPage from '@/views/clientPage.vue'
import home from '@/layouts/client/home.vue'
import drama from '@/layouts/client/drama.vue'
import dramaDetail from '@/layouts/client/dramaDetail.vue'
import actorInfo from '@/layouts/client/actorInfo.vue'
import actorDetail from '@/layouts/client/actorDetail.vue'
import communicate from '@/layouts/client/communicate.vue'
import knowledge from '@/layouts/client/knowledge.vue'
import knowledgeDetail from '@/layouts/client/knowledgeDetail.vue'
import news from '@/layouts/client/news.vue'
import newsDetail from '@/layouts/client/newsDetail.vue'
import newsList from '@/layouts/client/newsList.vue'
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
      redirect:'/drama',
      children:[
        // {
        //   path:'home',
        //   component:home,
        // },
        {
         path:'drama',
         component:drama, 
        },
        {
          path:'drama/:id',
          component:dramaDetail,
        },
        {
          path:'actorInfo',
          component:actorInfo,
        },
        {
          path:'actor/:id',
          component:actorDetail,
        },
        // {
        //   path:'communicate',
        //   component:communicate,
        // },
        {
          path:'knowledge',
          component:knowledge,
        },
        {
          path:'knowledge/:id',
          component:knowledgeDetail,
        },
        {
          path:'news',
          component:news,
        },
        {
          path:'news/:id',
          component:newsDetail,
        },
        {
          path:'news/list',
          component:newsList,
        },
        {
          path:'profile',
          component:profile,
          meta: {
            requiresAuth: true
          }
        },
      ]
    }
  ],
})

export default router
