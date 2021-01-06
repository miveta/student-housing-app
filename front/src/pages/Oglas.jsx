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

    componentDidMount() {
        let self = this

        //substring da maknemo id= i dobijemo čisti oglasId
        const oglasId = this.props.match.params.id.substring(3);

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

    render() {

        let oglas = this.state.oglas
        let oglasId = this.props.match.params.id.substring(3);

        return (
            <div className='inner'>
                <p>naslov: {oglas.naslov}</p>
                <p>opis: {oglas.opis}</p>
                <p>godina: {oglas.godina}</p>
                <p>objavljen: {oglas.objavljen}</p>

                <Lajkovi oglasId={oglasId}></Lajkovi>
            </div>
        )
    }
}
export default Oglas;