import React, {Component} from 'react';
import OglasList from "../oglasi/homepage/OglasList";
import cookie from "react-cookies";
import {Col, Nav, Row, Tab} from "react-bootstrap";

class Homepage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            oglasi: [],
            kandOglasi: []
        };
    }

    componentDidMount() {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/list`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body =>
                        this.setState({oglasi: body})
                    ).catch(error => console.log(error))
                }
            });

        console.log(this)

        if (this.state.user !== undefined) {
            fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/kandidati/student?student_username=${this.state.user.korisnickoIme}`, options)
                .then(response => {
                    if (response.status === 200) {
                        response.json().then(body =>
                            this.setState({kandOglasi: body})
                        ).catch(error => console.log(error))
                    }
                });
        }

    }

    render() {
        return (
            <div className="middle">
                <Tab.Container id="left-tabs-example" defaultActiveKey="first" className={"left-tabs"}>
                    <Row>
                        <Col sm={2}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link eventKey="first">Svi oglasi</Nav.Link>
                                </Nav.Item>
                                {
                                    this.state.user && <Nav.Item>
                                        <Nav.Link eventKey="second">Preporučeni oglasi</Nav.Link>
                                    </Nav.Item>
                                }

                            </Nav>
                        </Col>
                        <Col>
                            <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <OglasList oglasi={this.state.oglasi}
                                               isLoggedIn={this.props.isLoggedIn}/>
                                </Tab.Pane>
                                {
                                    this.state.user &&
                                    <Tab.Pane eventKey="second">
                                        <OglasList oglasi={this.state.kandOglasi} isLoggedIn={this.props.isLoggedIn}/>
                                    </Tab.Pane>
                                }
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        )
    }
}

export default Homepage;