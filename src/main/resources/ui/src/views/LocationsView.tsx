import { useLoaderData } from "react-router-dom"
import { GetLocationDto } from "../dtos/locationDtos/GetLocationDto.ts"
import { deleteLocation } from "../api/locationApi.ts"
import { DataTable } from "../components/DataTable.tsx"
import { Link } from "@mui/material"

export const LocationsView = () => {
  const locations = useLoaderData() as GetLocationDto[]

  const handleDelete = async (id: number) => {
    await deleteLocation(id)
    window.location.reload()
  }

  return (
    <DataTable data={locations} columns={[
      { label: "Name", render: (location => <Link href={`/locations/${location.id}`}>{location.name}</Link>) },
      { label: "Description", render: location => location.description },
      { label: "Parent Location", render: location => location.parentLocationSummary?.name }
    ]} onDelete={handleDelete} getEditLink={id => `/locations/${id}/update`} />
  )
}