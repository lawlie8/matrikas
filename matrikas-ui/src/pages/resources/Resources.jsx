import React, { useState } from 'react';
import { Table, Input, Button } from 'antd';
import 'antd/dist/reset.css';
import './Resources.css';

export default function Resources() {
  const initialData = [
    { key: '1', name: 'aws-ebs-csi', source: 'https://github.com/kubernetes-sigs/aws-ebs-csi-driver/releases', version: '' },
    { key: '2', name: 'calico', source: 'https://github.com/projectcalico/calico/releases/', version: '' },
    { key: '3', name: 'aws-vpc-cni', source: 'https://github.com/aws/amazon-vpc-cni-k8s/releases', version: '' },
  ];

  const [data, setData] = useState(initialData);
  const [formData, setFormData] = useState({ name: '', source: '', version: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAdd = () => {
    if (!formData.name || !formData.source) return;
    const newData = { key: Date.now().toString(), ...formData };
    setData([...data, newData]);
    setFormData({ name: '', source: '', version: '' });
  };

  const columns = [
    {
      title: 'Image Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Source',
      dataIndex: 'source',
      key: 'source',
      render: (text) => <a href={text} target="_blank" rel="noopener noreferrer">{text}</a>,
    },
    {
      title: 'Latest Version',
      dataIndex: 'version',
      key: 'version',
    },
  ];

  return (
    <div className="resources-page-main">
      <h1>Resource Page</h1>
      <h2 className="text-xl font-bold mb-4">Image Details</h2>
      <Table columns={columns} dataSource={data} pagination={{ pageSize: 5 }} />
      
      <h2 className="text-lg font-bold mt-6">Add Image Details</h2>
      <div className="flex flex-col gap-2 mt-4">
        <Input name="name" value={formData.name} onChange={handleChange} placeholder="Image Name" />
        <Input name="source" value={formData.source} onChange={handleChange} placeholder="Source URL" />
        <Input name="version" value={formData.version} onChange={handleChange} placeholder="Latest Version" />
        <Button type="primary" onClick={handleAdd}>Add Image</Button>
      </div>
    </div>
  );
}
