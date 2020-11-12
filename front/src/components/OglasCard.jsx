import React from "react";
import {Link} from "react-router-dom";

function OglasCard(props) {
    const oglas = props.oglas;

    function onClick() {
        window.location(`http://localhost:3000/oglas/id=${oglas.id}`);
    }

    return (
        <div className={"Card"}>
            {oglas.naslov && <Link onClick={onClick}>{oglas.naslov}</Link>}
            <br/>
            {oglas.opis && <p>{oglas.opis}</p>}
        </div>
    );
}

export default OglasCard;
