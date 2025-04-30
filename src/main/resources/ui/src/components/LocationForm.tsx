import { FormMode } from "../types.ts"
import { useEffect, useMemo, useState } from "react"
import { PostLocationDto } from "../dtos/locationDtos/PostLocationDto.ts"
import { useLoaderData, useNavigate } from "react-router-dom"
import { GetLocationDto } from "../dtos/locationDtos/GetLocationDto.ts"
import { PutLocationDto } from "../dtos/locationDtos/PutLocationDto.ts"
import { createLocation, updateLocation } from "../api/locationApi.ts"
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

interface LocationFormProps {
  mode: FormMode
  isEditable?: boolean
}

const validationSchema = Yup.object({
  name: Yup.string().required("Name is required")
})

export const LocationForm = ({ mode, isEditable }: LocationFormProps) => {
  const [locationData, locations] = useLoaderData() as [(GetLocationDto | null), GetLocationDto[]]

  const [stayOnPage, setStayOnPage] = useState(true)
  const [isEditableFlag, setIsEditableFlag] = useState(isEditable || false)

  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  const navigate = useNavigate()

  const initialValues: PostLocationDto | PutLocationDto = useMemo(() => ({
    name: locationData?.name || "",
    description: locationData?.description || "",
    parentLocationId: locationData?.parentLocationSummary?.id ?? "None"
  }), [locationData])

  const handleSubmit = async (values: PostLocationDto | PutLocationDto) => {
    const submitValues = {
      ...values,
      parentLocationId: values.parentLocationId === "None" ? null : values.parentLocationId
    }
    setIsLoading(true)
    setError(null)
    try {
      if (mode === FormMode.CREATE) {
        await createLocation(submitValues as PostLocationDto)
      } else if (isEditableFlag && locationData) {
        await updateLocation((locationData as GetLocationDto).id, submitValues as PutLocationDto)
      }
      setSuccess(true)
      if (!stayOnPage) {
        navigate("/locations")
      }
    } catch (err) {
      setError(`Failed to ${mode === FormMode.CREATE ? "create" : "update"} location: ${(err as Error).message}`)
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

        <FormControl style={{ minHeight: "5em" }}>
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

        <FormControl style={{ minHeight: "5em" }}>
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

        {locations && (
          <FormControl style={{ minHeight: "5em" }}>
            <InputLabel id={"parentLocation"}>Parent Location</InputLabel>
            <Select
              labelId={"parentLocation"}
              id={"parentLocationSelect"}
              name={"parentLocationId"}
              label={"Parent Location"}
              value={formik.values.parentLocationId}
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
          <FormControlLabel control={<Checkbox checked={stayOnPage} onChange={() => setStayOnPage(!stayOnPage)} />}
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