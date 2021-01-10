import React, {Component} from 'react';
import TrazimSobu from "../components/TrazimSobu";
import Soba from "../components/Soba";
import cookie from "react-cookies";
import {Col, Row} from "react-bootstrap";


class MojOglas extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            grad: {
                id: '',
                naziv: '',
                domovi: []
            },
            soba: {
                id: '',
                brojKreveta: 'Jednokrevetna',
                kat: '',
                komentar: '',
                paviljon: undefined,
                tipKupaonice: 'Privatna'
            },
            uvjeti: {
                domId: [],
                paviljoni: [],
                katovi: [],
                brojKreveta: [],
                tipKupaonice: []
            },
            kandidati: [],
            changed: false
        }

        let options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };


        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/student?student_username=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            this.state.soba = {...json};
            this.state.changed = true
        }).catch(e => console.log("korisnik jos nema sobu"))


        fetch(`${process.env.REACT_APP_BACKEND_URL}/auth/grad?user=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({grad: {...json}})
        })
        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/zadano?user=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({uvjeti: {...json}})
        })
     }

    submitSoba = (soba) => {
        let self = this;

        const body = {
            studentUsername: self.state.user.korisnickoIme,
            idPaviljon: soba.idPaviljon,
            kat: soba.kat === "" ? 0 : soba.kat,
            brojKreveta: soba.brojKreveta.toUpperCase(),
            tipKupaonice: soba.tipKupaonice.toUpperCase(),
            komentar: soba.komentar
        };

        console.log(body)

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };


        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/spremi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            self.setState({...json})
        })
    }

    submitUvjeti = (uvjeti) => {
        let self = this;

        const body = {
            studentUsername: self.state.user.korisnickoIme,
            domId: uvjeti.domId,
            paviljoni: uvjeti.paviljoni,
            katovi: uvjeti.katovi,
            brojKreveta: uvjeti.brojKreveta,
            tipKupaonice: uvjeti.tipKupaonice,

        };

        console.log(body)
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };


        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/uvjetiIveta`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            self.setState({...json})
        })
    }

    render() {
        return (
            <div className="middle">
                <Row className={"outerForm"}>
                    <Col>
                        <Soba grad={this.state.grad} soba={this.state.soba} submitSoba={this.submitSoba}/>
                    </Col>
                    <Col>
                        {
                            this.state.soba.id === '' ?
                                <p>definirajte prvo svoju sobu</p>
                                :
                                <TrazimSobu grad={this.state.grad} uvjeti={this.state.uvjeti} submitUvjeti={this.submitUvjeti}/>
                        }
                    </Col>
                </Row>

                <h2> Preporučeni oglasi </h2>

            </div>
        )
    }
}


export default MojOglas;