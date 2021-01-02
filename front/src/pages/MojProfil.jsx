import React, {Component, useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import {Link} from "react-router-dom";
import cookie from 'react-cookies';
import {withRouter} from 'react-router-dom';


class MojProfil extends Component {
    user;
    constructor(props) {
        super(props)
        this.user = cookie.load('principal');
    }

    state = {
        isOpen: false
    };

    openModal = () => this.setState({ isOpen: true });
    closeModal = () => this.setState({ isOpen: false });

    deleteUser = () => {

        const registerForm = {
            ime: this.user.ime,
            prezime: this.user.prezime,
            jmbag: this.user.jmbag,
            username: this.user.korisnickoIme,
            email: this.user.email,
            obavijestiNaMail: this.user.obavijestiNaMail
        };

        const options = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(registerForm)
        };

        console.log(options.body);
        fetch(`${process.env.REACT_APP_BACKEND_URL}/student/delete`, options)
            .then(response => {
                if (response.status === 200) {
                    this.props.onLogout();
                    this.props.history.push("/")
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    }


    render() {
        return (
            <div className="inner">

                <p>Ime: {this.user.ime} </p>
                <p>Prezime: {this.user.prezime}</p>
                <p>JMBAG: {this.user.jmbag} </p>
                <p>Korisničko ime: {this.user.korisnickoIme} </p>
                <p>Email: {this.user.email}</p>
                <p>Obavijesti na mail: {this.user.obavijestiNaMail ? "Da" : "Ne"}</p>
                <Link to="/mojprofil/uredi">
                <Button
                    type="submit"
                    variant="dark"
                    size="lg"> Uredi
                </Button>
                </Link>{' '}
                <Button
                    variant="dark"
                    size="lg"
                    onClick={this.openModal}> Izbriši
                </Button>
                <Modal show={this.state.isOpen} onHide={this.closeModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Jeste sigurni da želite izbrisati svoj profil?</Modal.Title>
                    </Modal.Header>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.closeModal}>
                            Poništi
                        </Button> {' '}
                        <Button variant="primary" onClick={this.deleteUser}>
                            Potvrdi
                        </Button>
                    </Modal.Footer>
                </Modal>

            </div>
        )
    }
}

export default withRouter(MojProfil);