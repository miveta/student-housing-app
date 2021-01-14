import React, {Component} from "react";
import cookie from "react-cookies";
import {Button, ButtonGroup, ToggleButton} from "react-bootstrap";
import Tooltip from "@material-ui/core/Tooltip";
import {makeid} from "./makeId";


class Lajkovi extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.user,
            oglas: '',
            isLoggedIn: cookie.load('isAuth'),
            ocjena: '',
            key: makeid(5)
        }
    }

    ocjene = [
        {name: '🙂', value: '1', description: 'Sviđa mi se'},
        {name: '😁', value: '2', description: 'Jako mi se sviđa'},
        {name: '😍', value: '3', description: 'To je to'},
        {name: '❌', value: '4', description: 'Nemoj više prikazivati'},
    ];

    async componentDidMount() {
        let self = this;

        let user = self.state.user;
        let oglas = self.props.oglas;

        if (this.state.isLoggedIn) {
            //ovo za fetchanje oglasa je ovdje jer inace baca nullpointer
            const optionsOglas = {
                method: 'GET',
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            };

            fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${self.props.oglasId}`, optionsOglas)
                .then(response => {
                    if (response.status === 200) {
                        response.json().then(body => {
                            self.setState({oglas: body})
                        }).catch(error => console.log(error))
                    }
                });


            const optionsLajk = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                }
            };

            await fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/ocjena?student_username=${this.state.user.korisnickoIme}&oglas_id=${self.props.oglasId}`, optionsLajk)
                .then(response => {
                    response.text().then(body => {
                        self.setState({ocjena: body})
                    });
                }).catch(error => console.log(error));
        }
    }

    change = (e) => {
        let self = this;
        let ocjena = e.target.value;
        let user = self.state.user;
        let oglas = self.state.oglas;
        this.setState({ocjena: ocjena});

        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/update?student_username=${user.korisnickoIme}&oglas_id=${oglas.id}&ocjena=${ocjena}`, options)
            .then(response => {
                if (response.status === 200) {
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    };

    clearInput = () => {
        let self = this;
        let user = self.state.user;
        let oglas = self.state.oglas;
        this.setState({ocjena: ''});

        const options = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/delete?student_username=${user.korisnickoIme}&oglas_id=${oglas.id}`, options)
            .then(response => {
                if (response.status === 200) {
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    }

    render() {
        return (
            <div className={"likes"} hidden={!this.state.user}>
                <ButtonGroup size="sm" toggle>
                    {this.ocjene.map((like, idx) => (
                        <Tooltip title={like.description}>
                            <ToggleButton
                                key={idx}
                                type="radio"
                                variant="outline-light"
                                value={like.value}
                                checked={this.state.ocjena === like.value}
                                onChange={(e) => this.change(e)}
                            >
                                {like.name}
                            </ToggleButton>
                        </Tooltip>
                    ))}
                    <Button variant={"likes"} onClick={this.clearInput} disabled={!this.state.isLoggedIn}>
                        Poništi odabir
                    </Button>
                </ButtonGroup>
            </div>
        )
    }
}

export default Lajkovi;
