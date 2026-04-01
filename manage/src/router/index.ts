import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { reactive } from 'vue'
import login from '@/view/login.vue'
import manage from '@/view/manage.vue'
import { mockMenu } from '@/types.ts/meaus'

// 1. 基础静态路由
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'login', component: login },
    {
      path: '/manage',
      name: 'manage',
      redirect: '/manage/home',
      component: manage,
      children: [
        { path: 'home', name: 'home', component: () => import('@/components/home.vue') }
      ]
    }
  ]
})

// 2. 扫描所有组件，用于动态导入
const modules = import.meta.glob('@/components/**/**.vue')

// 3. 定义 MenuItem 接口
interface MenuItem {
  name: string
  path: string
  component?: string
  permission?: string
  children?: MenuItem[]
}

// 4. 创建 reactive routes，用于存储动态路由
const routes = reactive<RouteRecordRaw[]>([])

// 5. 递归函数：把菜单转成 RouteRecordRaw 并 push 到 routes
function addRoutesFun(menuList: MenuItem[], parentName = 'manage') {
  menuList.forEach(item => {
    if (item.component) {
      const mod = modules[`/src/components/${item.component}`]
      if (mod) {
        routes.push({
          path: item.path,
          name: item.name,
          // 强制懒加载类型
          component: mod as unknown as () => Promise<any>
        })
      }
    }

    if (item.children && item.children.length > 0) {
      addRoutesFun(item.children, parentName)
    }
  })

  // push 完所有动态路由再添加到 router
  routes.forEach(route => {
    router.addRoute(parentName, route)
  })
}

// 7. 执行动态路由添加
addRoutesFun(mockMenu)

export default router