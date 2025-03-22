import React, { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import "./Dashboard.css";
import { GET_ALL_LIBRARIES, GET_SCAN_BY_TAG_ID, GET_TAGS_BY_LIBRARY } from "../../util/Constants";
import instance from "../../util/axios";
import { notification, Select } from "antd";

const Dashboard = () => {
  const [libraryList, setLibraryList] = useState([]);
  const [dashboardCve, setDashboardCve] = useState([]);
  const [cardsCveCount, setCardsCveCount] = useState([]);


  const [tagList, setTagList] = useState([]);
  const lineChartData = {
    series: [
      { name: "Critical", data: [800, 950, 1000, 1100, 1200] },
      { name: "High", data: [400, 450, 500, 550, 600] },
      { name: "Medium", data: [200, 300, 350, 400, 450] },
      { name: "Low", data: [100, 150, 200, 250, 300] },
    ],
    options: {
      chart: { type: "area", height: 350 },
      dataLabels: { enabled: false },
      stroke: { curve: "smooth" },
      xaxis: { categories: ["27-07", "28-07", "29-07", "30-07", "31-07"] },
      yaxis: {
        tickAmount: 4,
        labels: { formatter: (value) => Math.round(value) },
        min: 0,
        max: 2000,
      },
      colors: ["#E74C3C", "#E67E22", "#F1C40F", "#3498DB"],
    },
  };

  const donutChartData = {
    series: [485, 878, 1002, 100],
    options: {
      chart: { type: "donut" },
      labels: ["Critical", "High", "Medium", "Low"],
      colors: ["#E74C3C", "#E67E22", "#F1C40F", "#3498DB"],
      legend: { position: "bottom" },
      dataLabels: { enabled: true },
    },
  };

  const tableData = [
    { date: "27-07", critical: 800, high: 400, medium: 200, low: 100 },
    { date: "28-07", critical: 950, high: 450, medium: 300, low: 150 },
    { date: "29-07", critical: 1000, high: 500, medium: 350, low: 200 },
    { date: "30-07", critical: 1100, high: 550, medium: 400, low: 250 },
    { date: "31-07", critical: 1200, high: 600, medium: 450, low: 300 },
  ];

  useEffect(() => {
    instance
      .get(GET_ALL_LIBRARIES)
      .then((response) => {
        if (response.status === 200) {
          notification.success({
            message: "Sucess",
            duration: 1,
            description: "Fetched All Libraries",
            style: { width: "200px" },
          });
          setLibraryList(response?.data);
          console.log(response?.data);
        }
      })
      .catch(() => {
        notification.error({
          message: "Error",
          duration: 1,
          description: "Failed To Fetch Libraries",
          style: { width: "250px" },
        });
      });
  }, []);

  const handleChange = (value) => {
    instance
      .get(GET_TAGS_BY_LIBRARY + "/" + value)
      .then((response) => {
        if (response.status === 200) {
          setTagList(response?.data);
        }
      })
      .catch(() => {});
  };

  useEffect(()=>{
    try{
      let criticalCount = 0;
      let mediumCount = 0;
      let lowCount = 0;
      let highCount = 0;

      if(dashboardCve[0].critical !== ""){
        criticalCount = dashboardCve[0]?.critical.split(",").length
      }
      if(dashboardCve[0].low !== ""){
        lowCount = dashboardCve[0]?.low.split(",").length
      }
      if(dashboardCve[0].medium !== ""){
        mediumCount = dashboardCve[0]?.medium.split(",").length
      }
      if(dashboardCve[0].high !== ""){
        highCount = dashboardCve[0]?.high.split(",").length
      }
      setCardsCveCount([criticalCount,highCount,mediumCount,lowCount])
    }catch{

    }

      
    

  },[dashboardCve])

  const renderDashboard = (value) => {
    console.log(value);
    
    instance
      .get(GET_SCAN_BY_TAG_ID + "/" + value)
      .then((response) => {
        if (response.status === 200) {
          setDashboardCve(response?.data)
        }
      })
      .catch(() => {});
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-page-main">
        <div className="dashboard-left">
          <h1 className="dashboard-title">CVE Dashboard</h1>

          <div className="dashboard-filters">
            <div className="filter-card">
              <h2 className="filter-label">{"Image Name"}</h2>

              <Select placeholder="Image Name" onChange={handleChange}>
                {libraryList.map((item) => (
                  <Select.Option value={item.id}>
                    {item.imageName}
                  </Select.Option>
                ))}
              </Select>
            </div>
            <div className="filter-card">
              <h2 className="filter-label">{"Version"}</h2>

              <Select placeholder="Image Version" onChange={renderDashboard}>
                {tagList.map((item) => (
                  <Select.Option value={item.id}>{item.version}</Select.Option>
                ))}
              </Select>
            </div>
          </div>
        </div>

        <div className="dashboard-center">
          <div className="dashboard-stats">
            {["Critical", "High", "Medium", "Low"].map((severity, index) => (
              <div key={index} className="stat-card">
                <h2 className="stat-title">{severity}</h2>
                <p className="stat-value">
                  {cardsCveCount[index]}
                </p>
              </div>
            ))}
          </div>

          <div className="line-chart-container">
            <Chart
              options={lineChartData.options}
              series={lineChartData.series}
              type="area"
              height={400}
            />
          </div>
        </div>

        <div className="dashboard-right">
          <div className="donut-chart-container">
            <h2 className="chart-title">CVE Severity Distribution</h2>
            <Chart
              options={donutChartData.options}
              series={donutChartData.series}
              type="donut"
              height={300}
            />
          </div>

          <div className="table-container">
            <h2 className="table-title">CVE Counts by Scan Date</h2>
            <table className="dashboard-table">
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Critical</th>
                  <th>High</th>
                  <th>Medium</th>
                  <th>Low</th>
                </tr>
              </thead>
              <tbody>
                {tableData.map((row, index) => (
                  <tr key={index}>
                    <td>{row.date}</td>
                    <td>{row.critical}</td>
                    <td>{row.high}</td>
                    <td>{row.medium}</td>
                    <td>{row.low}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
