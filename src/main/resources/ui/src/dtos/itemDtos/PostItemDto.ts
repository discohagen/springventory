export interface PostItemDto {
  name: string
  description: string | null
  quantity: number
  locationId: number | null | "None"
}