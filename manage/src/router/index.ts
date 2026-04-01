import { createRouter, createWebHistory, routeLocationKey } from 'vue-router'
import login from '@/view/login.vue'
import manage from '@/view/manage.vue'
import home from '@/components/home.vue'
import { mockMenu } from '@/types.ts/meaus'
import { reactive,ref } from 'vue'
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

//动态路由
import type { RouteRecordRaw } from 'vue-router';
interface MenuItem {
  name: string
  path: string
  component?: string
  permission?: string
  children?: MenuItem[]
}
const routes = reactive<RouteRecordRaw[]>([]);
function addRoutesFun(mockMenu:MenuItem[]){
 
  mockMenu.forEach(item=>{
    if(item.children && item.children.length>0){
      item.children.forEach(child => {
        routes.push({
          path: child.path,
          name: child.name,
          component: () => import(`@/components/${child.component}`) // 动态导入
        });
      });
    }else{
       routes.push({
        path:item.path,
        name:item.name,
        component:()=>import(`@/components/${item.component}`)
      })
    }
  })
  routes.forEach(item =>{
  router.addRoute('manage', item);

})
console.log('999',routes)
}

addRoutesFun(mockMenu)
export default router
