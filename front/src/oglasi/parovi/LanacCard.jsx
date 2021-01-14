import React, {Component} from 'react';
import {Button, Col, Row} from "react-bootstrap";
import SobaReadOnly from "../SobaReadOnly";

export default class ParCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            par1: props.par2
        };

        this.par1 = props.par1;
        this.oglas1 = props.par1.oglas1.student === props.user.korisnickoIme ? props.par1.oglas2 : props.par1.oglas1

        this.par2 = props.par2
        this.oglas2 =  props.par2.oglas1.student === props.user.korisnickoIme ? props.par2.oglas2 : props.par2.oglas1

        this.oglas = props.par1.oglas1.student === props.user.korisnickoIme ? props.par1.oglas1 : props.par1.oglas2
    }

    onUpdatePar = (potvrdeno) => {
        let self = this
        const options = {
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/updatePar?par_id=${this.par1.parID}&par2_id=${this.par2.parID}&odobren=${potvrdeno}&student_username=${this.props.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            self.setState(json)
        }).catch(() => console.log("korisnik nema oglase?"))
    };

    onPrihvati = () => {
        this.onUpdatePar(true)
    };

    onOdbij = () => {
        this.onUpdatePar(false)
    };

    render() {
        console.log(this.oglas1)
        return (
            <div className={"innerForm"}>
                <Row>
                    <SobaReadOnly horizontal={true} title={this.oglas1.student} soba={this.oglas1.soba}/>
                    <br/>
                    <SobaReadOnly horizontal={true} title={"Vaša soba"} soba={this.oglas.soba}/>
                    <br/>
                    <SobaReadOnly horizontal={true} title={this.oglas2.student} soba={this.oglas2.soba}/>
                </Row>
                <Row>
                    <Col>
                        <Button disabled={this.props.postojiPotvrden} className={"yes"} block onClick={this.onPrihvati}>Prihvati lančanu zamjenu</Button>
                    </Col>
                    <Col>
                        <Button disabled={this.props.postojiPotvrden} className={"no"} block onClick={this.onOdbij}>Odbij lančanu zamjenu</Button>
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
