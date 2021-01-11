import React, {Component} from 'react';
import {Col, Nav, Row, Tab} from "react-bootstrap";

class OglasiPage extends Component {
    constructor(props) {
        super(props);

    }


    render() {
        return (
            <div className="middle">
                <Tab.Container id="left-tabs-example" defaultActiveKey="first" className={"left-tabs"}>
                    <Row>
                        <Col sm={2}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link eventKey="first">Aktivni oglas</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link eventKey="second"> Arhivirani oglasi</Nav.Link>
                                </Nav.Item>


                            </Nav>
                        </Col>
                        <Col>
                            <Tab.Content>
                                <Tab.Pane eventKey="first">

                                </Tab.Pane>

                                <Tab.Pane eventKey="second">

                                </Tab.Pane>

                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        )
    }
}