import React, {Component} from 'react';
import ArhiviraniOglasCard from "./ArhiviraniOglasCard";
import * as cookie from "react-cookies";

class ArhiviraniOglasiList extends Component {
    constructor(props) {
        super(props)
        this.state = {
            oglasi: [],
            user: cookie.load('principal')
        }

        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/arhivirani?student_username=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            this.setState({oglasi: json})
        }).catch(e => console.log("korisnik nema oglase?"))
    }

    render() {
        return (
            <div>
                {
                    this.state.oglasi.map(oglas =>
                        <div className={"card"}>
                            <ArhiviraniOglasCard
                                onAktiviraj={this.props.onAktiviraj}
                                id={oglas.id}
                                key={oglas.id}
                                oglas={oglas}
                                user={this.state.user}/>
                        </div>
                    )
                }
            </div>

        );
    }
}

export default ArhiviraniOglasiList;
