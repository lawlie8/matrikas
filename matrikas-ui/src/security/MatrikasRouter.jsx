import { BrowserRouter, Route,Routes } from "react-router";
import Scans from "../pages/scans/Scans";
import Dashboard from "../pages/dashboard/Dashboard";

export default function MatrikasRouter(path = {path}) {
    return (
        <Routes>
            <Route path="/scans" element={<Scans />}></Route>
            <Route path="/dashboard" element={<Dashboard />}></Route>
        </Routes>
 
    );
}