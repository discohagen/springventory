import { LocationSummaryDto } from "../locationDtos/LocationSummaryDto.ts"

export interface GetItemDto {
  id: number
  name: string
  description: string | null
  quantity: number
  locationSummary: LocationSummaryDto | null
}