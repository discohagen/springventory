import { GetLocationDto } from "../dtos/locationDtos/GetLocationDto.ts"
import { api } from "./baseApi.ts"
import { PostLocationDto } from "../dtos/locationDtos/PostLocationDto.ts"
import { PutLocationDto } from "../dtos/locationDtos/PutLocationDto.ts"

const LOCATIONS_PATH = "/locations"

export const fetchLocations = async (): Promise<GetLocationDto[]> => {
  const { data } = await api.get(LOCATIONS_PATH)
  return data
}

export const fetchLocationById = async (id: number): Promise<GetLocationDto> => {
  const { data } = await api.get(`${LOCATIONS_PATH}/${id}`)
  return data
}

export const createLocation = async (location: PostLocationDto) => {
  await api.post(LOCATIONS_PATH, location)
}

export const updateLocation = async (id: number, updates: PutLocationDto) => {
  await api.put(`${LOCATIONS_PATH}/${id}`, updates)
}

export const deleteLocation = async (id: number) => {
  await api.delete(`${LOCATIONS_PATH}/${id}`)
}