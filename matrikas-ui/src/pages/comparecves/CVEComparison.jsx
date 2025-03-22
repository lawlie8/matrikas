import React, { useState } from 'react';
import { Card, Typography, Select, Button, Table, Row, Col, Statistic } from 'antd';
import Chart from 'react-apexcharts';
import './CVEComparison.css';

const { Title } = Typography;
const { Option } = Select;

export default function CVEComparison() {
  const images = [
    { name: 'aws-ebs-csi', versions: ['1.0', '1.1', '1.2', '1.3'] },
    { name: 'calico', versions: ['3.8', '3.9', '3.10', '3.11'] },
    { name: 'aws-vpc-cni', versions: ['2.6', '2.7', '2.8', '2.9'] },
  ];

  const [selectedImage, setSelectedImage] = useState(null);
  const [version1, setVersion1] = useState(null);
  const [version2, setVersion2] = useState(null);
  const [comparisonResult, setComparisonResult] = useState([]);

  const handleCompare = () => {
    if (!selectedImage || !version1 || !version2) return;

    const mockCVEData = [
      { library: "aws-ebs-csi", total: 10, cve: 'CVE-2023-0001', severity: 'Critical', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "abbc1 ", total: 20, cve: 'CVE-2023-0002', severity: 'Medium', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 12, cve: 'CVE-2023-0003', severity: 'Low', status: 'Present' },
      { library: "aws-ebs-csi", total: 10, cve: 'CVE-2023-0001', severity: 'High', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 20, cve: 'CVE-2023-0002', severity: 'Medium', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 12, cve: 'CVE-2023-0003', severity: 'Low', status: 'Present' },
      { library: "aws-ebs-csi", total: 10, cve: 'CVE-2023-0001', severity: 'High', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 20, cve: 'CVE-2023-0002', severity: 'Medium', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 12, cve: 'CVE-2023-0003', severity: 'Critical', status: 'Present' },
      { library: "aws-ebs-csi", total: 10, cve: 'CVE-2023-0001', severity: 'High', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 20, cve: 'CVE-2023-0002', severity: 'Medium', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { library: "aws-ebs-csi", total: 12, cve: 'CVE-2023-0003', severity: 'Low', status: 'Present' },
    ];
    setComparisonResult(mockCVEData);
  };

  const columns = [
    { title: 'CVE ID', dataIndex: 'cve', key: 'cve' },
    { title: 'Severity', dataIndex: 'severity', key: 'severity',
    filters: [
      { text: 'Critical', value: 'Critical' },
      { text: 'High', value: 'High' },
      { text: 'Medium', value: 'Medium' },
      { text: 'Low', value: 'Low' },
    ],
    onFilter: (value, record) => record.severity.indexOf(value) === 0, },
    { title: 'Status', 
      dataIndex: 'status', 
      key: 'status', 
      filters: [
        { text: 'Fixed', value: 'Fixed' },
        { text: 'Present', value: 'Present' },
      ],
      onFilter: (value, record) => record.status.indexOf(value) === 0,
      render: (status) => {
      const statusStyle = status === 'Fixed' 
        ? { backgroundColor: 'green', color: 'white', padding: '5px 15px', borderRadius: '25px' }
        : {backgroundColor: 'red', color: 'white', padding: '5px 15px', borderRadius: '25px'};
      
      return (
        <span style={statusStyle}>{status}</span>
      );}},
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
    colors: ['#DF2026', "#FF8C00",'#FEB019' , '#FFFF9F'],
    legend: {
      position: 'bottom',
    },
    dataLabels: { enabled: true },
  };

  return (
    <div className="cve-comparison-page">
      <Card className="cve-comparison-card" >
        <Title level={1} style={{ textAlign: 'center' }}>CVE Comparison</Title>
        <Select placeholder="Select Image" style={{ width: '50%' }} onChange={setSelectedImage}>
          {images.map((image) => (
            <Option key={image.name} value={image.name}>{image.name}</Option>
          ))}
        </Select>
        <br /> <Select placeholder="Select First Version" style={{ width: '25%', marginRight: '0.25%' }} onChange={setVersion1} disabled={!selectedImage}>
          {selectedImage && images.find(img => img.name === selectedImage).versions.map(version => (
            <Option key={version} value={version}>{version}</Option>
          ))}
        </Select>
        <Select placeholder="Select Second Version" style={{ width: '25%' }} onChange={setVersion2} disabled={!selectedImage}>
          {selectedImage && images.find(img => img.name === selectedImage).versions.map(version => (
            <Option key={version} value={version}>{version}</Option>
          ))}
        </Select>
      <br /> <Button type="primary" style={{ marginTop: '10px', width: '50%' }} onClick={handleCompare} disabled={!version1 || !version2}>Compare</Button>
        <Row gutter={16} style={{ marginTop: '20px' }}>
          <Col span={14}>
            {comparisonResult.length > 0 && <Table columns={columns} dataSource={comparisonResult} pagination={true} />}
          </Col>
          <Col span={10}>
            {/* <Row gutter={16}> */}
              <Col >
              
                {comparisonResult.length > 0 && <Card title="Total CVEs fixed"> 
                <Row gutter={16}>
                <Col span={6}>
          <Statistic title="Critical" value="10" />
        </Col>
        <Col span={6}>
          <Statistic title="High" value="10" />
        </Col>
        <Col span={6}>
          <Statistic title="Medium" value="10" />
        </Col>
        <Col span={6}>
          <Statistic title="Low" value="5" /> 
        </Col>
      </Row>
                </Card>}
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
