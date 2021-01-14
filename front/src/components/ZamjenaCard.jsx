import React, {Component} from "react";
import * as cookie from "react-cookies";
import {Button, Col, Form, Row} from "react-bootstrap";
import SobaReadOnly from "../oglasi/SobaReadOnly";


class ZamjenaCard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: cookie.load('principal'),
            odobren: props.par.odobren,
            parId: props.par.parID,
            oglas1: props.par.oglas1,
            oglas2: props.par.oglas2,
            soba1: props.par.oglas1.soba,
            soba2: props.par.oglas2.soba
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

        let self = this;
        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/updateParSC?par_id=${self.state.parId}&odobren=${b}&zaposlenikKorisnickoIme=${self.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    };

    render() {
        return (

            <Row>
                <Col>
                    <SobaReadOnly soba={this.state.soba1} title={this.state.oglas1.studentJmbag}/>
                    <Form.Group>
                        <Form.Label> E-mail </Form.Label>
                        <Form.Control readOnly name="email" value={this.state.oglas1.studentEmail}/>
                    </Form.Group>
                    {!this.state.odobren &&
                    <Button className="yes" onClick={() => this.click(true)} block>Odobri</Button>}
                </Col>
                <Col>
                    <SobaReadOnly soba={this.state.soba2} title={this.state.oglas2.studentJmbag}/>
                    <Form.Group>
                        <Form.Label> E-mail </Form.Label>
                        <Form.Control readOnly name="email" value={this.state.oglas2.studentEmail}/>
                    </Form.Group>
                    {!this.state.odobren &&
                    <Button className="no" onClick={() => this.click(false)} block>Odbij</Button>}
                </Col>
                {this.state.odobren && <Button variant="dark" disabled={true}
                                               block>Potvrdio {this.props.par.zaposlenikSC.korisnickoIme}</Button>}
            </Row>
        )
    }

}

export default ZamjenaCard;