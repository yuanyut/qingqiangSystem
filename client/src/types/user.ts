//定义登录接口
export interface UserInfo {
    username: string    // 用户名必须是字符串
    nickname?: string
    avatar?: string
    isLogin?: boolean
}
