import { useNavigate } from "react-router-dom"
import { IconButton, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"
import { Delete, Edit } from "@mui/icons-material"
import { ReactNode } from "react"

interface DataTableProps<T> {
  data: T[]
  columns: { label: string, render: (data: T) => ReactNode }[]
  onDelete: (id: number) => void
  getEditLink: (id: number) => string
}

export const DataTable = <T extends { id: number }>({
                                                      data,
                                                      columns,
                                                      onDelete,
                                                      getEditLink
                                                    }: DataTableProps<T>) => {
  const navigate = useNavigate()

  return (
    <TableContainer>
      <Table>
        <TableHead>
          <TableRow>
            {columns.map((column, index) => (
              <TableCell key={index}>
                {column.label}
              </TableCell>
            ))}
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map(datum => (
            <TableRow key={datum.id}>
              {columns.map((column, index) => (
                <TableCell key={index}>
                  {column.render(datum)}
                </TableCell>
              ))}
              <TableCell>
                <IconButton onClick={() => navigate(getEditLink(datum.id))} color={"primary"}>
                  <Edit />
                </IconButton>
                <IconButton onClick={() => onDelete(datum.id)} color={"error"}>
                  <Delete />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}