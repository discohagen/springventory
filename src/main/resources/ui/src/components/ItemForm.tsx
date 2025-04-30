import { useLoaderData, useNavigate } from "react-router-dom"
import { GetItemDto } from "../dtos/itemDtos/GetItemDto.ts"
import { useEffect, useMemo, useState } from "react"
import { PostItemDto } from "../dtos/itemDtos/PostItemDto.ts"
import { PutItemDto } from "../dtos/itemDtos/PutItemDto.ts"
import { createItem, updateItem } from "../api/itemApi.ts"
import { FormMode } from "../types.ts"
import * as Yup from "yup"
import { useFormik } from "formik"
import {
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormHelperText,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography
} from "@mui/material"
import { GetLocationDto } from "../dtos/locationDtos/GetLocationDto.ts"

interface ItemFormProps {
  mode: FormMode
  isEditable?: boolean
}

const validationSchema = Yup.object({
  name: Yup.string().required("Name is required"),
  quantity: Yup.number().required("Quantity is required").positive("Quantity must be positive")
})

export const ItemForm = ({ mode, isEditable }: ItemFormProps) => {
  const [itemData, locations] = useLoaderData() as [(GetItemDto | null), GetLocationDto[]]

  const [stayOnPage, setStayOnPage] = useState(true)
  const [isEditableFlag, setIsEditableFlag] = useState(isEditable || false)

  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  const navigate = useNavigate()

  const initialValues: PostItemDto | PutItemDto = useMemo(() => ({
    name: itemData?.name || "",
    description: itemData?.description || "",
    quantity: itemData?.quantity || 1,
    locationId: itemData?.locationSummary?.id ?? "None"
  }), [itemData])

  const handleSubmit = async (values: PostItemDto | PutItemDto) => {
    const submitValues = {
      ...values,
      locationId: values.locationId === "None" ? null : values.locationId
    }
    setIsLoading(true)
    setError(null)
    try {
      if (mode === FormMode.CREATE) {
        await createItem(submitValues as PostItemDto)
      } else if (isEditableFlag && itemData) {
        await updateItem((itemData as GetItemDto).id, submitValues as PutItemDto)
      }
      setSuccess(true)
      if (!stayOnPage) {
        navigate("/items")
      }
    } catch (err) {
      setError(`Failed to ${mode === FormMode.CREATE ? "create" : "update"} item: ${(err as Error).message}`)
    } finally {
      setIsLoading(false)
    }
  }

  const formik = useFormik({
    initialValues,
    validationSchema,
    onSubmit: handleSubmit
  })

  const resetForm = formik.resetForm

  useEffect(() => {
    resetForm({ values: initialValues })
  }, [mode, initialValues, resetForm])

  return (
    <form onSubmit={formik.handleSubmit}>
      <Stack>

        <FormControl style={{ minHeight: "6em" }}>
          <TextField
            id={"name"}
            name={"name"}
            label={"Name"}
            value={formik.values.name}
            onChange={formik.handleChange}
            error={formik.touched.name && Boolean(formik.errors.name)}
            disabled={mode === FormMode.VIEW && !isEditableFlag}
          />
          <FormHelperText error={formik.touched.name && Boolean(formik.errors.name)}>
            {formik.touched.name && formik.errors.name}
          </FormHelperText>
        </FormControl>

        <FormControl style={{ minHeight: "6em" }}>
          <TextField
            id={"description"}
            name={"description"}
            label={"Description"}
            value={formik.values.description}
            onChange={formik.handleChange}
            error={formik.touched.description && Boolean(formik.errors.description)}
            disabled={mode === FormMode.VIEW && !isEditableFlag}
          />
          <FormHelperText error={formik.touched.description && Boolean(formik.errors.description)}>
            {formik.touched.description && formik.errors.description}
          </FormHelperText>
        </FormControl>

        <FormControl style={{ minHeight: "6em" }}>
          <TextField
            id={"quantity"}
            name={"quantity"}
            label={"Quantity"}
            type={"number"}
            value={formik.values.quantity}
            onChange={formik.handleChange}
            error={formik.touched.quantity && Boolean(formik.errors.quantity)}
            disabled={mode === FormMode.VIEW && !isEditableFlag}
          />
          <FormHelperText error={formik.touched.quantity && Boolean(formik.errors.quantity)}>
            {formik.touched.quantity && formik.errors.quantity}
          </FormHelperText>
        </FormControl>

        {locations && (
          <FormControl style={{ minHeight: "6em" }}>
            <InputLabel id={"location"}>Location</InputLabel>
            <Select
              labelId={"location"}
              id={"locationSelect"}
              name={"locationId"}
              label={"Location"}
              value={formik.values.locationId}
              onChange={formik.handleChange}
              disabled={mode === FormMode.VIEW && !isEditableFlag}
            >
              <MenuItem value={"None"}>
                None
              </MenuItem>
              {locations.map(location => (
                <MenuItem key={location.id} value={location.id}>
                  {location.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        )}

        <Stack direction={"row"} sx={{
          justifyContent: "space-between"
        }}>
          {mode === FormMode.VIEW && (
            <Button onClick={() => setIsEditableFlag(!isEditableFlag)}>
              {isEditableFlag ? "View" : "Edit"}
            </Button>
          )}
          <FormControlLabel
            control={<Checkbox checked={stayOnPage} onChange={() => setStayOnPage(!stayOnPage)} />}
            label={`Stay on page after ${mode === FormMode.CREATE ? "creating" : "updating"}`} />
          <Button type={"submit"} variant={"contained"} color={"primary"}
                  disabled={isLoading || (mode === FormMode.VIEW && !isEditableFlag)}>
            {mode === FormMode.CREATE ? "Create" : "Update"}
          </Button>
        </Stack>

        {error && <Typography color={"error"}>{error}</Typography>}
        {success && <Typography
          color={"success"}>item {mode === FormMode.CREATE ? "created" : "updated"} successfully</Typography>}
      </Stack>
    </form>
  )
}