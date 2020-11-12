import React from "react";
import Card from "./Card";

function Oglas(props) {
    const {id, naslov, opis, godina, objavljen, status, student} = props.oglas;

    return (
        <Card title={naslov}>
            <p>{id} {naslov} {opis} {godina} {objavljen} {status} {student}</p>
        </Card>
    );
}

export default Oglas;
