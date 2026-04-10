//这里应该是api获取数据
export const mockMenu = [
  {
    "name": "数据大屏",
    "path": "home",
    "component": "/src/components/home.vue",
    "permission": "home:home:list"
  },

  {
    "name": "统计分析",
    "path": "analysis",
    "icon": "DataAnalysis",
    "children": [
      {
        "name": "剧目热度",
        "path": "analysis/drama",
        "component": "/src/components/Analysis/Drama/index.vue",
        "permission": "analysis:drama:list"
      },
      {
        "name": "用户画像",
        "path": "analysis/user",
        "component": "/src/components/Analysis/User/index.vue",
        "permission": "analysis:user:list"
      },
      // {
      //   "name": "PV/UV 数据",
      //   "path": "analysis/pvuv",
      //   "component": "/src/components/Analysis/PVUV/index.vue",
      //   "permission": "analysis:pvuv:list"
      // }
    ]
  },
  {
    "name": "系统管理",
    "path": "/system",
    "icon": "Setting",
    "children": [
      {
        "name": "用户管理",
        "path": "system/user",
        "component": "/src/components/System/User/index.vue",
        "permission": "system:user:list"
      },
      // {
      //   "name": "角色管理",
      //   "path": "system/role",
      //   "component": "/src/components/System/Role/index.vue",
      //   "permission": "system:role:list"
      // },
      // {
      //   "name": "权限管理",
      //   "path": "system/permission",
      //   "component": "/src/components/System/Permission/index.vue",
      //   "permission": "system:permission:list"
      // },
      // {
      //   "name": "菜单管理",
      //   "path": "system/menu",
      //   "component": "/src/components/System/Menu/index.vue",
      //   "permission": "system:menu:list"
      // },
      // {
      //   "name": "操作日志",
      //   "path": "system/log",
      //   "component": "/src/components/System/Log/index.vue",
      //   "permission": "system:log:list"
      // }
    ]
  },
  {
    "name": "内容管理",
    "path": "/contents",
    "icon": "Document",
    "children": [
      {
        "name": "剧目管理",
        "path": "contents/drama",
        "component": "/src/components/Contents/Drama/index.vue",
        "permission": "contents:drama:list"
      },
      {
        "name": "文化管理",
        "path": "contents/article",
        "component": "/src/components/Contents/Article/index.vue",
        "permission": "contents:article:list"
      },
      {
        "name": "名家管理",
        "path": "contents/actor",
        "component": "/src/components/Contents/Actor/index.vue",
        "permission": "contents:article:list"
      },
      {
        "name": "资讯管理",
        "path": "contents/profile",
        "component": "/src/components/Contents/Profile/index.vue",
        "permission": "contents:article:list"
      },
      // {
      //   "name": "图片资源管理",
      //   "path": "contents/image",
      //   "component": "/src/components/Contents/Image/index.vue",
      //   "permission": "contents:image:list"
      // },
      // {
      //   "name": "视频资源管理",
      //   "path": "contents/video",
      //   "component": "/src/components/Contents/Video/index.vue",
      //   "permission": "contents:video:list"
      // },
      // {
      //   "name": "轮播图管理",
      //   "path": "contents/banner",
      //   "component": "/src/components/Contents/Banner/index.vue",
      //   "permission": "contents:banner:list"
      // }
    ]
  },
  // {
  //   "name": "用户管理",
  //   "path": "/user",
  //   "icon": "User",
  //   "children": [
  //     {
  //       "name": "前台用户列表",
  //       "path": "user/list",
  //       "component": "/src/components/User/List/index.vue",
  //       "permission": "user:list"
  //     },
  //     {
  //       "name": "用户行为数据",
  //       "path": "user/activity",
  //       "component": "/src/components/User/Activity/index.vue",
  //       "permission": "user:activity:list"
  //     }
  //   ]
  // },

  {
    "name": "推荐系统",
    "path": "/recommend",
    "icon": "Star",
    "children": [
      {
        "name": "推荐算法配置",
        "path": "recommend/config",
        "component": "/src/components/Recommend/Config/index.vue",
        "permission": "recommend:config:list"
      },
      {
        "name": "推荐命中率统计",
        "path": "recommend/stat",
        "component": "/src/components/Recommend/Stat/index.vue",
        "permission": "recommend:stat:list"
      }
    ]
  },
  {
    "name": "评论审核",
    "path": "/comment",
    "icon": "ChatLineSquare",
    "children": [
      {
        "name": "评论列表",
        "path": "comment/list",
        "component": "/src/components/Comment/List/index.vue",
        "permission": "comment:list"
      },
      {
        "name": "审核操作",
        "path": "comment/audit",
        "component": "/src/components/Comment/Audit/index.vue",
        "permission": "comment:audit:list"
      }
    ]
  },
   {
    "name": "个人管理",
    "path": "/mine",
    "icon": "Mine",
    "children": [
      {
        "name": "个人中心",
        "path": "mine/center",
        "component": "/src/components/Mine/Center/index.vue",
        "permission": "mine:list"
      },
    ]
  },
  // {
  //   "name": "系统设置",
  //   "path": "setting",
  //   "icon": "Tools",
  //   "children": [
  //     {
  //       "name": "数据字典",
  //       "path": "setting/dict",
  //       "component": "/src/components/Setting/Dict/index.vue",
  //       "permission": "setting:dict:list"
  //     },
  //     {
  //       "name": "Banner 配置",
  //       "path": "setting/banner",
  //       "component": "/src/components/Setting/Banner/index.vue",
  //       "permission": "setting:banner:list"
  //     }
  //   ]
  // }
]