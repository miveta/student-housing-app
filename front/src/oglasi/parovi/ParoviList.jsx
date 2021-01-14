import React, {Component} from 'react';
import * as cookie from "react-cookies";
import ParCard from "./ParCard";


export default class ParoviList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            parovi: [],
            user: cookie.load('principal')
        };

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
        }).catch(() => console.log("korisnik nema oglase?"))
    }


    render() {
        return (
            <div>
                <h3>Najbolji kandidat! :)</h3>
                {this.state.parovi.length === 1 ?
                    <div>
                        <ParCard
                            id={this.state.parovi[0].id}
                            key={this.state.parovi[0].id}
                            par={this.state.parovi[0]}
                            user={this.state.user}/>
                    </div>

                    : <p>vise od jednog {this.state.parovi.length}</p>
                }

            </div>)
    }
}
