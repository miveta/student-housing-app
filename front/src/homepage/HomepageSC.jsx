import React, {Component} from "react";
import cookie from "react-cookies";
import {Col, Nav, Row, Tab} from "react-bootstrap";
import ZamjenaList from "../components/ZamjenaList";
import {makeid} from "../components/makeId";

class HomepageSC extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            upiti: [],
            odobreniUpiti: [],
            key: makeid(5)
        };

        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/listParoviWithFlags?done=${true}`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body => {
                        this.setState({upiti: body});
                        let odobreni = this.state.upiti.filter(upit => upit.odobren === true);
                        this.setState({odobreniUpiti: odobreni});
                    }).catch(error => console.log(error))
                }
            });


    }

    /*getOdobreniUpiti(){

        this.state.upiti.forEach(u => {
            if(u.odobren)
                this.state.odobreniUpiti.add(u);
        })
    }*/

    render() {
        return(
            <div className="middleHomepage">
                <Tab.Container id="left-tabs-example" defaultActiveKey="sviUpiti" className={"left-tabs"}>
                    <Row>
                        <Col sm={2}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link eventKey="sviUpiti">Svi upiti</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link eventKey="odobreniUpiti">Odobreni upiti</Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Col>
                        <Col>
                            <Tab.Content>
                                <Tab.Pane eventKey="sviUpiti">
                                    <ZamjenaList upiti={this.state.upiti}/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="odobreniUpiti">
                                    <ZamjenaList upiti={this.state.odobreniUpiti}/>
                                </Tab.Pane>
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        )
    }
}
export default HomepageSC;
