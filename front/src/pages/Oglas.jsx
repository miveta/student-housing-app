import React, {Component} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import cookie from "react-cookies";
import Lajkovi from "../components/Lajkovi";

class Oglas extends Component {

     constructor(props) {
        super(props)
        this.state = {
            user: cookie.load('principal'),
            oglas: '',
            isLoggedIn: cookie.load('isAuth'),
            soba: ''
        }
    }

    componentDidMount() {
        let self = this

        //substring da maknemo id= i dobijemo čisti oglasId
        const oglasId = this.props.match.params.id.substring(3);

        const optionsOglas = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${oglasId}`, optionsOglas)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({oglas: body})
                    }).catch(error => console.log(error))
                }
            });

        const optionsSoba = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/getsoba?oglas_id=${oglasId}`, optionsSoba)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({soba: body})
                        console.log(body);
                    }).catch(error => console.log(error))
                }
            });
    }

    render() {

        let oglas = this.state.oglas
        let oglasId = this.props.match.params.id.substring(3);
        //todo: kako doci do podataka u paviljonu?
        const paviljon = this.state.soba.paviljon
        console.log(paviljon)

        return (
            <div className="innerForm">
                <h2>
                    Oglas sobe
                </h2>
                <br></br>
                <Form>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Kat sobe u paviljonu
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.kat}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Broj kreveta
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.brojKreveta}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Tip kupaonice
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.tipKupaonice}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Dodatni komentar
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.komentar}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <br/>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"}>
                            Oglas objavljen:
                        </Form.Label>
                        <Col>
                            <Form.Label>
                                {oglas.objavljen}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                </Form>
                <br></br>
                <Lajkovi oglasId={oglasId}></Lajkovi>
            </div>
        )
    }
}
export default Oglas;