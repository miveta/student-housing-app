import React, {Component} from 'react';
import {Card, Form} from "react-bootstrap";
import cookie from "react-cookies";
import Lajkovi from "../components/Lajkovi";
import SobaReadOnly from "../oglasi/SobaReadOnly";

class Oglas extends Component {
    constructor(props) {
        super(props)
        this.state = {
            user: cookie.load('principal'),
            oglas: '',
            isLoggedIn: cookie.load('isAuth'),
            soba: '',
            paviljon: ''
        }

        //substring da maknemo id= i dobijemo čisti oglasId
        const oglasId = this.props.match.params.id.substring(3);

        const optionsOglas = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${oglasId}`, optionsOglas)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        /*nisam ovo znala ljepše a da radi paviljon tj. da nije undefined*/
                        this.setState({oglas: body})
                        if (body.student === this.state.user.korisnickoIme) props.history.push("/oglasi")
                        this.setState({soba: this.state.oglas.soba})
                        this.setState({paviljon: this.state.soba.paviljon})
                    }).catch(error => console.log(error))
                }
            });
    }

    render() {
        let oglasId = this.props.match.params.id.substring(3);

        return (
            <div className="inner">
                {this.state.paviljon && <SobaReadOnly title={"Oglas sobe"} soba={this.state.soba}/>}
                <Form.Group>
                    <Form.Label> Kontakt E-mail </Form.Label>
                    <Form.Control readOnly name="email" value={this.state.oglas.studentEmail}/>
                </Form.Group>
                <Card.Text className="text-muted">
                    <br/>
                    Oglas je objavljen {this.state.oglas.objavljen}.
                </Card.Text>
                <Lajkovi oglasId={oglasId} user={this.state.user}/>
            </div>
        )
    }
}

export default Oglas;
