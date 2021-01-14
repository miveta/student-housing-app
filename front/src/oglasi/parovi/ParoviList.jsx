import React, {Component} from 'react';
import * as cookie from "react-cookies";
import ParCard from "./ParCard";
import LanacCard from "./LanacCard";


export default class ParoviList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            parovi: [],
            lanci: [],
            user: cookie.load('principal'),
            postojiPotvrden: false
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
            let parovi = json.filter(par => !par.lanac)
            this.setState({parovi: parovi})

            let lanci = json.filter(par => par.lanac)
            this.setState({lanci: lanci})

            json.filter(par => {
                let prvi = par.oglas1.student === this.state.user.korisnickoIme
                if((prvi && par.potvrdioPrvi) || (!prvi && par.potvrdioDrugi)) this.setState({postojiPotvrden: true})
            })

        }).catch(() => console.log("korisnik nema oglase?"))
    }


    render() {
        let lanciOglasi = []
        for (let i = 0; i < this.state.lanci.length; i += 2) {
            let lanac = {
                par1: this.state.lanci[i],
                par2: this.state.lanci[i + 1]
            }

            lanciOglasi.push(lanac)
        }
        return (
            <div>
                <h3>Najbolji kandidati za Vaš oglas!</h3>
                {this.state.parovi.map(par => (<div>
                    <ParCard
                        par={par}
                        user={this.state.user}
                    postojiPotvrden={this.state.postojiPotvrden}/>
                </div>))

                }

                {
                    lanciOglasi.map(lanac => (
                        <div>
                            <LanacCard
                                par1={lanac.par1}
                                par2={lanac.par2}
                                user={this.state.user}
                                postojiPotvrden={this.state.postojiPotvrden}/>
                        </div>
                    ))
                }
            </div>
        )
    }
}
