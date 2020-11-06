import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class Login extends Component {
    constructor(props){
        super(props);
    }


    render() {
        return (
            <Form>
                <h3>Log in</h3>

                <Form.Group>
                    <Form.Label> Email </Form.Label>
                    <Form.Control type="email" placeholder="Enter email"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Password </Form.Label>
                    <Form.Control type="password" placeholder="Enter password"/>
                </Form.Group>
                <Form.Group>
                    <Form.Check type="checkbox" label="Remember me"/>
                </Form.Group>
                <Button href="#" type="submit" variant="dark" size="lg" block>Sign in</Button>

                {
                    // Zakomentirano zato što nisam sigurna da ćemo raditi to sa forgot your pwd
                    /*
                    <p className="forgot-password text-right">
                        Forgot <a href="#">password?</a>
                    </p>
                    */
                }
            </Form>
        );
    }
}

export default Login;