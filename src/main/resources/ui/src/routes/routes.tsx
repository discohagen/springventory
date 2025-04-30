import { createBrowserRouter } from "react-router-dom"
import { fetchItemById, fetchItems } from "../api/itemApi.ts"
import { ItemsView } from "../views/ItemsView.tsx"
import App from "../App.tsx"
import { ItemForm } from "../components/ItemForm.tsx"
import { FormMode } from "../types.ts"
import { LocationsView } from "../views/LocationsView.tsx"
import { LocationForm } from "../components/LocationForm.tsx"
import { fetchLocationById, fetchLocations } from "../api/locationApi.ts"

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "items",
        element: <ItemsView />,
        loader: fetchItems
      },
      {
        path: "items/:id",
        element: <ItemForm mode={FormMode.VIEW} />,
        loader: async ({ params }) => Promise.all([fetchItemById(Number(params.id)), fetchLocations()])
      },
      {
        path: "items/:id/update",
        element: <ItemForm mode={FormMode.VIEW} isEditable={true} />,
        loader: async ({ params }) => Promise.all([fetchItemById(Number(params.id)), fetchLocations()])
      },
      {
        path: "items/create",
        element: <ItemForm mode={FormMode.CREATE} />,
        loader: async () => Promise.all([null, fetchLocations()])
      },
      {
        path: "locations",
        element: <LocationsView />,
        loader: fetchLocations
      },
      {
        path: "locations/:id",
        element: <LocationForm mode={FormMode.VIEW} />,
        loader: async ({ params }) => Promise.all([fetchLocationById(Number(params.id)), fetchLocations()])
      },
      {
        path: "locations/:id/update",
        element: <LocationForm mode={FormMode.VIEW} isEditable={true} />,
        loader: async ({ params }) => Promise.all([fetchLocationById(Number(params.id)), fetchLocations()])
      },
      {
        path: "locations/create",
        element: <LocationForm mode={FormMode.CREATE} />,
        loader: async () => Promise.all([null, fetchLocations()])
      }
    ]
  }

])