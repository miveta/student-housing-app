import React, {Component} from "react";
import {Button, Nav, Navbar} from "react-bootstrap";
import * as Swal from "sweetalert2";
import {withRouter} from 'react-router-dom';


class Header extends Component {
    constructor(props) {
        super(props);
    }

    logout = async () => {

        Swal.fire({
            title: 'Jeste li sigurni da se želite odjaviti?',
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#33B5E7',
            cancelButtonColor: '#d33',
            cancelButtonText: 'Odustani',
            confirmButtonText: 'Da, želim.'
        }).then(async (result) => {
            if (result.value) {
                await this.props.logout();
                this.props.history.push("/")
            }
        })
    };

    render() {
        return (
            <Navbar expand="lg" bg="light" className="justify-content-between header" sticky={"top"}>
                <Navbar.Brand href="/">ZAMJENA SOBA</Navbar.Brand>
                {this.props.authenticated
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
                        <Button variant="light" onClick={this.logout}> Odjavi se </Button>
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

export default withRouter(Header);