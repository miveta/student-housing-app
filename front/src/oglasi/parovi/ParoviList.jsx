import React, {Component} from 'react';
import * as cookie from "react-cookies";

export default class ParoviList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            parovi: [],
            user: cookie.load('principal')
        }

        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/listParovi?student_username=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            this.setState({parovi: json})
        }).catch(e => console.log("korisnik nema oglase?"))
    }

    render() {
        console.log(this.state)
        return (<div></div>)
    }
}