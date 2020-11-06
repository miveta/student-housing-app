import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class Register extends Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <Form>
                <h3>Register</h3>

                <Form.Group>
                    <Form.Label> Ime </Form.Label>
                    <Form.Control type="text" placeholder="Unesite svoje ime"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Prezime </Form.Label>
                    <Form.Control type="text" placeholder="Unesite svoje prezime"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Email </Form.Label>
                    <Form.Control type="email" placeholder="Enter email"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Password </Form.Label>
                    <Form.Control type="password" placeholder="Enter password"/>
                </Form.Group>
                <Button href="#" type="submit" variant="dark" size="lg" block>Sign in</Button>
                {/*
                // Already registered
                */}
            </Form>

        );
    }
}

export default Register;