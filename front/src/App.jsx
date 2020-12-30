import React, {Component} from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {Redirect, Route, Switch} from "react-router-dom";
import cookie from 'react-cookies';

import Soba from "./components/Soba";
import TrazimSobu from "./components/TrazimSobu";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Homepage from "./pages/Homepage";
import Header from "./partial/Header";
import Footer from "./partial/Footer";
import Register from "./pages/Register";
import Soba from "./components/Soba";
import TrazimSobu from "./components/TrazimSobu";
import MojProfil from "./pages/MojProfil"
import {useEffect, useState} from "react";
import UrediProfil from "./components/UrediProfil";


const PrivateRoute = ({component: Component, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            App.state.authenticated ? (
                <Component {...props} />
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

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authenticated: cookie.load('isAuth') === 'true'
        }
    }

    authenticate = (cb) => {
        cookie.save('isAuth', true, {path: '/', maxAge: 5 * 60 * 60});
        cookie.save('principal', cb, {path: '/', maxAge: 5 * 60 * 60});
        this.setState({authenticated: true});
        this.setState({user: cb});

        console.log(this)
    };


    logout = () => {
        cookie.remove('isAuth', {path: '/'});
        cookie.remove('principal', {path: '/'});
        this.setState({authenticated: false});
        this.setState({user: {}})
    };

    render() {
        console.log(this.state);
        return (
            <div className="App">
                <Header authenticated={this.state.authenticated} logout={this.logout}/>
                <div className="outer">
                    <Switch>
                        <Route exact path="/login" component={() => <Login authenticate={this.authenticate}
                                                                           authenticated={this.state.authenticated}/>}/>
                        <Route exact path="/register"
                               component={() => <Register authenticate={this.authenticate}
                                                          authenticated={this.state.authenticated}/>}/>
                        <PrivateRoute exact path="/soba" component={Soba}/>
                        <Route exact path="/trazimsobu" component={TrazimSobu}/>
                        <Route path='/' exact component={Homepage}/>
                    </Switch>
                </div>
                <Footer/>
            </div>
        )
    }
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
                    <Route exact path="/mojprofil" component={() => <MojProfil isLoggedIn={isLoggedIn} onLogout={onLogout}/>}/>
                    <Route exact path="/mojprofil/uredi" component={() => <UrediProfil onLogin={onLogin}/>}/>
                </Switch>
            </div>
            <Footer/>
        </div>
    );
}

export default App;