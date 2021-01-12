import React, {Component} from 'react';
import TrazimSobu from "./TrazimSobu";
import Soba from "./Soba";
import cookie from "react-cookies";
import {Button} from "react-bootstrap";
import ParoviList from "../parovi/ParoviList";

export default class MojOglas extends Component {
    brojKreveta = [
        {name: "Jednokrevetna", value: "JEDNOKREVETNA"},
        {name: "Dvokrevetna", value: "DVOKREVETNA"},
        {name: "Trokrevetna", value: "JEDNOKREVETNA"},
        {name: "Višekrevetna", value: "VIŠEKREVETNA"}
    ]
    tipKupaonice = [
        {name: "Privatna", value: "PRIVATNA"},
        {name: "Dijeljena", value: "DIJELJENA"},
    ]

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
                domovi: [],
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
            this.setState({grad: {...json}});
            //console.log(json)
        }).catch(e => console.log(e))

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
        console.log(this.state)
        return (
            <div>


                <Soba grad={this.state.grad} soba={this.state.soba} submitSoba={this.submitSoba}
                              brojKreveta={this.brojKreveta} tipKupaonice={this.tipKupaonice}/>

                        {
                            this.state.soba.id === '' ?
                                <p>definirajte prvo svoju sobu</p>
                                :
                                <TrazimSobu grad={this.state.grad} uvjeti={this.state.uvjeti}
                                            brojKreveta={this.brojKreveta} tipKupaonice={this.tipKupaonice}
                                            submitUvjeti={this.submitUvjeti}/>
                        }

                <Button onClick={this.props.onArhiviraj} disabled={this.state.soba.id === ""}>Arhiviraj oglas</Button>

                <ParoviList/>
            </div>
        )
    }
}
