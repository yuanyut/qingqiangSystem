//这里应该是api获取数据
export const mockMenu = [
  {
    name: "首页",
    path: "/manage/home",
  },
  {
    name: "内容管理",
    path: "/manage/content",
    children: [
      {
        name: "文章管理",
        path: "/manage/content/article",
        component: "Content/Article/index.vue",
        permission: "content:article:list"
      },
      {
        name: "剧目管理",
        path: "/manage/content/drama",
        component: "Content/Drama/index.vue",
        permission: "content:drama:list"
      }
    ]
  },
  {
    name: "用户管理",
    path: "/manage/user",
    children: [
      {
        name: "1管理",
        path: "/manage/user/article",
        component: "Content/Article/index.vue",
        permission: "content:article:list"
      },
      {
        name: "2管理",
        path: "/manage/user/drama",
        component: "Content/Drama/index.vue",
        permission: "content:drama:list"
      }
    ]
  }
]