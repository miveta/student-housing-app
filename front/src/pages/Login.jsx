import React from "react";
import {Button, Form} from 'react-bootstrap';

function Login(props) {
    const [loginForm, setLoginForm] = React.useState({username: '', lozinka: ''});
    const [error, setError] = React.useState('');

    // todo potencijalno ovo staviti kao props kojeg mu app proslijeduje?
    function onChange(event) {
        const {name, value} = event.target;
        setLoginForm(oldForm => ({...oldForm, [name]: value}))
    }

    function onSubmit(e) {
        e.preventDefault();
        setError("");

        const body = {
            username: loginForm.username,
            lozinka: loginForm.lozinka
        };


        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };

        fetch('http://localhost:8080/checklogin', options)
            .then(response => {
                if (response.status === 401) {
                    setError("Login failed");
                } else {
                    props.onLogin();
                }
            });
    }

    return (
        <Form onSubmit={onSubmit}>
            <h3>Log in</h3>

            <Form.Group>
                <Form.Label> Korisničko ime </Form.Label>
                <Form.Control name="username" type="text" placeholder={loginForm.username} onChange={onChange}/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Password </Form.Label>
                <Form.Control name="password" type="password" placeholder={loginForm.lozinka} onChange={onChange}/>
            </Form.Group>
            <Button type="submit" variant="dark" size="lg" block>Sign in</Button>

            {
                // Zakomentirano zato što nisam sigurna da ćemo raditi to sa forgot your pwd
                /*
                <p className="forgot-password text-right">
                    Forgot <a href="#">password?</a>
                </p>
                */
            }
        </Form>
    )
}

export default Login;