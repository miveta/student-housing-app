import React, {Component} from "react";
import {Button, Col, Form} from 'react-bootstrap';
import {Link, Redirect} from "react-router-dom";
import MdEye from 'react-ionicons/lib/MdEye';
import MdEyeOff from 'react-ionicons/lib/MdEyeOff'

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
            username: '',
            password: '',
            error: '',
            type: 'password'
        };
        this.showHide = this.showHide.bind(this);
    }

    showHide(e){
        e.preventDefault();
        e.stopPropagation();
        this.setState({
            type: this.state.type === 'input' ? 'password' : 'input'
        })
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
                        <Form.Row>
                            <Col xs={10}>
                                <Form.Control name="password" type={this.state.type} // tu ne trebaju placeholderi
                                              onChange={this.onChange}
                                              required/>
                            </Col>
                            <Col>
                                <Button className="passwordShow" variant={"light"} onClick={this.showHide}>{this.state.type === 'input' ? <MdEye></MdEye> : <MdEyeOff></MdEyeOff>}</Button>
                            </Col>
                        </Form.Row>
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