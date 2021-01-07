import React, {Component} from "react";
import {Link} from "react-router-dom";
import Lajkovi from "./Lajkovi";
import {Card} from "react-bootstrap";
import * as cookie from "react-cookies";

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
            <div className={"Card"}>
                {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
                <br/>
                {oglas.soba && oglas.soba.komentar && <p>{oglas.soba.komentar}</p>}
                <br/>
                <Lajkovi oglasId={oglas.id} user={this.state.user}></Lajkovi>
            </div>
        )
    }

}

export default OglasCard;