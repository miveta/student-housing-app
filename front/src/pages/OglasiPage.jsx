import React, {Component} from 'react';
import {Col, Nav, Row, Tab} from "react-bootstrap";
import MojOglas from "../oglasi/aktivni/MojOglas";
import ArhiviraniOglasiList from "../oglasi/arhivirani/ArhiviraniOglasiList";
import * as cookie from "react-cookies";

export default class OglasiPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            user: cookie.load('principal')
        }
    }

    onAktiviraj = (oglasId) => {
        let self = this

        let options = {
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/aktiviraj?student_username=${self.state.user.korisnickoIme}&oglas_id=${oglasId}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            window.location.reload();
        }).catch(e => console.log("korisnik jos nema sobu"))

    }

    onArhiviraj = () => {
        let self = this
        let options = {
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/arhiviraj?student_username=${self.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            window.location.reload();
        }).catch(e => console.log("korisnik jos nema sobu"))
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
                                    <MojOglas onArhiviraj={this.onArhiviraj}/>
                                </Tab.Pane>

                                <Tab.Pane eventKey="second">
                                    <ArhiviraniOglasiList onAktiviraj={this.onAktiviraj}/>
                                </Tab.Pane>

                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        )
    }
}