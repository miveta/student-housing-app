import React, {Component} from "react";
import {Button, Col, Form} from 'react-bootstrap';
import {Link, Redirect} from "react-router-dom";
import MdEye from "react-ionicons/lib/MdEye";
import MdEyeOff from "react-ionicons/lib/MdEyeOff";
import {makeid} from "../components/makeId";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ime: '',
            prezime: '',
            jmbag: '',
            username: '',
            email: '',
            lozinka: '',
            error: '',
            redirect: false,
            type: 'password',
            gradovi: [],
            key: makeid(5)
        };
        this.showHide = this.showHide.bind(this);

        let options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({gradovi: json})
            if (json.length >= 1) this.setState({grad: json[0].naziv})
        })
    }

    showHide(e) {
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
        e.preventDefault();
        let self = this;

        self.setState({error: ''});

        // names of variables of this object MUST match those of progi.projekt.forms.RegisterForm.class
        const registerForm = {
            ime: self.state.ime,
            prezime: self.state.prezime,
            jmbag: self.state.jmbag,
            username: self.state.username,
            email: self.state.email,
            lozinka: self.state.lozinka,
            nazivGrada: self.state.grad
        };

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(registerForm)
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/auth/register`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.props.authenticate(body);
                        self.setState({redirect: true})
                    });
                } else {
                    response.text().then(body => {
                        self.setState({error: body})
                    });
                }
            }).catch(error => console.log(error));
    };

    isValid = () => {
        const {ime, prezime, jmbag, username, email, lozinka} = this.state;
        return ime.length > 0 && prezime.length > 0 && jmbag.length === 10 && username.length > 0 && email.length > 0 && lozinka.length > 5;
    };

    render() {
        console.log(this.state);
        if (this.props.authenticated) return <Redirect to={"/"}/>;

        return (
            <div className="inner">
                <Form onSubmit={this.onSubmit}>
                    <h3>Registracija</h3>

                    <Form.Group>
                        <Form.Label> Ime </Form.Label>
                        <Form.Control name="ime" type="text" placeholder={this.state.name} onChange={this.onChange}
                                      required/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Prezime </Form.Label>
                        <Form.Control name="prezime" type="text" placeholder={this.state.prezime}
                                      onChange={this.onChange} required
                                      />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> JMBAG </Form.Label>
                        <Form.Control name="jmbag" type="text" placeholder={this.state.jmbag} onChange={this.onChange}
                                      required maxLength="10" minLength="10"/>
                        <Form.Text className={"textMuted"}>
                            JMBAG se sastoji od 10 znamenki!
                        </Form.Text>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Korisničko ime </Form.Label>
                        <Form.Control name="username" type="text" placeholder={this.state.username}
                                      onChange={this.onChange}
                                      required/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Email </Form.Label>
                        <Form.Control name="email" type="email" placeholder={this.state.email} onChange={this.onChange}
                                      required/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Lozinka </Form.Label>
                        <Form.Row>
                            <Col xs={10}>
                                <Form.Control name="lozinka" type={this.state.type} placeholder={this.state.lozinka}
                                              onChange={this.onChange}
                                              required/>
                            </Col>
                            <Col>
                                <Button className="passwordShow" variant={"light"}
                                        onClick={this.showHide}>{this.state.type === 'input' ? <MdEye></MdEye> :
                                    <MdEyeOff></MdEyeOff>}</Button>
                            </Col>
                        </Form.Row>
                        <Form.Text className={"textMuted"}>
                            Lozinka se treba sastojati od najmanje 5 znakova!
                        </Form.Text>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> U kojem gradu studiraš? </Form.Label>
                        <Form.Control as="select" name="grad" onChange={this.onChange} value={this.state.grad}>
                            {this.state.gradovi.map(grad => (
                                <option id={grad.id}>{grad.naziv}</option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <p className="errorMessage">
                        {this.state.error}
                    </p>

                    <Button type="submit" variant="dark" size="lg" block disabled={!this.isValid()}> Registriraj
                        se </Button>
                    <p className="already-registered text-right">
                        <Link to="/login">Već si registriran?</Link>
                    </p>
                </Form>
            </div>
        )
    }
}

export default Register;
