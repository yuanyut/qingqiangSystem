import { createRouter, createWebHistory } from 'vue-router'
import login from '@/view/login.vue'
import manage from '@/view/manage.vue'
import { mockMenu } from '@/types/meaus'
import { useUserInfoStore } from '@/stores/userInfo'

// 1. 创建路由
const router = createRouter({
  history: createWebHistory('/manage/'),
  routes: [
    { path: '/', name: 'login', component: login },
    {
      path: '/manage',
      name: 'manage',
      redirect: '/manage/home',
      component: manage,
      children: []
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

// 3. 递归添加路由
function addRoutes(menuList) {
  menuList.forEach(item => {
    if (item.component) {
      const componentLoader = modules[item.component]
      if (componentLoader) {
        router.addRoute('manage', {
          path: item.path,
          name: item.name,
          component: componentLoader,
          meta: {
            roles: item.role  // 从菜单配置里取role
          }
        })
      } else {
        console.warn(`组件不存在: ${item.component}`)
      }
    }
    if (item.children && item.children.length > 0) {
      addRoutes(item.children)
    }
  })
}

// 执行添加路由
addRoutes(mockMenu)

// 4. 路由守卫
router.beforeEach(async (to) => {
  const userStore = useUserInfoStore()
  
  // 先尝试从 localStorage 恢复登录状态（刷新页面后）
  userStore.restoreFromLocalStorage()
  
  const token = userStore.UserInfos.token
  let role = userStore.UserInfos.role

  // 如果有 token 但没有 role，尝试获取用户信息
  if (token && !role) {
    try {
      await userStore.fetchUserInfo()  // 调用 /me 接口
      role = userStore.UserInfos.role  // 重新获取
    } catch (error) {
      console.error('获取用户信息失败', error)
      // 获取失败，清除 token 并跳转登录
      userStore.logout()
      return '/'
    }
  }

  // // 未登录，跳登录页
  if (to.path !== '/' && !token) {
     return '/'
  }

  // // 已登录但访问登录页 → 跳首页
  if (to.path === '/' && token) {
     return '/manage'
  }

  // 检查路由权限
  if (to.meta.roles && (to.meta.roles as string[]).length > 0 && !(to.meta.roles as string[]).includes(role!)) {
     return '/403'
}

  return true
})

export default router
