import React, {Component} from 'react';
import {Button, Col} from "react-bootstrap";
import Row from "react-bootstrap/Row";
import './arhiviraniOglasi.css'
import SobaReadOnly from "../SobaReadOnly";
import TrazimSobuReadOnly from "../TrazimSobuReadOnly";

class ArhiviraniOglasCard extends Component {
    constructor(props) {
        super(props)
        this.soba = props.oglas.soba
        this.uvjeti = props.oglas.uvjeti
    }

    onAktiviraj = () => {
        this.props.onAktiviraj(this.props.oglas.id)
    }

    render() {
        return (
            <Row>
                <Col>
                    <SobaReadOnly soba={this.soba} title={"Nudim sobu"}/>
                </Col>
                <Col>
                    <TrazimSobuReadOnly uvjeti={this.uvjeti} title={"Tražim sobu"}/>
                    <Button onClick={this.onAktiviraj}>Aktiviraj</Button>
                </Col>
            </Row>
        )
    }

}

export default ArhiviraniOglasCard;