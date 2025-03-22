import { Row, Col, List, Form, Select, notification, Input, Tag } from 'antd';
import instance from '../../util/axios';
import './Scans.css';
import { CaretRightOutlined } from '@ant-design/icons';
import { CREATE_JOB_URL, GET_ALL_JOBS } from '../../util/Constants';
import React, { useEffect, useState } from 'react';


export default function Scans(params = { params }) {

    const [jobList, setJobList] = useState([]);

    useEffect(() => {
        instance.get(GET_ALL_JOBS).then((response) => {
            if (response.status === 200) {
                notification.success({
                    message: "Sucess",
                    duration: 1,
                    description: "Fetched All Jobs",
                    style: { width: '200px' }
                })
                setJobList(response?.data);
                console.log(response.data); 
            }
        }).catch(() => {
            notification.error({
                message: "Error",
                duration: 1,
                description: "Failed To Fetch Jobs",
                style: { width: '250px' }
            })
        });

    }, []);

    const onFinish = (values) => {
        if (values.imageName === undefined || values.imageVersion === undefined || values.jobName === undefined) {
            notification.warning({
                message: 'Warning',
                description: 'Missing Form Values'
            })
        } else {
            instance.post(CREATE_JOB_URL, {
                jobName: values.jobName,
                imageName: values.imageName,
                imageVersion: values.imageVersion
            }).then((response) => {
                if (response.status === 200) {
                    notification.success({
                        message: "Sucess",
                        duration: 1,
                        description: "Job Created",
                        style: { width: '200px' }
                    })
                }
                window.location.reload();
            }).catch(() => {
                notification.error({
                    message: "Error",
                    duration: 1,
                    description: "Job Failed To Start",
                    style: { width: '250px' }
                })
            });

        }
    };

    return <div className="scans-page-main">
        <h3 className="scans-page-heading">SCANS</h3>
        <div className='scans-page-content'>
            <Row gutter={16} style={{ height: '100%' }}>
                <Col span={6} style={{ height: '100%', borderRight: '1px solid #fc6008' }}>
                    <h3 style={{ width: '100%', textAlign: 'start', padding: '12px' }}>CREATE JOB</h3>
                    <Form name='VersionSelectionForm' layout='vertical' style={{ margin: '20px 10px' }} onFinish={onFinish}>
                        <Form.Item name="jobName" label="Job Name" required={true} >
                            <Input placeholder='Job Name'></Input>
                        </Form.Item>
                        <Form.Item name="imageName" label="Image Name" required={true}>
                            <Select placeholder="Image Name" options={[
                                {
                                    value: 'jack',
                                    label: 'Jack',
                                },
                                {
                                    value: 'lucy',
                                    label: 'Lucy',
                                },
                                {
                                    value: 'Yiminghe',
                                    label: 'yiminghe',
                                },
                                {
                                    value: 'disabled',
                                    label: 'Disabled',
                                    disabled: true,
                                },
                            ]}>

                            </Select>
                        </Form.Item>

                        <Form.Item name="imageVersion" label="Image Version" required={true}>
                            <Select placeholder="Image Version" options={[
                                {
                                    value: 'jack',
                                    label: 'Jack',
                                },
                                {
                                    value: 'lucy',
                                    label: 'Lucy',
                                },
                                {
                                    value: 'Yiminghe',
                                    label: 'yiminghe',
                                },
                                {
                                    value: 'disabled',
                                    label: 'Disabled',
                                    disabled: true,
                                },
                            ]}>
                            </Select>
                        </Form.Item>
                        <Form.Item>
                            <input className='scan-form-input' type="submit" value='CREATE JOB' />
                        </Form.Item>
                    </Form>
                </Col>
                <Col span={18} style={{ height: '100%' }}>
                    <List>

                        {
                            jobList.map((item, index) => (
                                <List.Item style={{ height: '75px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)', padding: '0px', margin: '10px 20px', borderRadius: '10px' }}>
                                    <ul style={{ display: 'flex', listStyle: 'none', height: '100%', width: '100%', margin: '0px', padding: '0px' }}>
                                        <li style={{ height: '100%', width: "50px", display: 'grid', alignContent: 'center' }}>
                                            {item.id}
                                        </li>
                                        <li style={{ height: '100%', width: "20%", display: 'grid', alignContent: 'center' }}>
                                            {item.jobName}
                                        </li>
                                        <li style={{ height: '100%', width: "20%", display: 'grid', alignContent: 'center' }}>
                                            <Tag style={{position:'relative',textAlign :'center'}}>
                                                {item.status}
                                            </Tag>

                                        </li>
                                        <li style={{ height: '100%', width: "20%", display: 'grid', alignContent: 'center' }}>
                                            {item.creationDate}
                                        </li>
                                        <li style={{ height: '100%', width: "40%", display: 'grid', alignContent: 'center' }}>
                                            <CaretRightOutlined className='job-start-icon' />
                                        </li>
                                    </ul>
                                </List.Item>
                            ))
                        }


                    </List>
                </Col>
            </Row>
        </div>

    </div>
}