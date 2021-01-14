import React, {Component} from "react";
import {Button, Col, Nav, Navbar, Row} from "react-bootstrap";
import * as Swal from "sweetalert2";
import {withRouter} from 'react-router-dom';
import ObavijestiDropdown from "../components/ObavijestiDropdown";
import cookie from "react-cookies";
import {makeid} from "../components/makeId";


class Header extends Component {
    constructor(props) {
        super(props);
        this.key = makeid(5);
        this.state = {
            user: cookie.load('principal')
        }
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

        /*let isStudent = this.state.user.tipKorisnika === "student";*/
        return (
            <Navbar expand="lg" bg="light" className="header" sticky={"top"}>
                <Navbar.Brand href="/">ZAMJENA SOBA</Navbar.Brand>
                <Navbar.Toggle/>
                <Navbar.Collapse className="justify-content-end">
                    {this.props.authenticated
                        ?
                        <Nav>
                            {/*todo napraviti da se ne vidi Oglasi Profil i Obavijesti na stranici od zaposlenika*/}
                            {/*edit: treba li ipak ostaviti profil, pa tamo nesto dodati da se ne moze mijenjati i*/}
                            {/*brisati ak nisi student*/}
                                <Nav.Link href="/oglasi">Oglasi</Nav.Link>
                                <Nav.Link href="/mojprofil">Profil</Nav.Link>
                                <ObavijestiDropdown getObavijesti={this.sendMessage} user={this.props.user}/>

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
