import React, {Component} from "react";
import {Link} from "react-router-dom";
import {ButtonGroup, ToggleButton} from "react-bootstrap";

class OglasCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: JSON.parse(localStorage.getItem("user")),
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
        console.log(this.props)
        let self = this;

        let user = self.state.user;
        let oglas = self.props.oglas;


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
        let oglas = this.props.oglas;
        return (
            <div className={"Card"}>
                {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
                <br/>
                {oglas.opis && <p>{oglas.opis}</p>}
                <br/>

                <ButtonGroup size="sm" toggle>
                    {this.ocjene.map((like, idx) => (
                        <ToggleButton
                            key={idx}
                            type="radio"
                            variant="outline-secondary"
                            value={like.value}
                            disabled={!this.props.isLoggedIn}
                            checked={this.state.ocjena === like.value}
                            onChange={(e) => this.change(e)}
                        >
                            {like.name}
                        </ToggleButton>
                    ))}
                </ButtonGroup>
            </div>
        )
    }


}

export default OglasCard;