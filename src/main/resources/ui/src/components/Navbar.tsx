import { Link, useLocation } from "react-router-dom"
import { AppBar, Button, Divider, IconButton, Stack, Toolbar } from "@mui/material"
import { Add } from "@mui/icons-material"

export const Navbar = () => {
  const location = useLocation()

  const isActive = (path: string) => location.pathname.startsWith(path)

  return (
    <AppBar position={"static"}>
      <Toolbar>
        <Stack direction={"row"} divider={<Divider orientation={"vertical"} flexItem />}
               spacing={2}>
          {/*<Button component={Link} to={"/"} color={isActive("/") ? "secondary" : "primary"}>Home</Button>*/}
          <Stack direction={"row"}>
            <Button component={Link} to={"/items"} color={isActive("/items") ? "secondary" : "primary"}>Items</Button>
            <IconButton component={Link} to={"/items/create"}
                        color={isActive("/items/create") ? "secondary" : "primary"}><Add /></IconButton>
          </Stack>
          <Stack direction={"row"}>
            <Button component={Link} to={"/locations"}
                    color={isActive("/locations") ? "secondary" : "primary"}>Locations</Button>
            <IconButton component={Link} to={"/locations/create"}
                        color={isActive("/locations/create") ? "secondary" : "primary"}><Add /></IconButton>
          </Stack>
        </Stack>
      </Toolbar>
    </AppBar>
  )
}