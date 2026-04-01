//这里应该是api获取数据
export const mockMenu = [
  {
    "name": "系统管理",
    "path": "/system",
    "icon": "Setting",
    "children": [
      {
        "name": "用户管理",
        "path": "/system/user",
        "component": "System/User/index.vue",
        "permission": "system:user:list"
      },
      {
        "name": "角色管理",
        "path": "/system/role",
        "component": "System/Role/index.vue",
        "permission": "system:role:list"
      },
      {
        "name": "权限管理",
        "path": "/system/permission",
        "component": "System/Permission/index.vue",
        "permission": "system:permission:list"
      },
      {
        "name": "菜单管理",
        "path": "/system/menu",
        "component": "System/Menu/index.vue",
        "permission": "system:menu:list"
      },
      {
        "name": "操作日志",
        "path": "/system/log",
        "component": "System/Log/index.vue",
        "permission": "system:log:list"
      }
    ]
  },
  {
    "name": "内容管理",
    "path": "/content",
    "icon": "Document",
    "children": [
      {
        "name": "剧目管理",
        "path": "/content/drama",
        "component": "Content/Drama/index.vue",
        "permission": "content:drama:list"
      },
      {
        "name": "文章管理",
        "path": "/content/article",
        "component": "Content/Article/index.vue",
        "permission": "content:article:list"
      },
      {
        "name": "图片资源管理",
        "path": "/content/image",
        "component": "Content/Image/index.vue",
        "permission": "content:image:list"
      },
      {
        "name": "视频资源管理",
        "path": "/content/video",
        "component": "Content/Video/index.vue",
        "permission": "content:video:list"
      },
      {
        "name": "轮播图管理",
        "path": "/content/banner",
        "component": "Content/Banner/index.vue",
        "permission": "content:banner:list"
      }
    ]
  },
  {
    "name": "用户管理",
    "path": "/user",
    "icon": "User",
    "children": [
      {
        "name": "前台用户列表",
        "path": "/user/list",
        "component": "User/List/index.vue",
        "permission": "user:list"
      },
      {
        "name": "用户行为数据",
        "path": "/user/activity",
        "component": "User/Activity/index.vue",
        "permission": "user:activity:list"
      }
    ]
  },
  {
    "name": "统计分析",
    "path": "/analysis",
    "icon": "DataAnalysis",
    "children": [
      {
        "name": "剧目热度分析",
        "path": "/analysis/drama",
        "component": "Analysis/Drama/index.vue",
        "permission": "analysis:drama:list"
      },
      {
        "name": "用户画像分析",
        "path": "/analysis/user",
        "component": "Analysis/User/index.vue",
        "permission": "analysis:user:list"
      },
      {
        "name": "PV/UV 数据",
        "path": "/analysis/pvuv",
        "component": "Analysis/PVUV/index.vue",
        "permission": "analysis:pvuv:list"
      }
    ]
  },
  {
    "name": "推荐系统",
    "path": "/recommend",
    "icon": "Star",
    "children": [
      {
        "name": "推荐算法配置",
        "path": "/recommend/config",
        "component": "Recommend/Config/index.vue",
        "permission": "recommend:config:list"
      },
      {
        "name": "推荐命中率统计",
        "path": "/recommend/stat",
        "component": "Recommend/Stat/index.vue",
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
        "path": "/comment/list",
        "component": "Comment/List/index.vue",
        "permission": "comment:list"
      },
      {
        "name": "审核操作",
        "path": "/comment/audit",
        "component": "Comment/Audit/index.vue",
        "permission": "comment:audit:list"
      }
    ]
  },
  {
    "name": "系统设置",
    "path": "/setting",
    "icon": "Tools",
    "children": [
      {
        "name": "数据字典",
        "path": "/setting/dict",
        "component": "Setting/Dict/index.vue",
        "permission": "setting:dict:list"
      },
      {
        "name": "Banner 配置",
        "path": "/setting/banner",
        "component": "Setting/Banner/index.vue",
        "permission": "setting:banner:list"
      }
    ]
  }
]