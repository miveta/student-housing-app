import React, {Component} from 'react';
import TrazimSobu from "./TrazimSobu";
import Soba from "./Soba";
import cookie from "react-cookies";
import {Button, Row} from "react-bootstrap";

export default class MojOglas extends Component {
    brojKreveta = [
        {name: "Jednokrevetna", value: "JEDNOKREVETNA", key: "kr1"},
        {name: "Dvokrevetna", value: "DVOKREVETNA", key: "kr2"},
        {name: "Trokrevetna", value: "JEDNOKREVETNA", key: "kr3"},
        {name: "Višekrevetna", value: "VIŠEKREVETNA", key: "kr4"}
    ];
    tipKupaonice = [
        {name: "Privatna", value: "PRIVATNA", key: "ku1"},
        {name: "Dijeljena", value: "DIJELJENA", key: "kr2"},
    ];

    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            grad: {
                id: '',
                naziv: '',
                domovi: [],
                key: makeid(5)
            },
            soba: {
                id: '',
                brojKreveta: 'Jednokrevetna',
                kat: '',
                komentar: '',
                paviljon: undefined,
                tipKupaonice: 'Privatna',
                key: makeid(5)
            },
            uvjeti: {
                domovi: [],
                paviljoni: [],
                katovi: [],
                brojKreveta: [],
                tipKupaonice: [],
                key: makeid(5)
            },
            kandidati: [],
            changed: false,
            key: makeid(5)
        };

        function makeid(length) {
            var result           = '';
            var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            var charactersLength = characters.length;
            for ( var i = 0; i < length; i++ ) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
            }
            return result;
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
        }).catch(() => console.log("korisnik jos nema sobu"));


        fetch(`${process.env.REACT_APP_BACKEND_URL}/auth/grad?user=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({grad: {...json}});
            //console.log(json)
        }).catch(e => console.log(e));

        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/zadano?user=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({uvjeti: {...json}});
            //console.log(json)
        }).catch(e => console.log(e))
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
    };

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
    };

    render() {
        return (
            <div>
                {
                    this.state.soba.id !== '' &&
                    <Row style={{padding: "0px 30px",}}>
                        <Button className="yes margin" onClick={this.props.onArhiviraj}
                                disabled={this.state.soba.id === ""} block>Arhiviraj
                            oglas</Button></Row>
                }

                <Soba grad={this.state.grad} soba={this.state.soba} submitSoba={this.submitSoba}
                      brojKreveta={this.brojKreveta} tipKupaonice={this.tipKupaonice}/>

                {
                    this.state.soba.id !== '' &&
                    <TrazimSobu grad={this.state.grad} uvjeti={this.state.uvjeti}
                                brojKreveta={this.brojKreveta} tipKupaonice={this.tipKupaonice}
                                submitUvjeti={this.submitUvjeti}/>
                }
            </div>
        )
    }
}
