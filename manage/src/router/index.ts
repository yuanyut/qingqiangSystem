import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { reactive } from 'vue'
import login from '@/view/login.vue'
import manage from '@/view/manage.vue'
import { mockMenu } from '@/types.ts/meaus'
import type { DefineComponent } from 'vue'
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
    // 如果有子菜单，但没有 component，设置 redirect
    if (item.children && item.children.length > 0 && !item.component) {
      router.addRoute(parentName, {
        path: item.path.replace(/^\//, ''), // 去掉前导 /
        name: item.name,
        redirect: item.children[0]!.path.replace(/^\//, ''),
      })
    }

    // 如果有 component，动态加载
    // 有 children，没 component → 只做菜单分组 + redirect
    // 有 component → 点击直接渲染页面
    // 没有 children，必须有 component → 点击直接渲染页面
    if (item.component) {
      const mod = modules[`/src/components/${item.component}`]
      if (mod) {
        router.addRoute(parentName, {
          path: item.path.replace(/^\//, ''),
          name: item.name,
          component: () => (mod() as Promise<{ default: DefineComponent }>)
            .then(m => m.default)
        })
      }
    }

    // 递归处理子菜单
    if (item.children && item.children.length > 0) {
      addRoutesFun(item.children, parentName)
    }
  })
}

// 7. 执行动态路由添加
addRoutesFun(mockMenu)

export default router