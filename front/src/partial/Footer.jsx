import React, {Component} from "react";
import {Navbar} from "react-bootstrap";

class Footer extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(

            <footer>
            <Navbar bg="light" className="justify-content-end footer">
                Spring rolice 2020
            </Navbar>
            </footer>

        )
    }
}

export default Footer;