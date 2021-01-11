import React, {Component} from 'react';
import {Col, Dropdown, DropdownButton, Form, ListGroup} from "react-bootstrap";

class TrazimSobuReadOnly extends Component {
    constructor(props) {
        super(props);
        this.uvjeti = props.uvjeti
    }

    render() {
        return (
            <Form>
                <h3>{this.props.title}</h3>
                <Form.Group>
                    <Form.Label>Kat</Form.Label>
                    <Form.Control name="katovi" as="select" readOnly>
                        {
                            this.uvjeti.katovi && this.uvjeti.katovi.map((kat, idx) => (
                                <option id={idx}>{kat}</option>
                            ))
                        }
                    </Form.Control>
                </Form.Group>
                <Form.Group>
                    <Form.Row>
                        <Col>
                            <Form.Label>Broj kreveta</Form.Label>
                            <Form.Control name="kreveti" as="select" readOnly>
                                {
                                    this.uvjeti.brojKreveta && this.uvjeti.brojKreveta.map(brojKreveta => (
                                        <option>{brojKreveta}</option>
                                    ))
                                }
                            </Form.Control>
                        </Col>
                        <Col>

                            <Form.Label>Tip kupaonice</Form.Label>
                            <Form.Control name="kupaonice" as="select" readOnly>
                                {
                                    this.uvjeti.tipKupaonice && this.uvjeti.tipKupaonice.map(tipKupaonice => (
                                        <option>{tipKupaonice}</option>
                                    ))
                                }
                            </Form.Control>
                        </Col>
                    </Form.Row>
                </Form.Group>

                <Form.Group>
                    <DropdownButton title={"Domovi i paviljoni"} variant={"light"} block drop={"down"}>
                        {
                            this.uvjeti.domovi && this.uvjeti.domovi.length === 0 ?
                                (
                                    <Dropdown.Item>
                                        Nema odabranih domova i/ili paviljona.
                                    </Dropdown.Item>
                                )
                                : this.uvjeti.domovi.map(dom => (
                                    <>
                                        <Dropdown.Item>
                                            <Form.Label>{dom.naziv}</Form.Label>
                                            <ListGroup>
                                                {
                                                    dom.paviljoni && dom.paviljoni.filter(paviljon => this.uvjeti.paviljoni.includes(paviljon.id)).map(
                                                        paviljon => (
                                                            <ListGroup.Item>{paviljon.naziv}</ListGroup.Item>
                                                        )
                                                    )
                                                }
                                            </ListGroup>
                                        </Dropdown.Item>
                                        <Dropdown.Divider/>
                                    </>
                                ))
                        }
                    </DropdownButton>
                </Form.Group>
            </Form>
        )
    }
}

export default TrazimSobuReadOnly;