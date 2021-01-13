import React, {Component} from "react";
import {Link} from "react-router-dom";
import Lajkovi from "../../components/Lajkovi";
import * as cookie from "react-cookies";
import {Nav} from "react-bootstrap";
import {TextField} from "@material-ui/core";
import {TextFormat} from "@material-ui/icons";

class OglasCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal')
        }
    }

    render() {
        let oglas = this.props.oglas;
        return (
            <a href={`/oglas/id=${oglas.id}`}>
                <div className={"Card"}>
                    <Nav.Item>
                        {oglas.naslov && <Nav.Link href={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Nav.Link>}
                    </Nav.Item>
                    {oglas.soba && oglas.soba.komentar && <p>{oglas.soba.komentar}</p>}
                    <br/>
                    <Lajkovi oglasId={oglas.id} user={this.state.user}></Lajkovi>
                </div>
            </a>
        )
    }

}

export default OglasCard;