import React, {Component} from "react";
import {Card} from "react-bootstrap";
import Lajkovi from "./Lajkovi";
import {Link} from "react-router-dom";

class KandidatCard extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        let oglas = this.props.kandidat.oglas
        return (
            <div className={"Card"}>
                {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
                <br/>
                {oglas.soba && oglas.soba.komentar && <p>{oglas.soba.komentar}</p>}
                <br/>
                <Lajkovi oglasId={oglas.id}></Lajkovi>
            </div>

        )
    }
}

export default KandidatCard;