import { Row, Col, List, Form, Select, notification } from 'antd';
import instance from '../../util/axios';
import './Scans.css';

export default function Scans(params = { params }) {


    const onFinish = (values) => {
        if (values.imageName === undefined || values.imageVersion === undefined) {
            notification.warning({
                message: 'Warning',
                description: 'Missing Form Values'
            })
        } else {
            // instance.get()
        }
    };

    return <div className="scans-page-main">
        <h3 className="scans-page-heading">SCANS</h3>
        <div className='scans-page-content'>
            <Row gutter={16} style={{ height: '100%' }}>
                <Col span={6} style={{ height: '100%', borderRight: '1px solid #fc6008' }}>
                    <h3 style={{ width: '100%', textAlign: 'start', padding: '12px' }}>SELECT IMAGE</h3>
                    <Form name='VersionSelectionForm' layout='vertical' style={{ margin: '20px 10px' }} onFinish={onFinish}>
                        <Form.Item name="imageName" label="imageName" required={true}>
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

                        <Form.Item name="imageVersion" label="imageVersion" required={true}>
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
                            <input className='scan-form-input' type="submit" value='SUBMIT' />
                        </Form.Item>
                    </Form>
                </Col>
                <Col span={18} style={{ height: '100%' }}>
                    <List>
                        <List.Item style={{ height: '75px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)', margin: '10px 20px', borderRadius: '10px' }}>

                        </List.Item>
                        <List.Item style={{ height: '75px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)', margin: '10px 20px', borderRadius: '10px' }}>

                        </List.Item>
                    </List>
                </Col>
            </Row>
        </div>

    </div>
}