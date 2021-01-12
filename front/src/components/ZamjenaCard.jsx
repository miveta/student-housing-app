import React, {Component} from "react";
import * as cookie from "react-cookies";
import {Button, Col, Row} from "react-bootstrap";
import ZamjenaPrikazPara from "./ZamjenaPrikazPara";


class ZamjenaCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            potvrden: false,
            parId: '',
            oglas1: '',
            oglas2: '',
            soba1: '',
            soba2: ''
        }
    }

    componentDidMount() {
        let self = this;
        self.setState({parId: self.props.parId})
        const optionsOglas = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        console.log(self.props)
        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${self.props.oglas1Id}`, optionsOglas)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({oglas1: body})
                        console.log(body)
                    }).catch(error => console.log(error))
                }
            });

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/getoglas?oglas_id=${self.props.oglas2Id}`, optionsOglas)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({oglas2: body})
                        console.log(body)
                    }).catch(error => console.log(error))
                }
            });

        const optionsSoba = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/getsoba?oglas_id=${self.props.oglas1Id}`, optionsSoba)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({soba1: body})
                        console.log(body);
                    }).catch(error => console.log(error))
                }
            });

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/getsoba?oglas_id=${self.props.oglas2Id}`, optionsSoba)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        self.setState({soba2: body})
                        console.log(body);
                    }).catch(error => console.log(error))
                }
            });
        //get listu svih potvrdenih zamjena koje cekaju na odobrenje
    }

    click = (b) => {
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/updateParSC?par_id=${this.state.parId}&odobren=${b}&zaposlenikKorisnickoIme=${this.state.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                    });
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    }

    render() {
        return (
            <div className={"Card"}>
                <Row>
                    <Col>
                        <ZamjenaPrikazPara soba={this.state.soba1}/>
                    </Col>
                    <Col>
                        <ZamjenaPrikazPara soba={this.state.soba2}/>
                    </Col>
                </Row>
                <Row>
                    <Button className={"yes"} onClick={this.click(true)}>
                        Odobri
                    </Button>
                    <Button className={"no"} onClick={this.click(false)}>
                        Odbij
                    </Button>
                </Row>
            </div>
        )
    }

}

export default ZamjenaCard;