import { StrictMode } from "react"
import { createRoot } from "react-dom/client"
import { RouterProvider } from "react-router-dom"
import { router } from "./routes/routes.tsx"
import { createTheme, CssBaseline, ThemeProvider } from "@mui/material"

const darkTheme = createTheme({
  palette: {
    mode: "dark",
    background: {
      default: "#2c2c2c",
      paper: "#353535"
    },
    text: {
      primary: "#fefefe"
    }
  }
})

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <RouterProvider router={router} />
    </ThemeProvider>
  </StrictMode>
)
