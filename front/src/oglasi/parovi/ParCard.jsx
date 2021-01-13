import React, {Component} from 'react';
import SobaReadOnly from "../SobaReadOnly";
import {Button, Col, Row} from "react-bootstrap";

export default class ParCard extends Component {
    constructor(props) {
        super(props);
        this.state = {}

        this.par = props.par
        this.oglas = props.par.oglas1.student === props.user.korisnickoIme ? props.par.oglas2 : props.par.oglas1
    }

    onPrihvati = () => {

    }

    onOdbij = () => {

    }

    render() {
        return (
            <div className={"innerForm"}>
                <Row>
                    <SobaReadOnly horizontal={true} soba={this.oglas.soba}/>
                </Row>
                <Row>
                    <Col>
                        <Button className={"yes"} block>Prihvati</Button>
                    </Col>
                    <Col>
                        <Button className={"no"} block>Odbij</Button>
                    </Col>
                </Row>
                <br/>
                <Row>
                    Kada jednom prihvatite ili odbijete zamjenu ne možete više promijeniti svoj izbor!
                </Row>
            </div>
        )
    }

}