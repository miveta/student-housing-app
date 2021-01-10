import React, {Component} from "react";
import {Link} from "react-router-dom";
import Lajkovi from "./Lajkovi";
import * as cookie from "react-cookies";
import {Button, ButtonGroup, Col, FormLabel} from "react-bootstrap";

class ZamijenaCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal'),
            potvrden: false
        }
    }

    componentDidMount() {
        let self = this;
        //get listu svih potvrdenih zamjena koje cekaju na odobrenje
    }

    click = () => {
        //post
    }

    render() {
        return (
            <div className={"Card"}>
                <FormLabel>Pero i Marko se žele zamijeniti za sobu</FormLabel>
                <br/>

                <ButtonGroup>
                    <Col xs={9}>
                        <Button variant="primary" hidden={this.state.potvrden} onClick={this.click}>
                            Odobri
                        </Button>
                    </Col>
                    <Col>
                        <Button variant="danger" hidden={this.state.potvrden}>
                            Odbij
                        </Button>
                    </Col>
                </ButtonGroup>
            </div>
        )
    }

}

export default ZamijenaCard;