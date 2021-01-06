import React, {Component} from 'react';
import TrazimSobu from "../components/TrazimSobu";
import Soba from "../components/Soba";
import cookie from "react-cookies";


class MojOglas extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            soba: {
                id: '',
                brojKreveta: '',
                kat: '',
                komentar: '',
                paviljon: undefined,
                tipKupaonice: ''
            },
            gradovi: []
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
        }).catch(e => console.log(e))


        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({gradovi: json})
        })
    }

    submitSoba = (soba) => {
        let self = this;

        const body = {
            studentUsername: self.state.user.korisnickoIme,
            idPaviljon: soba.idPaviljon,
            kat: soba.kat,
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
    }


    render() {
        return (
            <div className="middle">
                <Soba gradovi={this.state.gradovi} soba={this.state.soba} submitSoba={this.submitSoba}/>
                <TrazimSobu korisnikImaSobu={this.state.soba && this.state.soba.id !== ''}/>
            </div>
        )
    }
}


export default MojOglas;