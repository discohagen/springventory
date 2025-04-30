export interface PutItemDto {
  name: string
  description: string | null
  quantity: number
  locationId: number | null | "None"
}