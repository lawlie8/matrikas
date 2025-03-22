import { BrowserRouter, Route,Routes } from "react-router";
import Scans from "../pages/scans/Scans";
import Dashboard from "../pages/dashboard/Dashboard";
import App from "../App";
import Landing from "../pages/landing/Landing";
import Resources from "../pages/resources/Resources";
import CVEComparison from "../pages/comparecves/CVEComparison";

export default function MatrikasRouter(path = {path}) {
    return (
        <Routes>
            <Route path="/scans" element={<Scans />}></Route>
            <Route path="/" element={<Dashboard />}></Route>
            <Route path="/resources" element={<Resources />}></Route>
            <Route path="/comparecves" element ={<CVEComparison />}></Route>
            <Route path="/info" element={<Landing />}></Route>
        </Routes>
 
    );
}
