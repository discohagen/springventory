import { useLoaderData } from "react-router-dom"
import { GetItemDto } from "../dtos/itemDtos/GetItemDto.ts"
import { deleteItem } from "../api/itemApi.ts"
import { DataTable } from "../components/DataTable.tsx"
import { Link } from "@mui/material"

export const ItemsView = () => {
  const items = useLoaderData() as GetItemDto[]

  const handleDelete = async (id: number) => {
    await deleteItem(id)
    window.location.reload()
  }

  return (
    <DataTable data={items} columns={[
      { label: "Name", render: (item => <Link href={`/items/${item.id}`}>{item.name}</Link>) },
      { label: "Description", render: item => item.description },
      { label: "Quantity", render: item => item.quantity },
      { label: "Location", render: item => item.locationSummary?.name }
    ]} onDelete={handleDelete} getEditLink={id => `/items/${id}/update`} />
  )
}