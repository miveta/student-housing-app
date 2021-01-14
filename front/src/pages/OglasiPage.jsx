import React, {Component} from 'react';
import {Col, Dropdown, NavLink, Row, Tab} from "react-bootstrap";
import MojOglas from "../oglasi/aktivni/MojOglas";
import ArhiviraniOglasiList from "../oglasi/arhivirani/ArhiviraniOglasiList";
import * as cookie from "react-cookies";
import NavItem from "react-bootstrap/NavItem";
import ParoviList from "../oglasi/parovi/ParoviList";
import * as Swal from "sweetalert2";

export default class OglasiPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            user: cookie.load('principal')
        }
    }

    onAktiviraj = (oglasId) => {
        let self = this;

        let options = {
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/aktiviraj?student_username=${self.state.user.korisnickoIme}&oglas_id=${oglasId}`, options)
            .then(response => {
                if (response.status === 200) {
                    window.location.reload();
                } else if (response.status === 400) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Možete imati samo jedan aktivan oglas!',
                        type: 'error',
                        toast: true,
                        confirmButtonColor: '#12c2e9',
                        confirmButtonText: 'Ok',
                    })
                }
            }).catch(() => console.log("korisnik jos nema sobu"))
    };

    onArhiviraj = () => {
        let self = this;
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
            }).then(() => {
            window.location.reload();
        }).catch(() => console.log("korisnik jos nema sobu"))
    };

    render() {
        return (
            <Tab.Container id="left-tabs-example" defaultActiveKey="mojOglas" className={"tabs"}>
                <Row className={"tabs"}>
                    <Col sm={3} className={"left"}>
                        <Dropdown as={NavItem} className={"navigation"}>
                            <Dropdown.Header as={"h2"}>Tvoj aktivni oglas</Dropdown.Header>
                            <Dropdown.Item as={NavLink} eventKey="mojOglas" block>
                                Moj oglas
                            </Dropdown.Item>
                            <Dropdown.Item as={NavLink} eventKey="kandidati" block>
                                Najbolji kandidati
                            </Dropdown.Item>
                            <Dropdown.Divider/>
                            <Dropdown.Item as={NavLink} eventKey="arhivirani" block>
                                Arhivirani oglasi
                            </Dropdown.Item>
                            <Dropdown.Divider/>
                            <Dropdown.Item as={NavLink} eventKey="provedeni" block>
                                Provedene zamjene
                            </Dropdown.Item>
                        </Dropdown>
                    </Col>
                    <Col className={"middle"}>
                        <Tab.Content>
                            <Tab.Pane eventKey="mojOglas">
                                <MojOglas onArhiviraj={this.onArhiviraj}/>
                            </Tab.Pane>

                            <Tab.Pane eventKey="kandidati">
                                <ParoviList/>
                            </Tab.Pane>

                            <Tab.Pane eventKey="arhivirani">
                                <ArhiviraniOglasiList onAktiviraj={this.onAktiviraj}/>
                            </Tab.Pane>

                        </Tab.Content>
                    </Col>
                    <Col sm={3} className={"right"}/>

                </Row>
            </Tab.Container>

        )
    }
}