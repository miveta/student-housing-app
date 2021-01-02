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
import MojProfil from "./pages/MojProfil"
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
                        <Route exact path="/mojprofil" component={() => <MojProfil isLoggedIn={this.state.authenticated} onLogout={this.logout}/>}/>
                        <Route exact path="/mojprofil/uredi" component={() => <UrediProfil onLogin={this.authenticate}/>}/>
                    </Switch>
                </div>
                <Footer/>
            </div>
        )
    }
}

export default App;