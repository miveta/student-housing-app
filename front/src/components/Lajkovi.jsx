import React, {Component} from "react";
import cookie from "react-cookies";
import {ButtonGroup, ToggleButton} from "react-bootstrap";

class Lajkovi extends Component{
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            isLoggedIn: cookie.load('isAuth'),
            ocjena: ''
        }
    }

    ocjene = [
        {name: 'Sviđa mi se🙂', value: '1'},
        {name: 'Jako mi se sviđa😁', value: '2'},
        {name: 'To je to😍', value: '3'},
        {name: 'Nemoj više prikazivati❌', value: '4'},
    ];

    componentDidMount() {
        let self = this;

        let user = self.state.user;
        let oglas = self.props.oglas;
        console.log("lajk")
        console.log(oglas)

        if(this.state.isLoggedIn) {
            const options = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                }
            };

            fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/ocjena?student_username=${user.korisnickoIme}&oglas_id=${oglas.id}`, options)
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
        let oglas = self.props.oglas;

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

    render() {
        return (
            <ButtonGroup size="sm" toggle>
                {this.ocjene.map((like, idx) => (
                    <ToggleButton
                        key={idx}
                        type="radio"
                        variant="outline-secondary"
                        value={like.value}
                        disabled={!this.state.isLoggedIn}
                        checked={this.state.ocjena === like.value}
                        onChange={(e) => this.change(e)}
                    >
                        {like.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
        )
    }
}
export default Lajkovi;