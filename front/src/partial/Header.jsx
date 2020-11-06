import React, {Component} from "react";
import {Link} from "react-router-dom";

class Header extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        {/*
            // todo - promijeni u bootstrap!
             kad se ekran smanji na pola nestane navbar
        */}
        return(
            <nav className="navbar navbar-expand-lg navbar-light fixed-top">
                <div className="container">
                    <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                        <ul className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link className="nav-link" to={"/login"}>Login</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to={"/register"}>Register</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }
}

export default Header;