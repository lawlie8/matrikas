import './Header.css';
import { useNavigate } from "react-router";
import { PieChartOutlined,FireOutlined,PlusCircleOutlined, HomeOutlined } from '@ant-design/icons';
import { Tooltip } from 'antd'
export default function Header(params = { params }) {

    const navigate = useNavigate();

    return <div className="global-header">
        <ul style={{ listStyle: 'none', padding: '0px', marginTop: '20px' }}>
            <li>
                <a href="https://www.persistent.com">
                    <img height="25px" width="25px" src="/logo.png" alt="persistent" />
                </a>
            </li>


        </ul>
        <ul style={{ listStyle: 'none', padding: '0px', marginTop: '50px' }}>
        <li key={"/"} style={{cursor:'pointer'}} onClick={()=>{navigate("/")}}>
                <Tooltip  placement="right" title="Dashboard">
                    <HomeOutlined style={{ fontSize: '25px' }}  />
                </Tooltip>
            </li>
            <li key={"/dashboard"} style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{navigate("/dashboard")}}>
                <Tooltip  placement="right" title="Dashboard">
                    <PieChartOutlined style={{ fontSize: '25px' }}  />
                </Tooltip>
            </li>
            <li style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{navigate("/scans")}}>
                <Tooltip  placement="right" title="Show Scans">
                    <FireOutlined  style={{ fontSize: '25px' }} />
                </Tooltip>
            </li>
            <li style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{navigate("/resources")}}>
                <Tooltip  placement="right" title="Add Resources">
                    <PlusCircleOutlined  style={{ fontSize: '25px' }} />
                </Tooltip>
            </li>
        </ul>
    </div>
}