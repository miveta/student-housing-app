import React, {Component} from "react";
import {Link} from "react-router-dom";
import Lajkovi from "./Lajkovi";

class OglasCard extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let oglas = this.props.oglas;
        return (
            <div className={"Card"}>
                {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
                <br/>
                {oglas.opis && <p>{oglas.opis}</p>}
                <br/>
                <Lajkovi oglasId={oglas.id}></Lajkovi>
            </div>
        )
    }

}

export default OglasCard;