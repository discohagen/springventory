import { api } from "./baseApi.ts"
import { GetItemDto } from "../dtos/itemDtos/GetItemDto.ts"
import { PostItemDto } from "../dtos/itemDtos/PostItemDto.ts"
import { PutItemDto } from "../dtos/itemDtos/PutItemDto.ts"

const ITEMS_PATH = "/items"


export const fetchItems = async (): Promise<GetItemDto[]> => {
  const { data } = await api.get(ITEMS_PATH)
  return data
}

export const fetchItemById = async (id: number): Promise<GetItemDto> => {
  const { data } = await api.get(`${ITEMS_PATH}/${id}`)
  return data
}

export const createItem = async (item: PostItemDto) => {
  await api.post(ITEMS_PATH, item)
}

export const updateItem = async (id: number, updates: PutItemDto) => {
  await api.put(`${ITEMS_PATH}/${id}`, updates)
}

export const deleteItem = async (id: number) => {
  await api.delete(`${ITEMS_PATH}/${id}`)
}

