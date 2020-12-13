import React from "react";
import {Button, Form} from 'react-bootstrap';
import {Link} from "react-router-dom";

function Login(props) {
    const [loginForm, setLoginForm] = React.useState({username: '', password: ''});
    const [error, setError] = React.useState('');

    function onChange(event) {
        const {name, value} = event.target;
        setLoginForm(oldForm => ({...oldForm, [name]: value}))
    }

    function onSubmit(e) {
        e.preventDefault();
        setError("");

        const body = {
            username: loginForm.username,
            password: loginForm.password
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
                        props.onLogin(body);
                    });
                } else if (response.status === 401 || response.status === 400) {
                    response.text().then(body => {
                        setError(body);
                    });
                } else {
                    response.text().then(body => {
                        console.log(body)
                    });
                }
            }).catch(error => console.log(error));
    }

    function isValid() {
        const {username, password} = loginForm;
        return username.length > 0 && password.length > 5;
    }

    if (props.isLoggedIn) {
        // todo ulogirani user ne bi mogao ni pristupiti ovoj komponenti
    }

    return (
        <div className="inner">
            <Form onSubmit={onSubmit}>
                <h3>Prijava</h3>

                <Form.Group>
                    <Form.Label> Korisničko ime </Form.Label>
                    <Form.Control name="username" type="text" placeholder={loginForm.username} onChange={onChange}
                                  required/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Lozinka </Form.Label>
                    <Form.Control name="password" type="password" placeholder={loginForm.password} onChange={onChange}
                                  required/>
                </Form.Group>
                <p className="errorMessage">
                    {error}
                </p>
                <Button type="submit" variant="dark" size="lg" block disabled={!isValid()}> Prijavi se </Button>
                <p className="not-registered text-right">
                    <Link to="/register">Nisi još registriran?</Link>
                </p>
                {
                    // Zakomentirano zato što nisam sigurna da ćemo raditi to sa forgot your pwd
                    /*
                    <p className="forgot-password text-right">
                        Forgot <a href="#">password?</a>
                    </p>
                    */
                }
            </Form>
        </div>
    )
}

export default Login;