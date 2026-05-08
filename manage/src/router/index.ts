import { createRouter, createWebHistory } from 'vue-router'
import login from '@/view/login.vue'
import manage from '@/view/manage.vue'
import { mockMenu } from '@/types/meaus'

// 1. 创建路由
const router = createRouter({
  //history: createWebHistory(import.meta.env.BASE_URL/manage),
  history: createWebHistory('/manage/'),  // 添加 base 路径
  routes: [

    { path: '/', name: 'login', component: login },

    {
      path: '/manage',
      name: 'manage',
      redirect: '/manage/home',
      component: manage,
      children: [
      ]
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('@/view/404.vue')
    }
  ]
})

// 2. 扫描所有组件
const modules = import.meta.glob('@/components/**/**.vue')
console.log(modules)
// 3. 定义菜单类型（component 改为可选）
interface MenuItem {
  name: string
  path: string
  component?: string
  icon?: string
  permission?: string
  children?: MenuItem[]
}

// 4. 递归添加路由
function addRoutes(menuList: MenuItem[]) {
  menuList.forEach(item => {

    // 只有当 component 存在时才添加路由,这里只有一级菜单
    if (item.component) {
      const componentLoader = modules[item.component]

      if (componentLoader) {
        router.addRoute('manage', {
          path: item.path,
          name: item.name,
          component: componentLoader,
          meta: {
            icon: item.icon,
            permission: item.permission
          }
        })
      } else {
        console.warn(`组件不存在: ${item.component}`)
      }
    }

    // 递归处理子路由
    if (item.children && item.children.length > 0) {
      addRoutes(item.children)
    }
  })
}

// 5. 执行添加路由
addRoutes(mockMenu)
// 打印所有路由
// console.log('=== 所有路由路径 ===')
// router.getRoutes().forEach(route => {
//   console.log(route)
// })
export default router
