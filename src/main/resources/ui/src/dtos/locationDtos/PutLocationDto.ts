export interface PutLocationDto {
  name: string
  description: string | null
  parentLocationId: number | null | "None"
}