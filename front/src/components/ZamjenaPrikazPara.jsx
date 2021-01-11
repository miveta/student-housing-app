import React, {Component} from "react";
import {Col, Form, Row} from "react-bootstrap";
import cookie from "react-cookies";

class ZamjenaPrikazPara extends Component{
    constructor(props) {
        super(props);
        this.state = {
            soba: ''
        }
    }

    componentDidMount() {
        let self = this;
        self.setState({soba: self.props.soba})
    }

    render() {

        return(
            <div class="innerform">
                <Form>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Kat sobe u paviljonu
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.kat}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Broj kreveta
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.brojKreveta}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Form.Label className={"formLabel"} column xs="10">
                            Tip kupaonice
                        </Form.Label>
                        <Col sm="10">
                            <Form.Label column sm="10">
                                {this.state.soba.tipKupaonice}
                            </Form.Label>
                        </Col>
                    </Form.Group>
                    <br/>
                </Form>
            </div>
        )
    }
}
export default ZamjenaPrikazPara;