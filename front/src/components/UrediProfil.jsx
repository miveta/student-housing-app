import React from "react";
import {Button, Form} from 'react-bootstrap';
import {Link} from "react-router-dom";
import {withRouter} from 'react-router-dom';
import cookie from "react-cookies";


function UrediProfil(props) {
    const user = cookie.load('principal');
    const [form, setForm] = React.useState({ime: user.ime, prezime: user.prezime, jmbag: user.jmbag, username: user.korisnickoIme, email: user.email, obavijestiNaMail: user.obavijestiNaMail});
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
            obavijestiNaMail: form.obavijestiNaMail
        };

        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(registerForm)
        };

        console.log(registerForm);
        fetch(`${process.env.REACT_APP_BACKEND_URL}/student/update`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        props.onLogin(body)
                        props.history.push("/mojprofil")
                    });
                } else {
                    response.text().then(body => {
                        setError(body);
                    });
                }
            }).catch(error => console.log(error));
    }

    function isValid() {
        const {ime, prezime, username, email} = form;
        return ime.length > 0 && prezime.length > 0 && username.length > 0 && email.length > 0;
    }

    return (
        <div className="inner">
            <Form onSubmit={onSubmit}>
                <h3>Uredi profil</h3>

                <Form.Group>
                    <Form.Label> Ime* </Form.Label>
                    <Form.Control name="ime" defaultValue={user.ime} type="text" placeholder={form.name} onChange={onChange} required/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Prezime* </Form.Label>
                    <Form.Control name="prezime" defaultValue={user.prezime} type="text" placeholder={form.prezime} onChange={onChange} required/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> JMBAG* (mora biti 10 znamenki) </Form.Label>
                    <Form.Control name="jmbag" readOnly defaultValue={user.jmbag} type="text" placeholder={form.jmbag} onChange={onChange} required/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Korisničko ime* </Form.Label>
                    <Form.Control name="username" readOnly defaultValue={user.korisnickoIme} type="text" placeholder={form.username} onChange={onChange} required/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Email* </Form.Label>
                    <Form.Control name="email" type="email" defaultValue={user.email} placeholder={form.email} onChange={onChange} required/>
                </Form.Group>
                <Form.Group>
                    <Form.Check name="obavijestiNaMail" type="checkbox" defaultChecked={user.obavijestiNaMail} onChange={e => setForm(oldForm => ({...oldForm, ["obavijestiNaMail"]: e.target.checked}))} label="Obavijesti na mail" />
                </Form.Group>
                <p className="errorMessage">
                    {error}
                </p>
                <Button type="submit" variant="dark" size="lg" block disabled={!isValid()}> Ažuriraj podatke </Button>
                <p>
                    Polja označena * ne smiju ostati prazna!
                </p>
            </Form>
        </div>
    )
}

export default withRouter(UrediProfil);
