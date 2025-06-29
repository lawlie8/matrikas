import React, { useEffect, useState } from 'react';
import { Card, Typography, Select, Button, Table, Row, Col, Statistic } from 'antd';
import Chart from 'react-apexcharts';
import './CVEComparison.css';
import { COMPARE_CVE, GET_ALL_LIBRARIES, GET_TAGS_BY_LIBRARY } from '../../util/Constants';
import instance from '../../util/axios';
const { Title } = Typography;
const { Option } = Select;

export default function CVEComparison() {


  const [libraryList, setLibraryList] = useState([]);
  const [selectedImage, setSelectedImage] = useState([]);
  const [selectedSideCarId, setSelectedSideCarId] = useState(0);

  const [isSideCar, setIsSideCar] = useState(false);
  const [sideCarList, setSideCarList] = useState([]);

  const [version1, setVersion1] = useState(null);
  const [version2, setVersion2] = useState(null);
  const [comparisonResult, setComparisonResult] = useState([]);
  const [tagList, setTagList] = useState([]);

  const handleCompare = () => {
    if (!selectedImage || !version1 || !version2) return;

    instance
      .post(COMPARE_CVE, {
        imageName: selectedImage,
        oldTag: version1,
        newTag: version2,
        sideCarId:selectedSideCarId
      })
      .then((response) => {
        if (response.status === 200) {
          setComparisonResult(response?.data);
          console.log(response?.data);
        }
      })
      .catch(() => {

      });


  };

  const columns = [
    { title: 'CVE ID', dataIndex: 'cveID', key: 'cveID' },
    {
      title: 'Severity', dataIndex: 'severity', key: 'severity',
      filters: [
        { text: 'Critical', value: 'Critical' },
        { text: 'High', value: 'High' },
        { text: 'Medium', value: 'Medium' },
        { text: 'Low', value: 'Low' },
      ],
      onFilter: (value, record) => record.severity.indexOf(value) === 0,
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      filters: [
        { text: 'Fixed', value: 'Fixed' },
        { text: 'Not-Fixed', value: 'Not-Fixed' },
      ],
      onFilter: (value, record) => record.status.indexOf(value) === 0,
      render: (status) => {
        const statusStyle = status === 'Fixed'
          ? { backgroundColor: 'green', color: 'white', padding: '5px 15px', borderRadius: '25px' }
          : { backgroundColor: 'red', color: 'white', padding: '5px 15px', borderRadius: '25px' };

        return (
          <span style={statusStyle}>{status}</span>
        );
      }
    },
  ];

  // Prepare data for the line chart based on mockCVEData
  const severityCounts = comparisonResult.reduce((acc, item) => {
    acc[item.severity] = (acc[item.severity] || 0) + 1;
    return acc;
  }, {});

  const donutChartData = {
    series: [severityCounts['Critical'] || 0, severityCounts['High'] || 0, severityCounts['Medium'] || 0, severityCounts['Low'] || 0],
    labels: ['Critical', 'High', 'Medium', 'Low'],
  };



  const donutChartOptions = {
    chart: {
      id: 'donut-chart',
      type: 'pie',
      height: '300px',
    },
    labels: donutChartData.labels,
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: '100%',
          },
        },
      },
    ],
    colors: ['#DF2026', "#FF8C00", '#FEB019', '#FFFF9F'],
    legend: {
      position: 'bottom',
    },
    dataLabels: { enabled: true },
  };

  useEffect(() => {
    instance
      .get(GET_ALL_LIBRARIES)
      .then((response) => {
        if (response.status === 200) {

          setLibraryList(response?.data);
          console.log(response?.data);
        }
      })
      .catch(() => {

      });
  }, []);

  const handleChangeSideCar = (value) => {
    setSelectedSideCarId(0)
    setSelectedSideCarId(value)
    }

  const handleChange = (value) => {
    setSideCarList([])
        libraryList.map((item) => {
      if (item.id === value) {
        setIsSideCar(item.sideCar)
        setSideCarList(item.sideCarsList)
        setSelectedImage(item.imageName)
      }
    })
    instance
      .get(GET_TAGS_BY_LIBRARY + "/" + value)
      .then((response) => {
        if (response.status === 200) {
          setTagList(response?.data);
        }
      })
      .catch(() => {

      });
  };

  useEffect(() => {
    setSelectedImage(0)
    instance
      .get(GET_ALL_LIBRARIES)
      .then((response) => {
        if (response.status === 200) {          
        setSelectedImage(response?.data);
        }
      })
      .catch(() => {

      });
  }, []);


  return (
    <div className="cve-comparison-page">
      <Card className="cve-comparison-card" >
        <Title level={1} style={{ textAlign: 'center' }}>CVE Comparison</Title>
        <Select placeholder="Image Name" onChange={handleChange}>
          {
            libraryList.map((item, index) => (
              <Select.Option value={item.id}>{item.imageName}</Select.Option>
            ))
          }
        </Select>
        <Select placeholder="Side Car Name" style={{ display: isSideCar === true ? "block" : "none" }} onChange={handleChangeSideCar}>
          {
            sideCarList.map((item, index) => (
              <Select.Option value={item.id}>{item.sideCarName}</Select.Option>
            ))
          }
        </Select>
        <br /> <Select placeholder="Old Image Version" onChange={setVersion1}>
          {
            tagList.map((item, index) => (
              <Select.Option value={item.version}>{item.version}</Select.Option>
            ))
          }
        </Select>
        <Select placeholder="New Image Version" onChange={setVersion2}>
          {
            tagList.map((item, index) => (
              <Select.Option value={item.version}>{item.version}</Select.Option>
            ))
          }
        </Select>
        <br /> <Button type="primary" style={{ marginTop: '10px', width: '50%' }} onClick={handleCompare} disabled={!version1 || !version2}>Compare</Button>
        <Row gutter={16} style={{ marginTop: '20px' }}>
          <Col span={14}>
            {comparisonResult.length > 0 && <Table columns={columns} dataSource={comparisonResult} pagination={true} />}
          </Col>
          <Col span={10}>
            {/* <Row gutter={16}> */}
            <Col >


            </Col>
            <Col>
              {comparisonResult.length > 0 && (
                <Card title="CVE Severity Data">
                  <Chart
                    options={donutChartOptions}
                    series={donutChartData.series}
                    type="pie"
                    height="300px"
                  />
                </Card>
              )}
            </Col>
            {/* </Row> */}
          </Col>
        </Row>
      </Card>
    </div>
  );
}
