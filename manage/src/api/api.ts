import api from './common'

// 响应结果类型
export interface ResponseResult<T> {
  code: number
  message: string
  data: T
}

// 总览数据类型
export interface OverviewData {
  todayVisit: number
  dramaCount: number
  userTotal: number
  pv: number
  uv: number
}

// 剧目分类数据类型
export interface CategoryData {
  category: string
  count: number
}

// 内容新增趋势数据类型
export interface ContentTrendData {
  time: string[]
  dramaCount: number[]
  articleCount: number[]
}

// 行为趋势数据类型
export interface BehaviorTrendData {
  time: string[]
  visitCount: number[]
  collectCount: number[]
  shareCount: number[]
}

// 剧目TOP10数据类型
export interface DramaTop10Data {
  name: string
  viewCount: number
}

// 内容质量维度数据类型
export interface QualityScoreData {
  overview: {
    avgUserRating: number
    avgCommentQuality: number
    highQualityContentRate: number
    activeContentCount: number
    contentCount: number
  }
  radar: {
    indicator: { name: string; max: number }[]
    value: number[]
  }
}

// 登录地图数据类型
export interface GeoData {
  province: string
  count: number
}

// 总览数据
export const getOverview = () => {
  return api.get<ResponseResult<OverviewData>>('/manage/dashboard/overview')
}

// 剧目分类饼图
export const getDramaCategory = () => {
  return api.get<ResponseResult<CategoryData[]>>('/manage/dashboard/drama-category')
}

// 内容新增趋势
export const getContentTrend = (type: string = 'day') => {
  return api.get<ResponseResult<ContentTrendData>>('/manage/dashboard/content-trend', { type })
}

// 行为趋势
export const getBehaviorTrend = (type: string = 'day') => {
  return api.get<ResponseResult<BehaviorTrendData>>('/manage/dashboard/behavior-trend', { type })
}

// 剧目TOP10
export const getDramaTop10 = () => {
  return api.get<ResponseResult<DramaTop10Data[]>>('/manage/dashboard/drama-top10')
}

// 内容质量维度
export const getQualityScore = () => {
  return api.get<ResponseResult<QualityScoreData>>('/manage/dashboard/quality-score')
}

// 登录地图
export const getGeo = () => {
  return api.get<ResponseResult<GeoData[]>>('/manage/dashboard/geo')
}