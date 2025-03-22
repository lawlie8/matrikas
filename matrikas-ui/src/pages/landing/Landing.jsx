import React from 'react';
import { Card, Typography, List } from 'antd';
import './Landing.css';
import vulnerabilityImage from '/data/image_222.jpg';
import managementImage from '/data/vulneraility_mange.jpg';

const { Title, Paragraph } = Typography;

export default function Landing() {
  return (
    <div className="landing-page-main">
      <div className="landing-content">
        <Card className="landing-card">
          <Title level={1} className="title">About Aqua Dashboard</Title>
          <Paragraph className="description">
            The Aqua Dashboard is a powerful tool designed to provide insights into your container security posture.
            It integrates with the Aqua Security platform to display real-time data on vulnerabilities, compliance,
            and security events.
          </Paragraph>

          <Title level={2} className="title">Features</Title>
          <List
            className="features-list"
            bordered={false}
            dataSource={[
              "Real-time vulnerability scanning",
              "Detailed reports on security incidents",
              "Integration with CI/CD pipelines",
              "Real-time release monitoring",
              "Automated CVE reporting and alerts",
              "Efficient version comparison",
            ]}
            renderItem={item => <List.Item className="list-item">{item}</List.Item>}
          />
        </Card>
      </div>
      <div className="images-container">
        <img src={vulnerabilityImage} alt="Vulnerability" className="landing-image" />
        <img src={managementImage} alt="Vulnerability Management" className="landing-image" />
      </div>
    </div>
  );
}
