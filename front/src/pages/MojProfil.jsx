import React, {Component, useState} from "react";
import {Button, Col, Form, Modal} from "react-bootstrap";
import {Link} from "react-router-dom";
import cookie from 'react-cookies';
import {withRouter} from 'react-router-dom';
import Row from "react-bootstrap/Row";



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
                <h2>
                    Moj profil
                </h2>
                <br></br>
                <Form>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Ime
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.ime}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Prezime
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.prezime}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            JMBAG
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.jmbag}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Korisničko ime
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.korisnickoIme}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Email
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.email}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Obavijesti na mail
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.user.obavijestiNaMail ? "Da" : "Ne"}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                </Form>

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
