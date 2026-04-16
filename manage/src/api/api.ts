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
  date: string
  dramaCount: number
  cultureCount: number
}

// 内容新增趋势响应数据类型
export type ContentTrendResponse = ContentTrendData[]

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
  return api.get<ResponseResult<ContentTrendResponse>>('/manage/dashboard/content-trend', { type })
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

// 用户年龄段分布
export const getUserAgeDistribution = () => {
  return api.get<ResponseResult<AgeData[]>>('/manage/dashboard/user-age-distribution')
}

// 用户年龄段数据类型
export interface AgeData {
  name: string
  clicks: number
}

// 用户性别占比数据类型
export interface GenderData {
  name: string
  value: number
}

// 用户性别占比
export const getUserGenderDistribution = () => {
  return api.get<ResponseResult<GenderData[]>>('/manage/dashboard/user-gender-distribution')
}

// 剧目热度趋势数据类型
export interface DramaHeatTrendData {
  date: string
  visitCount: number
  collectCount: number
  shareCount: number
}

// 剧目热度趋势
export const getDramaHeatTrend = (type: string = 'day') => {
  return api.get<ResponseResult<DramaHeatTrendData[]>>('/manage/dashboard/drama-heat-trend', { type })
}