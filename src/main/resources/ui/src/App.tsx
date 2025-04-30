import {Outlet} from "react-router-dom"
import {Navbar} from "./components/Navbar.tsx"
import {Box} from "@mui/material"


function App() {

    return (
        <>
            <Navbar/>
            <Box sx={{mx: 5, mt: 5}}>
                <Outlet/>
            </Box>
        </>
    )
}

export default App