import React, {Component} from 'react';
import OglasList from "../oglasi/homepage/OglasList";
import cookie from "react-cookies";
import {Col, Dropdown, NavItem, NavLink, Row, Tab} from "react-bootstrap";
import {withRouter} from "react-router-dom";

class Homepage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            oglasi: [],
            kandOglasi: [],
            gradovi: [],
            grad: null
        };

        if (cookie.load('role') === 'zaposlenikSC') props.history.push("/homepagesc")
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

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({gradovi: json})
            if (!this.state.user && json.length >= 1) this.setState({grad: json[0].naziv})
        })
    }

    onChange = (event) => {
        const {name, id} = event.target;
        this.setState(state => ({...state, [name]: id}))
    };

    render() {
        return (
            <Tab.Container id="left-tabs-example" defaultActiveKey="first" className={"tabs"}>
                <Row className={"tabs"}>
                    <Col sm={2} className={"left"} style={{minWidth: '280px'}}>
                        {
                            this.state.user ?
                                <Dropdown as={NavItem} className={"navigation"} style={{minWidth: '200px'}}>
                                    <Dropdown.Item as={NavLink} eventKey="first" block>Svi oglasi</Dropdown.Item>
                                    <Dropdown.Item as={NavLink} eventKey="second" block>Preporučeni
                                        oglasi</Dropdown.Item>
                                </Dropdown>
                                :
                                <Dropdown as={NavItem} className={"navigation"}>
                                    {this.state.gradovi.map(grad => (
                                        <Dropdown.Item id={grad.naziv} name={"grad"} onClick={this.onChange}
                                                       eventKey={"first"}>
                                            {grad.naziv}
                                        </Dropdown.Item>
                                    ))}
                                </Dropdown>
                        }
                    </Col>

                    <Col className={"middle"}>
                        <Tab.Content>
                            <Tab.Pane eventKey="first">
                                {!this.state.user ? this.state.grad &&
                                    <h3>{this.state.grad}</h3>
                                    :
                                    <h3> Sve dostupne sobe u vašem gradu </h3>
                                }

                                <OglasList oglasi={this.state.oglasi}
                                           grad={this.state.user ? this.state.user.grad : this.state.grad}
                                           jmbag={this.state.user ? this.state.user.jmbag : null}/>
                            </Tab.Pane>
                            {
                                this.state.user &&
                                <Tab.Pane eventKey="second">
                                    <h3> Sobe koje vam najbolje odgovaraju</h3>
                                    <OglasList oglasi={this.state.kandOglasi} isLoggedIn={this.props.isLoggedIn}
                                               user={this.state.user.jmbag}/>
                                </Tab.Pane>
                            }
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        )
    }
}

export default withRouter(Homepage);