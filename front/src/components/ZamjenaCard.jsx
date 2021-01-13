import React, {Component} from "react";
import * as cookie from "react-cookies";
import {Button, Col, Form, Row} from "react-bootstrap";
import ZamjenaPrikazPara from "./ZamjenaPrikazPara";
import SobaReadOnly from "../oglasi/SobaReadOnly";


class ZamjenaCard extends Component {
    constructor(props) {
        super(props);
        console.log(this.props)
        this.state = {
            user: cookie.load('principal'),
            odobren: props.odobren,
            parId: props.parId,
            oglas1: props.oglas1,
            oglas2: props.oglas2,
            soba1: props.oglas1.soba,
            soba2: props.oglas2.soba
        }
    }

    click = (b) => {
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        };
        console.log(b)
        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/updateParSC?par_id=${this.state.parId}&odobren=${b}&zaposlenikKorisnickoIme=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                    });
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    }

    render() {
        console.log(this.state.soba1)
        return (

            <Row>
                <Col>
                    <SobaReadOnly soba={this.state.soba1} title={this.state.oglas1.studentJmbag}/>
                    <Form.Group>
                        <Form.Label> E-mail </Form.Label>
                        <Form.Control readOnly name="email" value={this.state.oglas1.studentEmail}/>
                    </Form.Group>
                    {!this.state.odobren && <Button className="yes" onClick={() => this.click(true)} block>Odobri</Button>}
                </Col>
                <Col>
                    <SobaReadOnly soba ={this.state.soba2} title={this.state.oglas2.studentJmbag}/>
                    <Form.Group>
                        <Form.Label> E-mail </Form.Label>
                        <Form.Control readOnly name="email" value={this.state.oglas2.studentEmail}/>
                    </Form.Group>
                    {!this.state.odobren && <Button className="no" onClick={() => this.click(false)} block>Odbij</Button>}
                </Col>
                {this.state.odobren && <Button variant="dark" disabled={true} block>Potvrdio pero</Button>}
            </Row>
        )
    }

}

export default ZamjenaCard;