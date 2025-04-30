export interface PostLocationDto {
  name: string
  description: string | null
  parentLocationId: number | null | "None"
}