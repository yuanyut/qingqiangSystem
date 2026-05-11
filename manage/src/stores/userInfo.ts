import { defineStore } from 'pinia'
import type { UserInfo } from '@/types/user'
import { reactive } from 'vue'
import { getAdminInfo } from '@/api/api'  // 你的 /me 接口

export const useUserInfoStore = defineStore('userInfo', () => {
  // 状态
  const UserInfos = reactive<UserInfo>({
    username: '',
    nickname: '游客',
    avatar: '',
    isLogin: false,
    role: '',
    token: ''
  })

  // 设置用户信息（登录时调用）
  function setUserInfo(userInfo: UserInfo) {
    UserInfos.username = userInfo.username
    UserInfos.nickname = userInfo.nickname
    UserInfos.avatar = userInfo.avatar
    UserInfos.role = userInfo.role || ''
    UserInfos.isLogin = true
  }

  // 设置 token
  function setToken(token: string) {
    UserInfos.token = token
    localStorage.setItem('token', token)
  }

  // 获取用户信息（调用 /me 接口）
  async function fetchUserInfo() {
    try {
      const res = await getAdminInfo()
      if (res.code === 200) {
        UserInfos.username = res.data.username
        UserInfos.nickname = res.data.nickname || res.data.username
        UserInfos.avatar = res.data.avatar || ''
        UserInfos.role = res.data.role
        UserInfos.isLogin = true

        // 存到 localStorage，方便页面刷新后恢复
        localStorage.setItem('role', res.data.role!)
        localStorage.setItem('username', res.data.username)

        return res.data
      } else {
        throw new Error(res.msg || '获取用户信息失败')
      }
    } catch (error) {
      console.error('fetchUserInfo error:', error)
      throw error
    }
  }

  // 从 localStorage 恢复登录状态（页面刷新时调用）
  function restoreFromLocalStorage() {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    const username = localStorage.getItem('username')

    if (token && role) {
      UserInfos.token = token
      UserInfos.role = role
      UserInfos.username = username || ''
      UserInfos.isLogin = true
      return true
    }
    return false
  }

  // 登出
  function logout() {
    UserInfos.username = ''
    UserInfos.nickname = '游客'
    UserInfos.avatar = ''
    UserInfos.role = ''
    UserInfos.token = ''
    UserInfos.isLogin = false
    localStorage.clear()
  }

  return {
    UserInfos,      // 状态
    setUserInfo,    // 方法
    setToken,
    fetchUserInfo,
    restoreFromLocalStorage,
    logout
  }
})
