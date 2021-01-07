import React, {Component} from "react";
import {Button, Nav, Navbar} from "react-bootstrap";
import * as Swal from "sweetalert2";
import {withRouter} from 'react-router-dom';
import ObavijestiDropdown from "../components/ObavijestiDropdown";


class Header extends Component {
    constructor(props) {
        super(props);
    }

    logout = async () => {

        Swal.fire({
            title: 'Jeste li sigurni da se želite odjaviti?',
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#12c2e9',
            cancelButtonColor: '#f64571',
            cancelButtonText: 'Odustani',
            confirmButtonText: 'Da, želim.',
            heightAuto: false
        }).then(async (result) => {
            if (result.value) {
                this.props.history.push("/");
                await this.props.logout();
            }
        })
    };

    render() {
        return (
            <Navbar expand="lg" bg="light" className="header" sticky={"top"}>
                <Navbar.Brand href="/">ZAMJENA SOBA</Navbar.Brand>
                <Navbar.Toggle/>
                <Navbar.Collapse className="justify-content-end">
                    {this.props.authenticated
                        ?
                        <Nav>
                            <Nav.Link href="/oglasi">Oglasi</Nav.Link>
                            <Nav.Link href="/mojprofil">Profil</Nav.Link>
                            <ObavijestiDropdown/>
                            <Button variant="light" onClick={this.logout}> Odjavi se </Button>
                        </Nav>
                        :
                        <Nav>
                            <Nav.Link href="/login">Prijava</Nav.Link>
                            <Nav.Link href="/register">Registracija</Nav.Link>
                        </Nav>
                    }
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default withRouter(Header);