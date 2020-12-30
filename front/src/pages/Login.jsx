import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';
import {Link, Redirect} from "react-router-dom";
import App from "../App";


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
            username: '',
            password: '',
            error: ''
        };
    }


    onChange = (event) => {
        const {name, value} = event.target;
        this.setState(state => ({...state, [name]: value}))
    };

    onSubmit = (e) => {
        let self = this;

        e.preventDefault();

        const body = {
            username: this.state.username,
            password: this.state.password
        };

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };


        fetch(`${process.env.REACT_APP_BACKEND_URL}/auth/login`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.props.authenticate(body);
                        self.setState({redirect: true})
                    });
                } else if (response.status === 401 || response.status === 400) {
                    response.text().then(body => {
                        self.setState({error: body})
                    });
                } else {
                    response.text().then(body => {
                        self.setState({error: body})
                    });
                }
            }).catch(error => console.log(error));
    };

    isValid = () => {
        return this.state.username.length > 0 && this.state.password.length > 5;
    };

    render() {
        if (this.props.authenticated) return <Redirect to={"/"}/>;

        return (
            <div className="inner">
                <Form onSubmit={this.onSubmit}>
                    <h3>Prijava</h3>

                    <Form.Group>
                        <Form.Label> Korisničko ime </Form.Label>
                        <Form.Control name="username" type="text"
                                      onChange={this.onChange}
                                      required/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Lozinka </Form.Label>
                        <Form.Control name="password" type="password" // tu ne trebaju placeholderi
                                      onChange={this.onChange}
                                      required/>
                    </Form.Group>
                    <p className="errorMessage">
                        {this.state.error}
                    </p>
                    <Button type="submit" variant="dark" size="lg" block disabled={!this.isValid}> Prijavi se </Button>
                    <p className="not-registered text-right">
                        <Link to="/register">Nisi još registriran?</Link>
                    </p>
                </Form>
            </div>
        )
    }
}

export default Login;