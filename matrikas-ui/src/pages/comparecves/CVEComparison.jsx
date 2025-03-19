import React, { useState } from 'react';
import { Card, Typography, Select, Button, Table } from 'antd';
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
      { cve: 'CVE-2023-0001', severity: 'High', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { cve: 'CVE-2023-0002', severity: 'Medium', status: version1 !== version2 ? 'Fixed' : 'Present' },
      { cve: 'CVE-2023-0003', severity: 'Low', status: 'Present' },
    ];
    setComparisonResult(mockCVEData);
  };

  const columns = [
    { title: 'CVE ID', dataIndex: 'cve', key: 'cve' },
    { title: 'Severity', dataIndex: 'severity', key: 'severity' },
    { title: 'Status', dataIndex: 'status', key: 'status' },
  ];

  return (
    <div className="cve-comparison-page">
      <Card className="cve-comparison-card">
        <Title level={1} style={{ textAlign: 'center' }}>CVE Comparison</Title>
        <Select placeholder="Select Image" style={{ width: '100%', marginBottom: '10px' }} onChange={setSelectedImage}>
          {images.map((image) => (
            <Option key={image.name} value={image.name}>{image.name}</Option>
          ))}
        </Select>
        <Select placeholder="Select First Version" style={{ width: '48%', marginRight: '4%' }} onChange={setVersion1} disabled={!selectedImage}>
          {selectedImage && images.find(img => img.name === selectedImage).versions.map(version => (
            <Option key={version} value={version}>{version}</Option>
          ))}
        </Select>
        <Select placeholder="Select Second Version" style={{ width: '48%' }} onChange={setVersion2} disabled={!selectedImage}>
          {selectedImage && images.find(img => img.name === selectedImage).versions.map(version => (
            <Option key={version} value={version}>{version}</Option>
          ))}
        </Select>
        <Button type="primary" style={{ marginTop: '10px', width: '100%' }} onClick={handleCompare} disabled={!version1 || !version2}>Compare</Button>
        {comparisonResult.length > 0 && <Table style={{ marginTop: '20px' }} columns={columns} dataSource={comparisonResult} pagination={false} />}
      </Card>
    </div>
  );
}
