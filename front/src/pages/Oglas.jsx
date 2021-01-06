import React, {Component} from 'react';
import {ButtonGroup, ToggleButton} from "react-bootstrap";
import cookie from "react-cookies";
import Lajkovi from "../components/Lajkovi";

class Oglas extends Component {

    constructor(props) {
        super(props)
        this.state = {
            user: cookie.load('principal'),
            oglas: '',
            isLoggedIn: cookie.load('isAuth')
        }
    }

    ocjene = [
        {name: 'Sviđa mi se🙂', value: '1'},
        {name: 'Jako mi se sviđa😁', value: '2'},
        {name: 'To je to😍', value: '3'},
        {name: 'Nemoj više prikazivati❌', value: '4'},
    ];

    componentDidMount() {
        let self = this

        //substring da maknemo id= i dobijemo čisti oglasId
        const oglasId = this.props.match.params.id.substring(3)

        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${oglasId}`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                    self.setState({oglas: body})
                }).catch(error => console.log(error))
            }
        });
    }

    change = (e) => {
        let self = this;
        let ocjena = e.target.value;
        let user = self.state.user;
        const oglasId = this.props.match.params.id.substring(3)

        this.setState({ocjena: ocjena});

        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/update?student_username=${user.korisnickoIme}&oglas_id=${oglasId}&ocjena=${ocjena}`, options)
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

        let oglas = this.state.oglas

        console.log(oglas)

        return (
            <div className='inner'>
                <p>naslov: {oglas.naslov}</p>
                <p>opis: {oglas.opis}</p>
                <p>godina: {oglas.godina}</p>
                <p>objavljen: {oglas.objavljen}</p>

                <Lajkovi oglas={oglas}></Lajkovi>
            </div>
        )
    }
}
export default Oglas;