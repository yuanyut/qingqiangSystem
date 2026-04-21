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
export interface DramaHeatTrendData { date: string; visitCount: number; collectCount: number; shareCount: number }
export const getDramaHeatTrend = (type: string = 'day') => 
  api.get<ResponseResult<DramaHeatTrendData[]>>('/manage/dashboard/drama-heat-trend', { type })

// 用户管理相关API
export interface UserListParams {
  page: number;
  size: number;
  username?: string;
  nickname?: string;
  status?: number;
}

export interface UserData {
  id?: number | string;
  username: string;
  nickname: string;
  phone: number | string;
  password: string;
  role: string;
  status: number;
}

export const getUserList = (params: UserListParams) => 
  api.get<ResponseResult<{ list: any[]; total: number }>>('/user/admin/list', params)

export const getUserById = (id: number | string) => 
  api.get<ResponseResult<any>>(`/user/admin/${id}`)

export const addUser = (data: UserData) => 
  api.post<ResponseResult<any>>('/user/admin', data)

export const updateUser = (id: number | string, data: UserData) => 
  api.put<ResponseResult<any>>(`/user/admin/${id}`, data)

export const deleteUser = (id: number | string | (number | string)[]) => 
  Array.isArray(id) 
    ? api.delete<ResponseResult<any>>('/user/admin/batch', { data: id })
    : api.delete<ResponseResult<any>>(`/user/admin/${id}`)

// 剧目管理相关API
export interface DramaListParams {
  page: number;
  size: number;
  categoryId?: number;
  keyword?: string;
}

export interface DramaData {
  id?: number;
  name: string;
  cover: string;
  intro: string;
  categoryId: number;
  status: number;
  viewCount?: number;
  likeCount?: number;
  publishDate?: string;
}

export const getDramaList = (params: DramaListParams) => 
  api.get<ResponseResult<{ list: any[]; total: number }>>('/drama/list', params)

export const getAdminDramaList = (params: DramaListParams) => 
  api.get<ResponseResult<{ list: any[]; total: number }>>('/drama/admin/list', params)

export const getDramaById = (id: number) => 
  api.get<ResponseResult<any>>(`/drama/detail/${id}`)

export const addDrama = (data: DramaData) => 
  api.post<ResponseResult<any>>('/drama/add', data)

export const uploadVideo = (data: FormData) => 
  api.post<ResponseResult<{ url: string }>>('/drama/upload', data, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })

export const updateDrama = (data: DramaData) => 
  api.put<ResponseResult<any>>('/drama/update', data)

export const deleteDrama = (id: number | number[] | string | string[]) => 
  Array.isArray(id) 
    ? api.delete<ResponseResult<any>>('/drama/batch', { data: id })
    : api.delete<ResponseResult<any>>(`/drama/${id}`)

// 剧目分类相关API
export interface DramaCategory {
  id: number;
  name: string;
  code: string;
  description: string;
  sortOrder: number;
  status: number;
  icon: string;
}

export const getDramaCategoryList = () => 
  api.get<ResponseResult<DramaCategory[]>>('/drama/category/list')

// 文化内容相关API
export interface CultureListParams {
  page: number;
  size: number;
  category?: string;
  keyword?: string;
  status?: number;
}

export interface CultureData {
  id?: number;
  title: string;
  content: string;
  category: string;
  cover: string;
  viewCount?: number;
  likeCount?: number;
  createTime?: string;
  updateTime?: string;
  status?: number;
}

export const getCultureList = (params: CultureListParams) => 
  api.get<ResponseResult<{ list: any[]; total: number }>>('/content/culture/admin/list', params)

export const getCultureById = (id: number) => 
  api.get<ResponseResult<any>>(`/content/culture/detail/${id}`)

export const addCulture = (data: CultureData) => 
  api.post<ResponseResult<any>>('/content/culture/add', data)

export const updateCulture = (data: CultureData) => 
  api.put<ResponseResult<any>>('/content/culture/update', data)

export const deleteCulture = (id: number | number[] | string | string[]) => 
  Array.isArray(id) 
    ? api.delete<ResponseResult<any>>('/content/culture/batch', { data: id })
    : api.delete<ResponseResult<any>>(`/content/culture/${id}`)

// 登录相关API
export const loginAdmin = (data: { username: string; password: string }) => 
  api.post<ResponseResult<any>>('/admin/login', data)