import React, {Component} from "react";
import {Button, Nav, Navbar} from "react-bootstrap";

class Header extends Component {
    constructor(props) {
        super(props);
    }

    logout() {
        fetch("/logout").then(() => {
            this.state.props.onLogout();
        });
    }

    render() {
        return(
            <Navbar expand="lg" bg="light">
                <Nav className="mr-auto">
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/register">Register</Nav.Link>
                    <Button onClick={this.logout}/>
                </Nav>
            </Navbar>
        )
    }
}

export default Header;