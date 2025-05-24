import './Header.css';
import { useNavigate } from "react-router";
import { PieChartOutlined, FireOutlined, PlusCircleOutlined, HomeOutlined, HomeFilled, PieChartFilled, FireFilled, PlusCircleFilled, SwapOutlined, SwapRightOutlined } from '@ant-design/icons';
import { Tooltip } from 'antd'
import { useState } from 'react';

export default function Header(params = { params }) {

    const navigate = useNavigate();
    const [currentSelectedPage, setCurrentSelectedPage] = useState("/" + window.location.href.split("/").pop());
    
    return <div className="global-header">
        <ul style={{ listStyle: 'none', padding: '0px', marginTop: '20px' }}>
            <li>
                <a href="https://www.persistent.com">
                    <img height="25px" width="25px" src="/logo.png" alt="persistent" />
                </a>
            </li>
        </ul>
        <ul style={{ listStyle: 'none', padding: '0px', marginTop: '50px' }}>
        <li key={"/"} style={{cursor:'pointer'}} onClick={()=>{
            setCurrentSelectedPage("/");
            navigate("/")}}>
                <Tooltip  placement="right" title="Home">
                    {
                        currentSelectedPage !== '/' ?
                        <HomeOutlined style={{ fontSize: '25px' }} className='header-icon' />
                         :
                        <HomeFilled style={{ fontSize: '25px',color:'#fc6008' }} className='header-icon' /> 
                    }
                </Tooltip>
            </li>
            <li key={"/dashboard"} style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{
                setCurrentSelectedPage("/dashboard");
                navigate("/dashboard")}}>
                <Tooltip  placement="right" title="Dashboard">
                {
                        currentSelectedPage !== '/dashboard' ?
                        <PieChartOutlined style={{ fontSize: '25px' }} className='header-icon' />
                         :
                        <PieChartFilled style={{ fontSize: '25px',color:'#fc6008' }} className='header-icon' /> 
                    }
                </Tooltip>
            </li>
            <li style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{
                setCurrentSelectedPage("/scans");
                navigate("/scans")
                }}>
                <Tooltip  placement="right" title="Show Scans">
                {
                        currentSelectedPage !== '/scans' ?
                        <FireOutlined style={{ fontSize: '25px' }} className='header-icon' />
                         :
                        <FireFilled style={{ fontSize: '25px',color:'#fc6008' }} className='header-icon' /> 
                    }
                </Tooltip>
            </li>
            <li style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{
                setCurrentSelectedPage("/resources");
                navigate("/resources")
                }}>
                <Tooltip  placement="right" title="Add Resources">
                {
                        currentSelectedPage !== '/resources' ?
                        <PlusCircleOutlined style={{ fontSize: '25px' }} className='header-icon' />
                         :
                        <PlusCircleFilled style={{ fontSize: '25px',color:'#fc6008'}} className='header-icon' /> 
                    }
                </Tooltip>
            </li>
            <li style={{marginTop:'25px',cursor:'pointer'}} onClick={()=>{
                setCurrentSelectedPage("/comparecves");
                navigate("/comparecves")
                }}>
                <Tooltip  placement="right" title="CVEComparison">
                {
                        currentSelectedPage !== '/comparecves' ?
                        <SwapOutlined style={{ fontSize: '25px' }} className='header-icon' />
                         :
                        <SwapOutlined style={{ fontSize: '25px',color:'#fc6008'}} className='header-icon' /> 
                    }
                </Tooltip>
            </li>
        </ul>
    </div>
}
