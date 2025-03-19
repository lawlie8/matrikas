import React, { useState } from 'react';
import './Resources.css';

export default function Resources(params={params}) {
  const initialData = [
    { name: 'aws-ebs-csi', source: 'https://github.com/kubernetes-sigs/aws-ebs-csi-driver/releases', version: '' },
    { name: 'calico', source: 'https://github.com/projectcalico/calico/releases/', version: '' },
    { name: 'aws-vpc-cni', source: 'https://github.com/aws/amazon-vpc-cni-k8s/releases', version: '' },
  ];

  const [data, setData] = useState(initialData);
  const [formData, setFormData] = useState({ name: '', source: '', version: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAdd = () => {
    setData([...data, formData]);
    setFormData({ name: '', source: '', version: '' });
  };

  return (
    <div className="resources-page-main">
      <h1>Resource Page Goes Here</h1>
      <h2 className="text-xl font-bold mb-4">Image Details</h2>
      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr>
            <th className="border p-2">Image Name</th>
            <th className="border p-2">Source</th>
            <th className="border p-2">Latest Version</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              <td className="border p-2">{item.name}</td>
              <td className="border p-2"><a href={item.source} className="text-blue-500" target="_blank" rel="noopener noreferrer">{item.source}</a></td>
              <td className="border p-2">{item.version}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <h2 className="text-lg font-bold mt-6">Add Image Details</h2>
      <div className="flex flex-col gap-2 mt-4">
        <input className="border p-2" name="name" value={formData.name} onChange={handleChange} placeholder="Image Name" />
        <input className="border p-2" name="source" value={formData.source} onChange={handleChange} placeholder="Source URL" />
        <input className="border p-2" name="version" value={formData.version} onChange={handleChange} placeholder="Latest Version" />
        <button className="bg-blue-500 text-white p-2" onClick={handleAdd}>Add Image</button>
      </div>
    </div>
  );
}
