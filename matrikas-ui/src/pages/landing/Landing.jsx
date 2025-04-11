import React from 'react';
import { Card, Typography, List } from 'antd';
import './Landing.css';

const { Title, Paragraph } = Typography;

export default function Landing() {
  return (
    <div className="landing-page-main" style={{ backgroundColor: 'white', padding: '20px', display: 'flex', justifyContent: 'center' }}>
      <Card style={{ maxWidth: 800, width: '100%', boxShadow: 'none', border: 'none' }}>
        <Title level={1}>Matrikas Dashboard</Title>
        <Paragraph>
          The Matrikas Dashboard is a powerful tool designed to provide insights into your container security posture.
          It integrates with the Matrikas Security platform to display real-time data on vulnerabilities, compliance,
          and security events.
        </Paragraph>
        
        <Title level={2}>Features</Title>
        <List
          bordered={false}
          dataSource={[        "Real-time vulnerability scanning",
                                     "Detailed reports on security incidents",
                                     "Integration with CI/CD pipelines",
                                     "Real-time release monitoring",
                                     "Automated CVE reporting and alerts",
                                     "Efficient version comparison",]}
          renderItem={item => <List.Item>{item}</List.Item>}
        />
        
        <Title level={2}>Getting Started</Title>
        <Paragraph>
          To get started, ensure you have the <b>Docker</b> and <b>Trivy</b> installed to access the Matrikas Security platform.
          Follow the documentation for setup instructions and best practices.
        </Paragraph>
        
        <Title level={2}>Contact</Title>
        <Paragraph>
          For support, please reach out to our team at <a href="mailto:support@matrikas.com">support@matrikas.com</a>.
        </Paragraph>
      </Card>
    </div>
  );
}