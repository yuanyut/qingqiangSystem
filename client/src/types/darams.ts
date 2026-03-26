import { ref, reactive } from 'vue'
export const cate = reactive([
  {
    name: '分类'
  },
  {
    name: '全部',
  },
  {
    name: '传统剧目',
  },
  {
    name: '现代剧目',
  },
  {
    name: '经典折子戏',
  }, {
    name: '新编历史剧'
  }
])
export const years = reactive([
  {
    name: '年代',
  },
  {
    name: '全部',
  },
  {
    name: '清代',
  },
  {
    name: '民国',
  },
  {
    name: '建国后',
  }, {
    name: '现代'
  }
])
export const paixu = reactive([
  {
    name: '排序',
  },
  {
    name: '默认排序',
  },
  {
    name: '热度最高',
  },
  {
    name: '最新发布',
  },
  {
    name: '最多播放',
  }
])
