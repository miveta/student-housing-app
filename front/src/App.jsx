import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {Redirect, Route, Switch} from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Footer from "./partial/Footer";
import Header from "./partial/Header";

function App() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [loadingUser, setLoadingUser] = React.useState(true);

    React.useEffect(() => {
        fetch("/userInfo")
            .then(response => {
                console.log(response);
                if (response.status !== 401) {
                    setLoadingUser(false);
                    setIsLoggedIn(true);
                } else {
                    setLoadingUser(false);
                }
            })
    }, []);

    if (loadingUser) {
        return <div>Loading...</div>
    }

    function onLogin() {
        setIsLoggedIn(true)
    }

    function onLogut() {
        setIsLoggedIn(false);
    }


    return (
        <div className="App">
            {isLoggedIn && <Header/>}
            <div className="outer">
                <div className="inner">
                    <Switch>
                        <Route exact path="/login" component={() => <Login onLogin={onLogin}/>}/>
                        <Route exact path="/register" component={Register}/>
                        {/*Je li ovo ok praksa??*/}
                        <Redirect to="/login"/>
                    </Switch>
                </div>
            </div>
            {isLoggedIn && <Footer/>}
        </div>

    );
}

export default App;