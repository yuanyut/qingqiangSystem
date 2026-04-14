import request from './request'

export interface Recommendation {
  id: number
  title: string
  description: string
  coverImage: string
  categoryName: string
  watchCount: number
  likeCount: number
}

export interface HomeRecommendations {
  hotDramas: Recommendation[]
  personalized: Recommendation[]
}

export const getHomeRecommendations = () => {
  return request<HomeRecommendations>({
    url: '/recommend/home',
    method: 'get'
  })
}
