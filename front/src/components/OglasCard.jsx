import React from "react";
import {Link} from "react-router-dom";

function OglasCard(props) {
    const oglas = props.oglas;


    return (
        <div className={"Card"}>
            {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
            <br/>
            {oglas.opis && <p>{oglas.opis}</p>}
        </div>
    );
}

export default OglasCard;
