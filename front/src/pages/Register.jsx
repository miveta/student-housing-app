import React from "react";
import {Button, Form} from 'react-bootstrap';
import {hashingPassword} from "../index";
import {Link} from "react-router-dom";

function Register(props) {
    const [form, setForm] = React.useState({ime: '', prezime: '', jmbag: '', username: '', email: '', lozinka: ''});
    const [error, setError] = React.useState('');

    function onChange(event) {
        const {name, value} = event.target;
        setForm(oldForm => ({...oldForm, [name]: value}))
    }

    async function onSubmit(e) {
        e.preventDefault();
        setError("");

        const body = {
            ime: form.ime,
            prezime: form.prezime,
            jmbag: form.jmbag,
            username: form.username,
            email: form.email,
            lozinka: await hashingPassword(form.lozinka)
        };

        console.log(body);
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };

        fetch('http://localhost:8080/register', options)
            .then(response => {
                    if (response.status === 401) {
                        setError("Login failed");
                    } else if (response.status === 400) {
                        response.json().then(body => {
                            setError(body.errors);
                        })
                    } else {

                    }
                }
            );
    }

    function isValid() {
        const {ime, prezime, jmbag, username, email, lozinka} = form;
        return true;
    }

    return (
        <Form onSubmit={onSubmit}>
            <h3>Registracija</h3>

            <Form.Group>
                <Form.Label> Ime </Form.Label>
                <Form.Control name="ime" type="text" placeholder={form.name} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Prezime </Form.Label>
                <Form.Control name="prezime" type="text" placeholder={form.prezime} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> JMBAG </Form.Label>
                <Form.Control name="jmbag" type="text" placeholder={form.jmbag} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Korisničko ime </Form.Label>
                <Form.Control name="username" type="text" placeholder={form.username} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Email </Form.Label>
                <Form.Control name="email" type="email" placeholder={form.email} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Lozinka </Form.Label>
                <Form.Control name="lozinka" type="password" placeholder={form.lozinka} onChange={onChange}/>
            </Form.Group>
            <div>{error}</div>
            <Button type="submit" variant="dark" size="lg" block disabled={!isValid()}> Registriraj se </Button>
            {
                <p className="already-registered text-right">
                    <Link to="/sign-in">Već si registriran?</Link>
                </p>
            }
        </Form>
    )
}

export default Register;