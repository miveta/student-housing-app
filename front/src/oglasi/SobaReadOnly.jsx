import React, {Component} from 'react';
import {Col, Form} from "react-bootstrap";

class SobaReadOnly extends Component {
    constructor(props) {
        super(props)
        this.soba = props.soba
        this.title = props.title
    }

    render() {
        return (
            <Form>
                <h3> {this.title} </h3>
                <Form.Group>
                    <Form.Label> Dom </Form.Label>
                    <Form.Control readOnly name="dom" value={this.soba.paviljon.nazivDom}/>
                </Form.Group>
                <Form.Group>
                    <Form.Row>
                        <Col xs={9}>
                            <Form.Label> Paviljon </Form.Label>
                            <Form.Control readOnly name="paviljon" value={this.soba.paviljon.naziv}/>
                            <Form.Text className={"textMuted"}>
                                Ovo je paviljon {this.soba.paviljon.kategorija} kategorije.
                            </Form.Text>
                        </Col>
                        <Col>
                            <Form.Label> Kat </Form.Label>
                            <Form.Control name="kat" readOnly type="number" defaultValue={this.soba.kat}/>
                        </Col>
                    </Form.Row>
                </Form.Group>
                <Form.Group>
                    <Form.Row>
                        <Col>
                            <Form.Label> Broj kreveta </Form.Label>
                            <Form.Control readOnly name="brojKreveta" value={this.soba.brojKreveta}/>
                        </Col>
                        <Col>
                            <Form.Label> Tip kupaonice </Form.Label>
                            <Form.Control readOnly name="tipKupaonice" value={this.soba.tipKupaonice}/>
                        </Col>
                    </Form.Row>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Komentar </Form.Label>
                    <Form.Control name="komentar" readOnly rows="2" as={"textarea"}
                                  defaultValue={this.soba.komentar}/>
                </Form.Group>
            </Form>
        )
    }
}

export default SobaReadOnly;