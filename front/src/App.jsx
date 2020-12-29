import React, {useEffect, useState} from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {Route, Switch, useHistory} from "react-router-dom";

import Login from "./pages/Login";
import Header from "./partial/Header";
import Homepage from "./pages/Homepage";
import Footer from "./partial/Footer";
import Register from "./pages/Register";
import Soba from "./components/Soba";
import TrazimSobu from "./components/TrazimSobu";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    let history = useHistory();

    useEffect(() => setIsLoggedIn(localStorage.getItem("user") !== null));

    function onLogin(user) {
        localStorage.setItem("user", JSON.stringify(user));
        setIsLoggedIn(true);
        history.push('/')
    }

    function onLogout() {
        localStorage.clear();
        setIsLoggedIn(false);
        history.push('/')
    }

    /*
        if (!isLoggedIn) {

            <Switch>
                <Route exact path="/login" component={() => <Login onLogin={onLogin}/>}/>
                <Route exact path="/register" component={() => <Register onLogin={onLogin}/>}/>
                {/!*Je li ovo ok praksa??*!/}
                <Redirect to="/login"/>
            </Switch>

        }
    */

    return (
        <div className="App">
            <Header onLogout={onLogout} isLoggedIn={isLoggedIn}/>
            <div className="outer">
                <Switch>
                    {/* todo slozi ove rute tako da ulogirani korisnik ni ne može otići na /login */}
                    <Route exact path="/login" component={() => <Login onLogin={onLogin} isLoggedIn={isLoggedIn}/>}/>
                    <Route exact path="/register"
                           component={() => <Register onLogin={onLogin} isLoggedIn={isLoggedIn}/>}/>
                    <Route exact path="/soba" component={() => <Soba/>}/>
                    <Route exact path="/trazimsobu" component={() => <TrazimSobu/>}/>
                    <Route path='/' exact component={() => <Homepage isLoggedIn={isLoggedIn}/>}/>
                </Switch>
            </div>
            <Footer/>
        </div>
    );
}

export default App;

/*
function PrivateRoute({ component: Component, ...rest }) {
    return (
        <Route
            {...rest}
            render={props =>
                cookie.load('isAuth') === 'true' ? (
                    <Component {...props}/>
                ) : (
                    <Redirect
                        to={{
                            pathname: "/login",

                        }}
                    />
                )
            }
        />
    );
}*/
