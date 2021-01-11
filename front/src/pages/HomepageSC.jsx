import React, {Component} from "react";
import cookie from "react-cookies";
import OglasList from "../components/OglasList";
import {Col, Nav, Row, Tab} from "react-bootstrap";
import ZamjenaList from "../components/ZamjenaList";

class HomepageSC extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            upiti: []
        };
    }

    componentDidMount() {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        //dodati podršku za back
        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/listParoviWithFlags`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        this.setState({upiti: body})
                        console.log(this.state.upiti)
                    }).catch(error => console.log(error))
                }
            });
    }

    render() {

        return(
            <div className="middle">
                <Tab.Container id="left-tabs-example" defaultActiveKey="first" className={"left-tabs"}>
                    <Row>
                        <Col sm={2}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link eventKey="first">Svi upiti</Nav.Link>
                                </Nav.Item>
                                {
                                    this.state.user && <Nav.Item>
                                        <Nav.Link eventKey="second">Potvrđeni upiti</Nav.Link>
                                    </Nav.Item>
                                }

                            </Nav>
                        </Col>
                        <Col>
                            <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <ZamjenaList upiti={this.state.upiti}/>
                                </Tab.Pane>
                                {
                                    this.state.user &&
                                    <Tab.Pane eventKey="second">
                                        <ZamjenaList/>
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
export default HomepageSC;