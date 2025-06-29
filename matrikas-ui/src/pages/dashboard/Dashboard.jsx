import React, { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import "./Dashboard.css";
import { GET_ALL_LIBRARIES, GET_SCAN_BY_TAG_ID, GET_TAGS_BY_LIBRARY,GET_SCAN_BY_TAG_ID_SIDE_CAR_ID } from "../../util/Constants";
import instance from "../../util/axios";
import { notification, Select } from "antd";

const Dashboard = () => {
  const [libraryList, setLibraryList] = useState([]);
  const [sideCarList, setSideCarList] = useState([]);
  const [selectedLibraryId, setSelectedLibraryId] = useState(0);
  const [selectedSideCarId, setSelectedSideCarId] = useState(0);

  const [dashboardCve, setDashboardCve] = useState([]);
  const [cardsCveCount, setCardsCveCount] = useState([]);
  const [tagList, setTagList] = useState([]);
  const [isSideCar, setIsSideCar] = useState(false);

  const [chartTagList, setChartTagList] = useState([]);

  const [aList, setAList] = useState([
    { name: "Critical", data: [] },
    { name: "High", data: [] },
    { name: "Medium", data: [] },
    { name: "Low", data: [] },]);

  const [maxLength, setMaxLength] = useState(50);

  useEffect(() => {
    for (let index = 0; index < 4; index++) {
      setMaxLength(maxLength > aList[index].data.length ? maxLength : aList[index].data.length)
    }
  }, [aList]);


  let lineChartData = {
    series: aList,
    options: {
      chart: { type: "area", height: 350 },
      dataLabels: { enabled: false },
      stroke: { curve: "smooth" },
      xaxis: { categories: chartTagList, max: chartTagList.length },
      yaxis: {
        tickAmount: 4,
        labels: { formatter: (value) => Math.round(value) },
        min: 0,
        max: Math.round((maxLength + 10) / 10) * 10,
      },
      colors: ["#E74C3C", "#E67E22", "#F1C40F", "#3498DB"],
    },
  };

  const sum = [1, 2, 3].reduce((partialSum, a) => partialSum + a, 0);
  const donutChartData = {
    series: [aList[0].data.reduce((partialSum, a) => partialSum + a, 0), aList[1].data.reduce((partialSum, a) => partialSum + a, 0), aList[2].data.reduce((partialSum, a) => partialSum + a, 0), aList[3].data.reduce((partialSum, a) => partialSum + a, 0)],
    options: {
      chart: { type: "donut" },
      labels: ["Critical", "High", "Medium", "Low"],
      colors: ["#E74C3C", "#E67E22", "#F1C40F", "#3498DB"],
      legend: { position: "bottom" },
      dataLabels: { enabled: true },
    },
  };

  const [tableData, setTableData] = useState([]);

  // console.log("alist", aList);
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

  const handleChangeSideCar = (sideCarId) => {

      setSelectedSideCarId(sideCarId)
      setChartTagList([])
      instance
        .get(GET_TAGS_BY_LIBRARY + "/" + selectedLibraryId )
        .then((response) => {
          if (response.status === 200) {
            // Sort the array by releaseDate (ascending order)
            setTagList(response?.data);
            response?.data.map((item) => {
              setChartTagList(prevArray => [item.version, ...prevArray])
            })
            handleChartForSideCar(response?.data,sideCarId)
          }
        })
        .catch(() => { });
  }

  const handleChangeImage = (value) => {
    setSelectedLibraryId(value)
    var localSideCar = false;
    libraryList.map((item) => {
      if (item.id === value) {
        localSideCar = item.sideCar;
        setIsSideCar(item.sideCar)
        setSideCarList(item.sideCarsList)
      }
    })
    console.log("localsideCar" + localSideCar);

    if (localSideCar) {
        instance
        .get(GET_TAGS_BY_LIBRARY + "/" + value)
        .then((response) => {
          if (response.status === 200) {
            // Sort the array by releaseDate (ascending order)
            setTagList(response?.data);
            response?.data.map((item) => {
              setChartTagList(prevArray => [item.version, ...prevArray])
            })
          }
        })
        .catch(() => { });
    } else {
      setChartTagList([])
      instance
        .get(GET_TAGS_BY_LIBRARY + "/" + value)
        .then((response) => {
          if (response.status === 200) {
            // Sort the array by releaseDate (ascending order)
            setTagList(response?.data);
            response?.data.map((item) => {
              setChartTagList(prevArray => [item.version, ...prevArray])
            })
            handleChart(response?.data)
          }
        })
        .catch(() => { });
    }


  };

    const handleChartForSideCar = (value,sideCarId) => {
    // console.log(value)
    value = value.map(e => e.id)
    // console.log(value)
    // console.log(value)

    let high = [];
    let medium = [];
    let low = [];
    let critical = [];

    let tagData = [];

    console.log("v", value);
    value.forEach(element => {
      console.log("element");
      instance
        .get(GET_SCAN_BY_TAG_ID_SIDE_CAR_ID + "/" + element + "/" + sideCarId)
        .then((response) => {
          let d = response?.data

          if (response.status === 200) {
            let criticalCount = 0;
            let mediumCount = 0;
            let lowCount = 0;
            let highCount = 0;

            if (d[0].critical !== "") {
              criticalCount = d[0]?.critical.split(",").length
            }
            if (d[0].low !== "") {
              lowCount = d[0]?.low.split(",").length

            }
            if (d[0].medium !== "") {
              mediumCount = d[0]?.medium.split(",").length

            }
            if (d[0].high !== "") {
              highCount = d[0]?.high.split(",").length

            }

            tagData.push({ date: d[0].tag.releaseDate, tag: d[0].tag.version, critical: criticalCount, high: highCount, medium: mediumCount, low: lowCount });
            console.log("tag", "counts", mediumCount, criticalCount, lowCount, highCount);

          }
        })
        .catch(() => { });
    });
    console.log("mydata", tagData[0])



    console.log("i am here", high, medium, low, critical)

    setTimeout(() => {


      const sortedData = tagData.sort((a, b) => new Date(b.date) - new Date(a.date));

      sortedData.forEach(e => {
        critical.push(e.critical)
        high.push(e.high)
        medium.push(e.medium)
        low.push(e.low)
      })

      critical.reverse()
      high.reverse()
      medium.reverse()
      low.reverse()

      console.log("is sorted ? .", sortedData);
      setTableData(tagData)
      setAList([
        { name: "Critical", data: critical },
        { name: "High", data: high },
        { name: "Medium", data: medium },
        { name: "Low", data: low },]);
    }, 2000);

  };

  const handleChart = (value) => {
    // console.log(value)
    value = value.map(e => e.id)
    // console.log(value)
    // console.log(value)

    let high = [];
    let medium = [];
    let low = [];
    let critical = [];

    let tagData = [];

    console.log("v", value);
    value.forEach(element => {
      console.log("element");
      instance
        .get(GET_SCAN_BY_TAG_ID + "/" + element)
        .then((response) => {
          let d = response?.data

          if (response.status === 200) {
            let criticalCount = 0;
            let mediumCount = 0;
            let lowCount = 0;
            let highCount = 0;

            if (d[0].critical !== "") {
              criticalCount = d[0]?.critical.split(",").length
            }
            if (d[0].low !== "") {
              lowCount = d[0]?.low.split(",").length

            }
            if (d[0].medium !== "") {
              mediumCount = d[0]?.medium.split(",").length

            }
            if (d[0].high !== "") {
              highCount = d[0]?.high.split(",").length

            }

            tagData.push({ date: d[0].tag.releaseDate, tag: d[0].tag.version, critical: criticalCount, high: highCount, medium: mediumCount, low: lowCount });
            console.log("tag", "counts", mediumCount, criticalCount, lowCount, highCount);

          }
        })
        .catch(() => { });
    });
    console.log("mydata", tagData[0])



    console.log("i am here", high, medium, low, critical)

    setTimeout(() => {


      const sortedData = tagData.sort((a, b) => new Date(b.date) - new Date(a.date));

      sortedData.forEach(e => {
        critical.push(e.critical)
        high.push(e.high)
        medium.push(e.medium)
        low.push(e.low)
      })

      critical.reverse()
      high.reverse()
      medium.reverse()
      low.reverse()

      console.log("is sorted ? .", sortedData);
      setTableData(tagData)
      setAList([
        { name: "Critical", data: critical },
        { name: "High", data: high },
        { name: "Medium", data: medium },
        { name: "Low", data: low },]);
    }, 2000);

  };

  useEffect(() => {
    try {
      let criticalCount = 0;
      let mediumCount = 0;
      let lowCount = 0;
      let highCount = 0;

      if (dashboardCve[0].critical !== "") {
        criticalCount = dashboardCve[0]?.critical.split(",").length
      }
      if (dashboardCve[0].low !== "") {
        lowCount = dashboardCve[0]?.low.split(",").length
      }
      if (dashboardCve[0].medium !== "") {
        mediumCount = dashboardCve[0]?.medium.split(",").length
      }
      if (dashboardCve[0].high !== "") {
        highCount = dashboardCve[0]?.high.split(",").length
      }
      setCardsCveCount([criticalCount, highCount, mediumCount, lowCount])
    } catch {

    }

  }, [dashboardCve])

  const renderDashboard = (value) => {
    console.log(value);
    if(isSideCar){
    instance
      .get(GET_SCAN_BY_TAG_ID_SIDE_CAR_ID + "/" + value + "/" + selectedSideCarId)
      .then((response) => {
        if (response.status === 200) {
          setDashboardCve(response?.data)
        }
      })
      .catch(() => { });
    }else{
    instance
      .get(GET_SCAN_BY_TAG_ID + "/" + value)
      .then((response) => {
        if (response.status === 200) {
          setDashboardCve(response?.data)
        }
      })
      .catch(() => { });
    }
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-page-main">
        <div className="dashboard-left">
          <h1 className="dashboard-title">CVE Dashboard</h1>

          <div className="dashboard-filters">
            <div className="filter-card">
              <h2 className="filter-label">{"Image Name"}</h2>

              <Select placeholder="Image Name" onChange={handleChangeImage}>
                {libraryList.map((item) => (
                  <Select.Option value={item.id}>
                    {item.imageName}
                  </Select.Option>
                ))}
              </Select>

            </div>
            <div className="filter-card" style={{ display: isSideCar === true ? "block" : "none" }}>
              <h2 className="filter-label">{"Side Car Name"}</h2>
              <Select placeholder="Side Car Name" onChange={handleChangeSideCar}>
                {sideCarList?.map((item) => (
                  <Select.Option value={item.id}>
                    {item.sideCarName}
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
              <div key={index} className="stat-card" style={{
                backgroundColor: severity === "Critical" ? "#E74C3C" :
                  severity === "High" ? "#E67E22" :
                    severity === "Medium" ? "#F1C40F" :
                      severity === "Low" ? "#3498DB" : "white"
              }}>
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
            <h2 className="table-title">CVE Counts by Library version</h2>
            <table className="dashboard-table">
              <thead>
                <tr>
                  <th>Tag</th>
                  <th>Critical</th>
                  <th>High</th>
                  <th>Medium</th>
                  <th>Low</th>
                </tr>
              </thead>
              <tbody>
                {tableData.map((row, index) => (
                  <tr key={index}>
                    <td>{row.tag}</td>
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
