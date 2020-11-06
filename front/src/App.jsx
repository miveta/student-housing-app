import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";

import Login from "./components/Login";
import Register from "./components/Register";
import Footer from "./partial/Footer";
import Header from "./partial/Header";

function App() {
    return (
        <Router>
            <div className="App">
                <Header/>
                <div className="outer">
                    <div className="inner">
                        <Switch>
                            <Route exact path='/' component={Login}/>
                            <Route path="/login" component={Login}/>
                            <Route path="/register" component={Register}/>
                            {/*Je li ovo ok praksa??*/}
                            <Redirect to="/"/>
                        </Switch>
                    </div>
                </div>
                <Footer/>
            </div>
        </Router>
    );
}

export default App;