import React, {Component} from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {Redirect, Route, Switch} from "react-router-dom";
import cookie from 'react-cookies';
import Login from "./pages/Login";
import Register from "./pages/Register";
import Homepage from "./pages/Homepage";
import Header from "./partial/Header";
import Footer from "./partial/Footer";
import MojProfil from "./pages/MojProfil"
import UrediProfil from "./components/UrediProfil";
import Oglas from "./pages/Oglas";
import MojOglas from "./pages/MojOglas";
import TrazimSobu from "./components/TrazimSobu";
import HomepageSC from "./pages/HomepageSC";

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
            authenticated: cookie.load('isAuth') === 'true',
            user: cookie.load("principal")
        }
    }

    authenticate = (cb) => {
        cookie.save('isAuth', true, {path: '/', maxAge: 5 * 60 * 60});
        cookie.save('principal', cb, {path: '/', maxAge: 5 * 60 * 60});
        this.setState({authenticated: true});
        this.setState({user: cb});
    };


    logout = () => {
        cookie.remove('isAuth', {path: '/'});
        cookie.remove('principal', {path: '/'});
        this.setState({authenticated: false});
        this.setState({user: {}})
    };


    render() {
        return (
            <div className="App">
                <Header authenticated={this.state.authenticated} logout={this.logout} user={this.state.user}/>
                <div className="outer">
                    <Switch>
                        <Route exact path="/login" component={() => <Login authenticate={this.authenticate}
                                                                           authenticated={this.state.authenticated}/>}/>
                        <Route exact path="/register"
                               component={() => <Register authenticate={this.authenticate}
                                                          authenticated={this.state.authenticated}/>}/>
                        <Route exact path="/oglasi" component={MojOglas}/>

                        <Route path='/' exact component={() => <Homepage isLoggedIn={this.state.authenticated}/>}/>
                        <Route exact path="/trazimsobu" component={TrazimSobu}/>
                        <Route exact path="/mojprofil" component={() => <MojProfil isLoggedIn={this.state.authenticated} onLogout={this.logout}/>}/>
                        <Route exact path="/mojprofil/uredi" component={() => <UrediProfil onLogin={this.authenticate}/>}/>
                        <Route exact path="/oglas/:id" component={Oglas}/>

                        {/*todo sloziti privatne rute za studenta i zaposlenika, ovo sve iznad ne bi trebao moc vidjeti zaposlenik, a ovo ispod student*/}
                        {/*this.state.authenticated && this.state.user.tipKorisnika === "zaposlenikSC" && */<Route exact path="/homepagesc" component={HomepageSC}/>}
                    </Switch>
                </div>
                <Footer/>
            </div>
        )
    }
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
