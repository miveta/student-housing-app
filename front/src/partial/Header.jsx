import React, {Component} from "react";
import {Button, Nav, Navbar} from "react-bootstrap";

class Header extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Navbar expand="lg" bg="light" className="justify-content-between header" sticky={"top"}>
                <Navbar.Brand href="/">ZAMJENA SOBA</Navbar.Brand>
                {this.props.isLoggedIn
                    ?
                    <Nav className="justify-content-end">
                        <Nav.Item>
                            <Nav.Link href="/soba">Moja soba</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="/trazimsobu">Tražim sobu</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="/mojprofil">Moj profil</Nav.Link>
                        </Nav.Item>
                        <Button variant="light" onClick={this.props.onLogout}> Odjavi se </Button>
                    </Nav>
                    :
                    <Nav className="justify-content-end">
                        <Nav.Item>
                            <Nav.Link href="/login">Prijava</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="/register">Registracija</Nav.Link>
                        </Nav.Item>
                    </Nav>
                }
            </Navbar>
        )
    }
}

export default Header;