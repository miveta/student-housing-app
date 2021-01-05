import React, {Component} from "react";
import {Navbar} from "react-bootstrap";

class Footer extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <Navbar bg="light" className="footer">
                Spring rolice 2020
            </Navbar>
        )
    }
}

export default Footer;