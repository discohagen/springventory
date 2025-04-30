import { LocationSummaryDto } from "./LocationSummaryDto.ts"

export interface GetLocationDto {
  id: number
  name: string
  description: string | null
  parentLocationSummary: LocationSummaryDto | null
}