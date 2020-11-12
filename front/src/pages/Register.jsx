import React from "react";
import {Button, Form} from 'react-bootstrap';
import {hashPassword} from "../index";
import {Link} from "react-router-dom";

function Register(props) {
    const [form, setForm] = React.useState({ime: '', prezime: '', jmbag: '', username: '', email: '', lozinka: ''});
    const [error, setError] = React.useState('');

    function onChange(event) {
        const {name, value} = event.target;
        setForm(oldForm => ({...oldForm, [name]: value}))
    }

    function onSubmit(e) {
        e.preventDefault();
        setError("");

        // names of variables of this object MUST match those of progi.projekt.forms.RegisterForm.class
        const registerForm = {
            ime: form.ime,
            prezime: form.prezime,
            jmbag: form.jmbag,
            username: form.username,
            email: form.email,
            lozinka: form.lozinka
        };

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(registerForm)
        };

        fetch('http://localhost:8080/auth/register', options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        props.onLogin(body);
                    });
                }
            }).catch(error => console.log(error));
    }

    function isValid() {
        const {ime, prezime, jmbag, username, email, lozinka} = form;
        return ime.length > 0 && prezime.length > 0 && jmbag.length === 10 && username.length > 0 && email.length > 0 && lozinka.length > 5;
    }

    return (
        <div className="inner">
            <Form onSubmit={onSubmit}>
                <h3>Registracija</h3>

                <Form.Group>
                    <Form.Label> Ime* </Form.Label>
                    <Form.Control name="ime" type="text" placeholder={form.name} onChange={onChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Prezime* </Form.Label>
                    <Form.Control name="prezime" type="text" placeholder={form.prezime} onChange={onChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> JMBAG* (mora biti 10 znamenki) </Form.Label>
                    <Form.Control name="jmbag" type="text" placeholder={form.jmbag} onChange={onChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Korisničko ime* </Form.Label>
                    <Form.Control name="username" type="text" placeholder={form.username} onChange={onChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Email* </Form.Label>
                    <Form.Control name="email" type="email" placeholder={form.email} onChange={onChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Lozinka* (minimalno 5 znakova)</Form.Label>
                    <Form.Control name="lozinka" type="password" placeholder={form.lozinka} onChange={onChange}/>
                </Form.Group>
                <Button type="submit" variant="dark" size="lg" block disabled={!isValid()}> Registriraj se </Button>
                <p className="already-registered text-right">
                    <Link to="/login">Već si registriran?</Link>
                </p>
                <p>
                    Polja označena * ne smiju ostati prazna!
                </p>
            </Form>
        </div>
    )
}

export default Register;